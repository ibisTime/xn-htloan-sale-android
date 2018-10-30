package com.cdkj.wzcd.module.cartool.gps;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityGpsApplyBinding;
import com.cdkj.wzcd.model.ZrdModel;
import com.cdkj.wzcd.module.cartool.uservoid.ZrdListActivity;
import com.lljjcoder.citylist.Toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


public class GpsApplyActivity extends AbsBaseLoadActivity {
    private ActivityGpsApplyBinding mBinding;
    private ZrdModel zrdModel;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, GpsApplyActivity.class);
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

        initListener();
    }


    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                applyRequest();
            }
        });
        mBinding.myNlMateUser.setOnClickListener(view -> {
            ZrdListActivity.open(this);
        });

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
        return true;
    }

    private void applyRequest() {
        Map<String, Object> map = new HashMap<>();

        map.put("applyWiredCount", mBinding.myElYouNumber.getText());//有线个数
        map.put("applyWirelessCount", mBinding.myElWuNumber.getText());//无线个数
        map.put("applyReason", mBinding.myElExplain.getText());
        map.put("applyUser", SPUtilHelper.getUserId());
        map.put("type", "2");//(1 公司 2 个人
        if (zrdModel != null) {
            map.put("applyUsername", zrdModel.getApplyUserName());
            map.put("mobile", zrdModel.getMobile());
            map.put("carFrameNo", zrdModel.getCarFrameNo());
            map.put("budgetOrderCode", zrdModel.getCode());
            map.put("customerName", zrdModel.getApplyUserName());
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

    @Subscribe
    public void getZrdModel(ZrdModel zrdModel) {

        if (zrdModel == null)
            return;
        this.zrdModel = zrdModel;
        mBinding.llRoot.setVisibility(View.VISIBLE);
        mBinding.myNlMateUser.setText(zrdModel.getCode());
        mBinding.myItemNlName.setContent(zrdModel.getApplyUserName());
        mBinding.myItemNlPhone.setContent(zrdModel.getMobile());
        mBinding.myItemNlCarNumber.setContent(zrdModel.getCarFrameNo());
    }
}
