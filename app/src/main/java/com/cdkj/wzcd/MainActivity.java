package com.cdkj.wzcd;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityMainBinding;
import com.cdkj.wzcd.module.business.credit.BssCreditListActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AbsBaseLoadActivity {

    private ActivityMainBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        getUserInfoRequest(true);

        initListener();
    }


    /**
     * 获取用户信息
     */
    public void getUserInfoRequest(boolean isShowdialog) {

        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelper.getUserId());
        map.put("token", SPUtilHelper.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("630067", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowdialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserModel>(this) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {

                saveUserInfo(data);
                setView(data);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFail(MainActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                if (isShowdialog) disMissLoading();
            }
        });
    }

    /**
     * 保存用户相关信息
     *
     * @param data
     */
    private void saveUserInfo(UserModel data) {
        SPUtilHelper.saveisTradepwdFlag(data.isTradepwdFlag());
        SPUtilHelper.saveUserPhoneNum(data.getMobile());
        SPUtilHelper.saveUserName(data.getRealName());
        SPUtilHelper.saveUserNickName(data.getNickname());
        SPUtilHelper.saveUserPhoto(data.getPhoto());
    }

    private void setView(UserModel data) {

        ImgUtils.loadQiNiuBorderLogo(this, data.getPhoto(), mBinding.imAvatar, R.color.white);
        mBinding.tvNick.setText(TextUtils.isEmpty(data.getLoginName()) ? "暂无" : data.getLoginName());

    }

    private void initListener() {
        mBinding.mySrZxdc.setOnClickListener(view -> {
            BssCreditListActivity.open(this);
        });
    }
}
