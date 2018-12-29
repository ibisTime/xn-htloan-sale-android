package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.DataFileAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivitySendAndExamineBinding;
import com.cdkj.wzcd.model.CLQDBean;
import com.cdkj.wzcd.model.DataFileModel;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

public class SendAndExamineActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivitySendAndExamineBinding mBinding;

    private List<CLQDBean> refFileList = new ArrayList<>();
    private DataFileAdapter refFileAdapter;

    private List<DataFileModel> sendFileList = new ArrayList<>();
    private DataFileAdapter sendFileAdapter;
    private boolean isGps;
    private List<CLQDBean> fileList;

    public static void open(Context context, String code, boolean isGps) {
        if (context != null) {
            Intent intent = new Intent(context, SendAndExamineActivity.class);
            intent.putExtra(DATA_SIGN, code);
            intent.putExtra("isGps", isGps);
            context.startActivity(intent);
        }
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_send_and_examine, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("收件");

        if (getIntent() == null) {
            return;
        }
        code = getIntent().getStringExtra(DATA_SIGN);
        isGps = getIntent().getBooleanExtra("isGps", false);

        if (isGps) {
            mBinding.llGps.setVisibility(View.VISIBLE);
            getData();
        } else {
            getCLQD();
        }

        initAdapter();
        initListener();


    }

    private void initAdapter() {
        refFileAdapter = new DataFileAdapter(refFileList);
        mBinding.rvRefFile.setLayoutManager(getLinearLayoutManager(true));
        mBinding.rvRefFile.setAdapter(refFileAdapter);

//        sendFileAdapter = new DataFileAdapter(sendFileList);
//        mBinding.rvSendFile.setLayoutManager(getLinearLayoutManager(false));
//        mBinding.rvSendFile.setAdapter(sendFileAdapter);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            pickUpAndPassRequest();
        });

        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            pickUpAndReissueRequest();
        });
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
                getData();
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

        DataTransferModel.GpsApply gpsApply = data.getGpsApply();
        if (isGps) {
            mBinding.llGps.setVisibility(View.VISIBLE);
            //gps  没有发件收件节点
            mBinding.myNlNodeSend.setVisibility(View.GONE);
            mBinding.myNlNodeRe.setVisibility(View.GONE);
            mBinding.myNlTeam.setText(data.getTeamName());
            mBinding.myNlApplyName.setText(data.getUserName());//申请人姓名
            mBinding.myNlApplyRole.setText(data.getUserRole());//申请人角色
            mBinding.myNlName.setText(gpsApply.getCustomerName());//客户姓名

            mBinding.myNlApplyWirelessCount.setText(gpsApply == null ? "" : data.getGpsApply().getApplyWirelessCount() + "");//车架号
            mBinding.myNlApplyWiredCount.setText(gpsApply == null ? "" : data.getGpsApply().getApplyWiredCount() + "");//车架号

            if (TextUtils.isEmpty(gpsApply.getCarFrameNo())) {
                mBinding.myNlCarNumber.setVisibility(View.GONE);
            } else {
                mBinding.myNlCarNumber.setText(gpsApply.getCarFrameNo());//车架号
            }
            if (TextUtils.isEmpty(gpsApply.getMobile())) {
                mBinding.myNlMobile.setVisibility(View.GONE);
            } else {
                mBinding.myNlMobile.setText(gpsApply.getMobile());//手机号
            }

        } else {
            mBinding.myNlName.setText(data.getCustomerName());//客户姓名
            //材料清单
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
        mBinding.myNlNodeSend.setText(NodeHelper.getNameOnTheCode(data.getFromNodeCode()));
        mBinding.myNlNodeRe.setText(NodeHelper.getNameOnTheCode(data.getToNodeCode()));
        mBinding.myElSendNote.setText(data.getSendNote());


//        if (!TextUtils.isEmpty(data.getRefFileList())) {
//            mBinding.llRefFile.setVisibility(View.VISIBLE);
//            setReFileListData(data.getRefFileList());
//        } else {
//            mBinding.llRefFile.setVisibility(View.GONE);
//        }
        //寄送材料清单
//        if (!TextUtils.isEmpty(data.getSendFileList())) {
//            mBinding.llSendFile.setVisibility(View.VISIBLE);
//
//            setSendFileListData(data.getSendFileList());
//        } else {
//            mBinding.llSendFile.setVisibility(View.GONE);
//        }

        DataDictionaryHelper.getValueOnTheKeyRequest(this, DataDictionaryHelper.send_type, data.getSendType(), data1 -> {
            mBinding.myNlSendType.setText(data1.getDvalue());

            if (TextUtils.equals(data1.getDvalue(), "快递")) {
                mBinding.llLogistics.setVisibility(View.VISIBLE);

                DataDictionaryHelper.getValueOnTheKeyRequest(this, DataDictionaryHelper.kd_company, data.getLogisticsCompany(), data2 -> {
                    mBinding.myNlLogisticsCompany.setText(data2.getDvalue());
                });

                mBinding.myNlLogisticsCode.setText(data.getLogisticsCode());
            }

        });

        mBinding.myNlSendDatetime.setText(DateUtil.formatStringData(data.getSendDatetime(), DateUtil.DEFAULT_DATE_FMT));
    }

    private void setReFileListData(String reFile) {

        String[] reFileStr = reFile.split(",");

        List<DataFileModel> list = new ArrayList<>();

        for (String file : reFileStr) {
            DataFileModel model = new DataFileModel();
            model.setFile(file);
            model.setChoice(false);
            list.add(model);
        }
        refFileList.clear();
//        refFileList.addAll(list);
        refFileAdapter.notifyDataSetChanged();
    }

    private void setSendFileListData(String sendFile) {

        String[] sendFileStr = sendFile.split(",");

        List<DataFileModel> list = new ArrayList<>();

        for (String file : sendFileStr) {
            DataFileModel model = new DataFileModel();
            model.setFile(file);
            model.setChoice(false);
            list.add(model);
        }
        sendFileList.clear();
        sendFileList.addAll(list);
        sendFileAdapter.notifyDataSetChanged();
    }

    /**
     * 收件并审核通过
     */
    private void pickUpAndPassRequest() {
        if (TextUtils.isEmpty(code))
            return;

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("code", code);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("remark", mBinding.myElSendNote.getText());
        map.put("receiver", SPUtilHelper.getUserId());//收件人
        Call call = RetrofitUtils.getBaseAPiService().successRequest("632151", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    UITipDialog.showSuccess(SendAndExamineActivity.this, "操作成功", dialogInterface -> {
                        finish();
                    });
                } else {
                    UITipDialog.showFail(SendAndExamineActivity.this, "操作失败");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 收件并补发
     */
    private void pickUpAndReissueRequest() {
        if (TextUtils.isEmpty(code))
            return;

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("code", code);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("remark", mBinding.myElSendNote.getText());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("632152", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    UITipDialog.showSuccess(SendAndExamineActivity.this, "操作成功", dialogInterface -> {
                        finish();
                    });
                } else {
                    UITipDialog.showFail(SendAndExamineActivity.this, "操作失败");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
