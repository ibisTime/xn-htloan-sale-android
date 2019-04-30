package com.cdkj.wzcd.module.main.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCodePresenter;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityUpDataPhoneBinding;

import java.util.HashMap;
import java.util.LinkedHashMap;

import retrofit2.Call;

public class UpDataPhoneActivity extends AbsBaseLoadActivity implements SendCodeInterface {
    ActivityUpDataPhoneBinding mBinding;
    private SendPhoneCodePresenter mSendCOdePresenter;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, UpDataPhoneActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_up_data_phone, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("修改手机号");

        mSendCOdePresenter = new SendPhoneCodePresenter(this);

        mBinding.btnCheckCode.setOnClickListener(v -> {
            mSendCOdePresenter.sendCodeRequest(mBinding.etPhone.getText().toString(), "630052", null,mBinding.btnCheckCode, UpDataPhoneActivity.this);
        });

        mBinding.btnLoadCheckCode.setOnClickListener(v -> {
            mSendCOdePresenter.sendCodeRequest(mBinding.etLoadPhone.getText().toString(), "630052", null,mBinding.btnLoadCheckCode, UpDataPhoneActivity.this);
        });

        mBinding.btnYesUpdataPsw.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mBinding.etLoadPhone.getText().toString())) {
                UITipDialog.showFail(UpDataPhoneActivity.this, "请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(mBinding.etLoadCheckCode.getText().toString())) {
                UITipDialog.showFail(UpDataPhoneActivity.this, "请输入验证码");
                return;
            }

            if (TextUtils.isEmpty(mBinding.etPhone.getText().toString())) {
                UITipDialog.showFail(UpDataPhoneActivity.this, "请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(mBinding.etCheckCode.getText().toString())) {
                UITipDialog.showFail(UpDataPhoneActivity.this, "请输入验证码");
                return;
            }

            reqeustUpPhoneNumber();
        });


    }

    /**
     * 请求接口修改手机号
     */
    private void reqeustUpPhoneNumber() {
        HashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        showLoadingDialog();
        hashMap.put("newMobile", mBinding.etPhone.getText().toString());
        hashMap.put("newCaptcha", mBinding.etCheckCode.getText().toString());
        hashMap.put("oldMobile", mBinding.etLoadPhone.getText().toString());
        hashMap.put("oldCaptcha", mBinding.etLoadCheckCode.getText().toString());
        hashMap.put("userId", SPUtilHelper.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("630052", StringUtils.getJsonToString(hashMap));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(UpDataPhoneActivity.this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    SPUtilHelper.saveUserPhoneNum(mBinding.etPhone.getText().toString());
                    UITipDialog.showSuccess(UpDataPhoneActivity.this, "手机号修改成功", dialog -> finish());
                } else {
                    UITipDialog.showFail(UpDataPhoneActivity.this, "手机号修改失败");
                }
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                // super.onReqFailure(errorCode, errorMessage);
                Log.i("pppppp", "onReqFailure: " + errorMessage);
                UITipDialog.showFail(UpDataPhoneActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    public void CodeSuccess(String msg, TextView view) {
        mSubscription.add(AppUtils.startCodeDown(60, view));
    }

    @Override
    public void CodeFailed(String code, String msg) {

        UITipDialog.showFail(UpDataPhoneActivity.this, msg);
    }

    @Override
    public void StartSend() {
        showLoadingDialog();
    }

    @Override
    public void EndSend() {
        disMissLoading();
    }
}
