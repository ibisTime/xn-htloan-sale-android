package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.DataFileAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityDataDetialsBinding;
import com.cdkj.wzcd.model.CLQDBean;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 资料传递详情
 */
public class DataDetialsActivity extends AbsBaseLoadActivity {

    private ActivityDataDetialsBinding mBinding;
    private String code;
    private boolean isGps;
    private List<DataDictionary> mCompany;

    private List<CLQDBean> refFileList = new ArrayList<>();
    private List<CLQDBean> fileList;
    private DataFileAdapter refFileAdapter;

    public static void open(Context context, String code, List<DataDictionary> company, boolean isGps) {
        if (context != null) {
            Intent intent = new Intent(context, DataDetialsActivity.class);
            intent.putExtra(DATA_SIGN, code);
            intent.putExtra("company", (Serializable) company);
            intent.putExtra("isGps", isGps);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_data_detials, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("详情");

        init();
        getData();
    }

    private void init() {
        if (getIntent() != null) {
            code = getIntent().getStringExtra(DATA_SIGN);
            mCompany = (List<DataDictionary>) getIntent().getSerializableExtra("company");
            isGps = getIntent().getBooleanExtra("isGps", false);
        }
        if (isGps) {
            mBinding.llGps.setVisibility(View.VISIBLE);
        } else {
            getCLQD();
            initAdapter();
        }
    }

    private void initAdapter() {
        refFileAdapter = new DataFileAdapter(refFileList);
        mBinding.rvRefFile.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvRefFile.setAdapter(refFileAdapter);
    }

    /**
     * 获取材料清单
     */
    private void getCLQD() {

        Call<BaseResponseListModel<CLQDBean>> clqd = RetrofitUtils.createApi(MyApiServer.class).getCLQD("632217", "{}");
        addCall(clqd);
        showLoadingDialog();
        clqd.enqueue(new BaseResponseListCallBack<CLQDBean>(this) {
            @Override
            protected void onSuccess(List<CLQDBean> data, String SucMessage) {
                if (data == null || data.size() == 0) {
                    return;
                }
                fileList = data;
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    public void getData() {
        Map<String, String> map = new HashMap<>();

        map.put("code", code);
        showLoadingDialog();
        Call call = RetrofitUtils.createApi(MyApiServer.class).getData("632156", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<DataTransferModel>(this) {
            @Override
            protected void onSuccess(DataTransferModel data, String SucMessage) {
                setView(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    private void setView(DataTransferModel data) {

        mBinding.myNlTeam.setText(data.getTeamName());//团队
        mBinding.myNlXindaiUserName.setText(data.getSaleUserName());//信贷专员
        mBinding.myNlNeiqinUserName.setText(data.getInsideJobName());//内勤专员
        mBinding.myNlSendUserName.setText(data.getSenderName());//发件人
        mBinding.myNlReceiveUserName.setText(data.getReceiverName());//收件人

        if (isGps) {
            mBinding.llGps.setVisibility(View.VISIBLE);
            DataTransferModel.GpsApply gpsApply = data.getGpsApply();
            mBinding.myNlApplyWiredCount.setText(gpsApply == null ? "" : gpsApply.getApplyWiredCount() + "");
            mBinding.myNlApplyWirelessCount.setText(gpsApply == null ? "" : gpsApply.getApplyWirelessCount() + "");
            mBinding.myNlCarNo.setText(gpsApply == null ? "" : gpsApply.getCarFrameNo());
            mBinding.myNlMobile.setText(gpsApply == null ? "" : data.getGpsApply().getMobile());//手机号
            mBinding.myNlName.setText(gpsApply.getCustomerName());
            mBinding.myNlNodeSend.setVisibility(View.GONE);
            mBinding.myNlNodeRe.setVisibility(View.GONE);
        } else {
            mBinding.myNlName.setText(data.getCustomerName());
            mBinding.llRefFile.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(data.getFilelist())) {
                String[] split = data.getFilelist().split(",");
                if (split != null && split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        for (int j = 0; j < fileList.size(); j++) {
                            if (TextUtils.equals(fileList.get(j).getId() + "", split[i])) {
                                refFileList.add(fileList.get(j));
                                break;
                            }
                        }
                    }
                    refFileAdapter.notifyDataSetChanged();
                }
            }

        }


        mBinding.myNlCode.setText(data.getBizCode());
        if (TextUtils.equals("2", data.getSendType())) {
            mBinding.myNlSendType.setText("快递");
        } else if (TextUtils.equals("1", data.getSendType())) {
            mBinding.myNlSendType.setText("线下");
        }
        mBinding.myNlNodeSend.setText(NodeHelper.getNameOnTheCode(data.getFromNodeCode()));
        mBinding.myNlNodeRe.setText(NodeHelper.getNameOnTheCode(data.getToNodeCode()));

        mBinding.myNlLogisticsCode.setText(data.getLogisticsCode());
        mBinding.myNlLogisticsCompany.setText(DataDictionaryHelper.getValueOnTheKey(data.getLogisticsCompany(), mCompany));
        mBinding.myNlSendDatetime.setText(DateUtil.formatStringData(data.getSendDatetime(), DateUtil.DEFAULT_DATE_FMT));
        mBinding.myNlOther.setText(data.getRemark());

    }

}
