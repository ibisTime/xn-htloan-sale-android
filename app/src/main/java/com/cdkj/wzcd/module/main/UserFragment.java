package com.cdkj.wzcd.module.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.FragmentUserBinding;
import com.cdkj.wzcd.module.main.user.UpDataPhoneActivity;
import com.cdkj.wzcd.module.user.SignInActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * 我的Fragment
 */
public class UserFragment extends BaseLazyFragment {


    private FragmentUserBinding mBinding;

    public static Fragment getInstance() {
        UserFragment homeFragment = new UserFragment();
        return homeFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_user, container, false);
        initListener();
        getUserDetial();
        return mBinding.getRoot();
    }

    /**
     * 获取用户详情
     */
    private void getUserDetial() {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelper.getUserId());
        map.put("token", SPUtilHelper.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("630067", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserModel>(mActivity) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {
                saveUserInfo(data);
                setView(data);

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFail(mActivity, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(UserModel data) {
        ImgUtils.loadQiNiuBorderLogo(mActivity, data.getPhoto(), mBinding.ivHead, R.color.white);
        mBinding.tvRealName.setText(data.getRealName());
        mBinding.tvRoleName.setText(data.getLoginName());
        mBinding.tvMessage.setText(data.getCompanyName() + "-" + data.getDepartmentName() + "-" + data.getPostName());
        mBinding.myNlLoginName.setText(data.getLoginName());
        mBinding.myNlPhone.setText(data.getMobile());
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
        SPUtilHelper.saveRoleCode(data.getRoleCode());
        SPUtilHelper.saveTeamCode(data.getTeamCode());
    }

    private void initListener() {

        mBinding.myNlPhone.setOnClickListener(view -> {
            UpDataPhoneActivity.open(mActivity);
        });

        mBinding.myNlUpPsw.setOnClickListener(view -> {
            FindPwdActivity.open(mActivity, "修改密码", SPUtilHelper.getUserPhoneNum());
        });

        mBinding.btnConfirm.setOnClickListener(v -> {
            logOut();
        });
    }

    /**
     * 退出登录
     */
    private void logOut() {
        showDoubleWarnListen("你确定要退出当前账号吗?", view -> {
            SPUtilHelper.logOutClear();
            UITipDialog.showSuccess(mActivity, "退出成功", dialogInterface -> {
                getActivity().finish();
                SignInActivity.open(mActivity, false);
            });
        });
    }

    protected void showDoubleWarnListen(String str, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isHidden()) {
            return;
        }

        CommonDialog commonDialog = new CommonDialog(mActivity).builder()
                .setTitle(getString(com.cdkj.baselibrary.R.string.tips)).setContentMsg(str)
                .setPositiveBtn(getString(com.cdkj.baselibrary.R.string.sure), onPositiveListener)
                .setNegativeBtn(getString(com.cdkj.baselibrary.R.string.cancel), null, false);

        commonDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.myNlPhone.setText(SPUtilHelper.getUserPhoneNum());
    }
}
