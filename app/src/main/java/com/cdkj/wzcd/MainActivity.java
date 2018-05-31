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
import com.cdkj.wzcd.module.business.cldy.BssCldyListActivity;
import com.cdkj.wzcd.module.business.cllh.CllhListActivity;
import com.cdkj.wzcd.module.business.credit.BssCreditListActivity;
import com.cdkj.wzcd.module.business.zxdc.face_view.FaceInterviewActivity;
import com.cdkj.wzcd.module.business.zxdc.gps_install.GPSInstallListActivity;
import com.cdkj.wzcd.module.business.zxdc.join_approval.JoinApplyActivity;
import com.cdkj.wzcd.module.cartool.gps.GpsActivity;
import com.cdkj.wzcd.module.cartool.history.HistoryUserActivity;
import com.cdkj.wzcd.module.cartool.uservoid.UserToVoidActivity;
import com.cdkj.wzcd.module.datatransfer.DataTransferActivity;

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

        if (false){
            mBinding.mySrZrsq.setVisibility(View.GONE);
            mBinding.lineZrsq.setVisibility(View.GONE);

            mBinding.mySrGpsaz.setVisibility(View.GONE);
            mBinding.lineGpsaz.setVisibility(View.GONE);

            mBinding.mySrCllh.setVisibility(View.GONE);
            mBinding.lineCllh.setVisibility(View.GONE);

            mBinding.llUtil.setVisibility(View.GONE);
        }

    }

    private void initListener() {
        mBinding.mySrZxdc.setOnClickListener(view -> {
            BssCreditListActivity.open(this);
        });

        //准入申请
        mBinding.mySrZrsq.setOnClickListener(v -> {
            JoinApplyActivity.open(this);
        });

        //面签
        mBinding.mySrMq.setOnClickListener(v -> {
            FaceInterviewActivity.open(this);
        });

        //gps 安装
        mBinding.mySrGpsaz.setOnClickListener(v -> {
            GPSInstallListActivity.open(this);
        });

        mBinding.mySrCllh.setOnClickListener(view -> {
            //车辆落户
            CllhListActivity.open(this);
        });
        mBinding.mySrCldy.setOnClickListener(view -> {
            //车辆抵押
            BssCldyListActivity.open(this);
        });
        mBinding.mySrZlcd.setOnClickListener(view -> {
            //资料上传
            DataTransferActivity.open(this);
        });
        mBinding.mySrKhzf.setOnClickListener(view -> {
            //客户作废
            UserToVoidActivity.open(this);
        });
        mBinding.mySrGpssl.setOnClickListener(view -> {
            //GPS申领
            GpsActivity.open(this);
        });
        mBinding.mySrLskh.setOnClickListener(view -> {
            //历史客户
            HistoryUserActivity.open(this);
        });


    }
}
