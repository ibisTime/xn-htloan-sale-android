package com.cdkj.wzcd.module.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.model.eventmodels.EventFinishMain;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityUserInfoBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/6/3.
 */

public class UserInfoActivity extends AbsBaseLoadActivity{

    private ActivityUserInfoBinding mBinding;

    private int PHOTOFLAG = 111;
    private UserModel mUserInfo;

    /**
     * @param context
     */
    public static void open(Context context, UserModel userModel) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("userModel",userModel);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_info, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("编辑资料");

        if (getIntent() != null) {
            mUserInfo = getIntent().getParcelableExtra(CdRouteHelper.DATA_SIGN);
        }

        setShowData();
        initListener();
    }

    /**
     * 设置显示数据
     */
    private void setShowData() {
        if (mUserInfo == null) {
            return;
        }

        ImgUtils.loadQiniuLogo(UserInfoActivity.this, mUserInfo.getPhoto(), mBinding.imgLogo);
        mBinding.rowNickName.setTvRight(mUserInfo.getNickname());

    }

    private void initListener() {
        /*头像*/
        mBinding.layoutLogo.setOnClickListener(view -> ImageSelectActivity.launch(this, PHOTOFLAG, false));

        /*昵称*/
        mBinding.rowNickName.setOnClickListener(view -> {

//            if (mUserInfo != null) {
//                NickModifyActivity.open(this, mUserInfo.getNickname());
//                return;
//            }
//            NickModifyActivity.open(this, "");

        });


        /*退出登录*/
        mBinding.tvLogout.setOnClickListener(view -> logOut());
    }

    /**
     * 退出登录
     */
    private void logOut() {
        showDoubleWarnListen("您确认退出登录?", view -> {
            SPUtilHelper.logOutClear();
            UITipDialog.showSuccess(this, "退出登录成功",dialogInterface -> {
                finish();

                EventBus.getDefault().post(new EventFinishMain());

                SignInActivity.open(this,false);
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(CameraHelper.staticPath);
            LogUtil.E("拍照获取路径" + path);
            showLoadingDialog();
            new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
                @Override
                public void onSuccess(String key) {
                    updateUserPhoto(key);
                }

                @Override
                public void onFal(String info) {
                    disMissLoading();
                }
            }, path);

        }
    }

    /**
     * 更新头像
     *
     * @param key
     */
    private void updateUserPhoto(final String key) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SPUtilHelper.getUserId());
        map.put("photo", key);
        map.put("token", SPUtilHelper.getUserToken());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805080", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(UserInfoActivity.this, "头像更改成功");
                ImgUtils.loadQiniuLogo(UserInfoActivity.this, key, mBinding.imgLogo);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFail(UserInfoActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
