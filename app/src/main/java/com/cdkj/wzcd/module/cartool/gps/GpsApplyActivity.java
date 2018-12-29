package com.cdkj.wzcd.module.cartool.gps;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityGpsApplyBinding;
import com.cdkj.wzcd.model.GpsApplyModel;
import com.cdkj.wzcd.model.PickerViewDataBean;
import com.cdkj.wzcd.model.TeamBean;
import com.cdkj.wzcd.model.ZrdModel;
import com.cdkj.wzcd.module.cartool.uservoid.ZrdListActivity;
import com.lljjcoder.citylist.Toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;


public class GpsApplyActivity extends AbsBaseLoadActivity {
    private ActivityGpsApplyBinding mBinding;
    private ZrdModel zrdModel;
    private String code;
    private int selectNumberTye = -1;
    private int selectTeamOptions = -1;
    private ArrayList<PickerViewDataBean> typeDatas;
    private List<TeamBean> teamData;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, GpsApplyActivity.class);
            context.startActivity(intent);
        }
    }

    public static void open(Context context, String code) {
        if (context != null) {
            Intent intent = new Intent(context, GpsApplyActivity.class);
            intent.putExtra(CdRouteHelper.DATA_SIGN, code);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_gps_apply, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("申领");

        init();
        initListener();
        getGPS();
    }

    private void init() {
        if (getIntent() != null) {
            code = getIntent().getStringExtra(CdRouteHelper.DATA_SIGN);
        }
        typeDatas = new ArrayList<>();
        PickerViewDataBean bean1 = new PickerViewDataBean();
        bean1.setKey("本部");
        bean1.setValue("1");
        typeDatas.add(bean1);
        PickerViewDataBean bean2 = new PickerViewDataBean();
        bean2.setKey("分部");
        bean2.setValue("2");
        typeDatas.add(bean2);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {

            if (check()) {
                if (TextUtils.isEmpty(code)) {
                    applyRequest();

                } else {
                    applyAgainRequest();
                }
            }
        });

        mBinding.myNlMateUser.setOnClickListener(view -> {
            ZrdListActivity.open(this);
        });

        mBinding.myNlType.setOnClickListener(v -> {
            OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(this, (options1, options2, options3, v1) -> {
                selectNumberTye = options1;
                mBinding.myNlType.setText(typeDatas.get(options1).getKey());

                if (TextUtils.equals("1", typeDatas.get(options1).getValue())) {
                    mBinding.myNlMateUser.setVisibility(View.VISIBLE);
                    if (zrdModel != null) {
                        mBinding.llRoot.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.llRoot.setVisibility(View.GONE);
                    }

                    mBinding.myNlTeamCode.setVisibility(View.GONE);
                } else if (TextUtils.equals("2", typeDatas.get(options1).getValue())) {
                    mBinding.myNlMateUser.setVisibility(View.GONE);
                    mBinding.llRoot.setVisibility(View.GONE);
                    mBinding.myNlTeamCode.setVisibility(View.VISIBLE);
                }
            }).build();
            optionsPickerView.setPicker(typeDatas);
            optionsPickerView.setSelectOptions(selectNumberTye);
            optionsPickerView.show();

        });

        mBinding.myNlTeamCode.setOnClickListener(v -> {

            if (teamData == null || teamData.size() == 0) {
                getTeamData();
                return;
            }
            showTeamDialog();
        });

    }

    private void showTeamDialog() {

        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(this, (options1, options2, options3, v1) -> {
            selectTeamOptions = options1;
            mBinding.myNlTeamCode.setText(teamData.get(options1).getName());

        }).build();
        optionsPickerView.setPicker(teamData);
        optionsPickerView.setSelectOptions(selectTeamOptions);
        optionsPickerView.show();

    }


    private boolean check() {
        // 申领个数
        if (TextUtils.isEmpty(mBinding.myElYouNumber.getText())) {
            ToastUtils.showShortToast(this, "请填写申领个数");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myElWuNumber.getText())) {
            ToastUtils.showShortToast(this, "请填写申领个数");
            return false;
        }

        if (selectNumberTye == -1) {
            ToastUtils.showShortToast(this, "请选择申请类型");
            return false;
        }

        if (TextUtils.equals("1", typeDatas.get(selectNumberTye).getValue())) {
            //本部的  客户信息必填
            if (zrdModel == null) {
                ToastUtils.showShortToast(this, "请匹配客户");
                return false;
            }
        } else if (TextUtils.equals("2", typeDatas.get(selectNumberTye).getValue())) {
            //分部的  团队公司必填
            if (selectTeamOptions == -1) {
                ToastUtils.showShortToast(this, "请匹配团队");
                return false;
            }
        } else {
            ToastUtils.showShortToast(this, "请选择申请类型");
            return false;
        }

        return true;
    }


    private void getGPS() {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        Map<String, String> map = new HashMap<>();

        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getGps("632716", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<GpsApplyModel>(this) {
            @Override
            protected void onSuccess(GpsApplyModel model, String SucMessage) {
                ZrdModel mode = new ZrdModel();
                mode.setCode(model.getBudgetOrderCode());
                mode.setApplyUserName(model.getCustomerName());
                mode.setCarFrameNo(model.getCarFrameNo());
                mode.setMobile(model.getMobile());

                zrdModel = mode;

                setView(mode);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    private void getTeamData() {
        Call<BaseResponseListModel<TeamBean>> teamDataCall = RetrofitUtils.createApi(MyApiServer.class).getTeamData("630197", "{}");
        showLoadingDialog();
        teamDataCall.enqueue(new BaseResponseListCallBack<TeamBean>(this) {
            @Override
            protected void onSuccess(List<TeamBean> data, String SucMessage) {
                teamData = data;
                showTeamDialog();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 重新申领
     */
    private void applyAgainRequest() {
        Map<String, Object> map = new HashMap<>();

        map.put("applyWiredCount", mBinding.myElYouNumber.getText());//有线个数
        map.put("applyWirelessCount", mBinding.myElWuNumber.getText());//无线个数
        map.put("applyReason", mBinding.myElExplain.getText());
        map.put("applyUser", SPUtilHelper.getUserId());
        map.put("type", "2");//(1 公司 2 个人
        map.put("code", code);//(1 公司 2 个人
        if (zrdModel != null) {
            map.put("applyUsername", zrdModel.getApplyUserName());
            map.put("mobile", zrdModel.getMobile());
            map.put("carFrameNo", zrdModel.getCarFrameNo());
            map.put("budgetOrderCode", zrdModel.getCode());
            map.put("customerName", zrdModel.getApplyUserName());
        }

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632713", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(GpsApplyActivity.this, "申领成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(GpsApplyActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void applyRequest() {
        Map<String, Object> map = new HashMap<>();

        map.put("applyWiredCount", mBinding.myElYouNumber.getText());//有线个数
        map.put("applyWirelessCount", mBinding.myElWuNumber.getText());//无线个数
        map.put("applyReason", mBinding.myElExplain.getText());
        map.put("applyUser", SPUtilHelper.getUserId());
        map.put("type", "2");//(1 公司 2 个人

        map.put("applyType", typeDatas.get(selectNumberTye).getValue());
        if (TextUtils.equals("1", typeDatas.get(selectNumberTye).getValue())) {
            //本部设置  匹配客户数据
            if (zrdModel != null) {
                map.put("applyUsername", zrdModel.getApplyUserName());
                map.put("mobile", zrdModel.getMobile());
                map.put("carFrameNo", zrdModel.getCarFrameNo());
                map.put("budgetOrderCode", zrdModel.getCode());
                map.put("customerName", zrdModel.getApplyUserName());
            }
        } else if (TextUtils.equals("2", typeDatas.get(selectNumberTye).getValue())) {
            //分部设置 团队数据
            map.put("teamCode", teamData.get(selectTeamOptions).getCode());
        }

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632710", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(GpsApplyActivity.this, "申领成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(GpsApplyActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    private void setView(ZrdModel zrdModel) {
        mBinding.llRoot.setVisibility(View.VISIBLE);
        mBinding.myNlMateUser.setText(zrdModel.getCode());
        mBinding.myItemNlName.setContent(zrdModel.getApplyUserName());
        mBinding.myItemNlPhone.setContent(zrdModel.getMobile());
        mBinding.myItemNlCarNumber.setContent(zrdModel.getCarFrameNo());
    }

    @Subscribe
    public void getZrdModel(ZrdModel zrdModel) {

        if (zrdModel == null)
            return;
        this.zrdModel = zrdModel;
        setView(zrdModel);
    }

}
