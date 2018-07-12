package com.cdkj.wzcd;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.ExpectActivity;
import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityMainBinding;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.model.NodeModel;
import com.cdkj.wzcd.module.business.audit.AuditListActivity;
import com.cdkj.wzcd.module.business.bank_loan.BankLoanListActivity;
import com.cdkj.wzcd.module.business.cldy.BssCldyListActivity;
import com.cdkj.wzcd.module.business.cllh.CllhListActivity;
import com.cdkj.wzcd.module.business.credit.BssCreditListActivity;
import com.cdkj.wzcd.module.business.gps_install.GPSInstallListActivity;
import com.cdkj.wzcd.module.business.interview.InterviewActivity;
import com.cdkj.wzcd.module.cartool.gps.GpsListActivity;
import com.cdkj.wzcd.module.cartool.history.HistoryUserActivity;
import com.cdkj.wzcd.module.cartool.uservoid.UserToVoidActivity;
import com.cdkj.wzcd.module.datatransfer.DataTransferActivity2;
import com.cdkj.wzcd.module.user.SignInActivity;
import com.cdkj.wzcd.tencent.TencentLogoutHelper;
import com.cdkj.wzcd.tencent.logininterface.TencentLogoutInterface;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.cdkj.wzcd.util.UserHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.wzcd.util.DataDictionaryHelper.budget_orde_biz_typer;
import static com.cdkj.wzcd.util.UserHelper.NQZY;
import static com.cdkj.wzcd.util.UserHelper.YWY;
import static com.cdkj.wzcd.util.UserHelper.ZHRY;

public class MainActivity extends AbsBaseLoadActivity implements TencentLogoutInterface {

    // 节点列表
    public static List<NodeModel> BASE_NODE_LIST = new ArrayList<>();
    // 业务种类
    public static List<DataDictionary> BASE_BIZ_TYPE = new ArrayList<>();

    private TencentLogoutHelper mLogoutHelper;

    private UserModel mUserModel;
    private ActivityMainBinding mBinding;

    private int PHOTOFLAG = 111;

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

        mLogoutHelper = new TencentLogoutHelper(this);

        initListener();

        NodeHelper.getNodeBaseDataRequest(this, "", "", new NodeHelper.NodeInterface() {
            @Override
            public void onSuccess(List<NodeModel> list) {
                BASE_NODE_LIST.clear();
                BASE_NODE_LIST.addAll(list);


                BizTypeHelper.getBizTypeBaseDataRequest(MainActivity.this, budget_orde_biz_typer, new BizTypeHelper.BizTypeInterface() {
                    @Override
                    public void onSuccess(List<DataDictionary> list) {

                        BASE_BIZ_TYPE.clear();
                        BASE_BIZ_TYPE.addAll(list);

                        getUserInfoRequest(true);

                    }

                    @Override
                    public void onReqFailure(String errorCode, String errorMessage) {

                    }
                });

            }

            @Override
            public void onReqFailure(String errorCode, String errorMessage) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserInfoRequest(false);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfoRequest(boolean isShowDialog) {

        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelper.getUserId());
        map.put("token", SPUtilHelper.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("630067", StringUtils.getJsonToString(map));

        addCall(call);

        if (isShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserModel>(this) {
            @Override
            protected void onSuccess(UserModel data, String SucMessage) {
                mUserModel = data;

                saveUserInfo(data);
                setView(data);

                getDataCount();
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFail(MainActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                if (isShowDialog) disMissLoading();
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
        SPUtilHelper.saveRoleCode(data.getRoleCode());
        SPUtilHelper.saveTeamCode(data.getTeamCode());
    }

    private void setView(UserModel data) {

        ImgUtils.loadQiNiuBorderLogo(this, data.getPhoto(), mBinding.imAvatar, R.color.white);
        mBinding.tvNick.setText(data.getLoginName() + "——" + data.getRealName());
        mBinding.tvCompany.setText(data.getCompanyName());

        if (TextUtils.equals(data.getRoleCode(), ZHRY)){ // 驻行人员
            mBinding.tvRole.setText("[驻行人员]");

            mBinding.mySrZrsq.setVisibility(View.GONE);
            mBinding.lineZrsq.setVisibility(View.GONE);

            mBinding.mySrGpsaz.setVisibility(View.GONE);
            mBinding.lineGpsaz.setVisibility(View.GONE);

            mBinding.mySrCllh.setVisibility(View.GONE);
            mBinding.lineCllh.setVisibility(View.GONE);

            mBinding.llUtil.setVisibility(View.GONE);

            mBinding.mySrAudit.setVisibility(View.VISIBLE);
            mBinding.lineAudit.setVisibility(View.VISIBLE);
        }else if (TextUtils.equals(data.getRoleCode(), YWY)){// 业务员
            mBinding.tvRole.setText("[业务员]");

        }else if (TextUtils.equals(data.getRoleCode(), NQZY)){// 内勤专员
            mBinding.tvRole.setText("[内勤专员]");

        }else {
            mBinding.tvRole.setText("[其他]");
        }

    }

    private void initListener() {
        mBinding.llUser.setOnClickListener(view -> {
            if (mUserModel == null)
                return;

//            UserInfoActivity.open(this, mUserModel);
            ImageSelectActivity.launch(this, PHOTOFLAG, false);
        });

        mBinding.flRight.setOnClickListener(view -> {

            mLogoutHelper.logout();
        });

        mBinding.mySrCredit.setOnClickListener(view -> {
            BssCreditListActivity.open(this);
        });

        //准入申请
        mBinding.mySrZrsq.setOnClickListener(v -> {
//            JoinApplyListActivity.open(this);
            ExpectActivity.open(this);
        });

        //面签
        mBinding.mySrMq.setOnClickListener(v -> {
            InterviewActivity.open(this);
        });

        //gps 安装
        mBinding.mySrGpsaz.setOnClickListener(v -> {
            GPSInstallListActivity.open(this);
        });

        //车辆落户
        mBinding.mySrCllh.setOnClickListener(view -> {
            CllhListActivity.open(this);
        });

        //车辆抵押
        mBinding.mySrCldy.setOnClickListener(view -> {
            BssCldyListActivity.open(this);
        });

        //结清审核
        mBinding.mySrAudit.setOnClickListener(view -> {
            AuditListActivity.open(this);
        });

        //资料传递
        mBinding.mySrZlcd.setOnClickListener(view -> {
            DataTransferActivity2.open(this);
        });

        //客户作废
        mBinding.mySrKhzf.setOnClickListener(view -> {
            UserToVoidActivity.open(this);
        });

        //GPS申领
        mBinding.mySrGpssl.setOnClickListener(view -> {
            GpsListActivity.open(this);
        });

        //历史客户
        mBinding.mySrLskh.setOnClickListener(view -> {
            HistoryUserActivity.open(this);
        });

        // 银行放款
        mBinding.mySrLoan.setOnClickListener(view -> {
            BankLoanListActivity.open(this);
//            BankLoanInputActivity.open(this,"12");

        });

    }


    /**
     * 退出登录
     */
    private void logOut() {
        showDoubleWarnListen("你确定要退出当前账号吗?", view -> {
            SPUtilHelper.logOutClear();
            UITipDialog.showSuccess(this, "退出成功",dialogInterface -> {
                finish();

                SignInActivity.open(this,false);
            });
        });
    }

    @Override
    public void emptyLoginUser() {
        // 未登陆腾讯云直接退出
        logOut();
    }

    @Override
    public void onLogoutSDKSuccess() {
        // 退出腾讯云成功
        logOut();
    }

    @Override
    public void onLogoutSDKFailed(String module, int errCode, String errMsg) {
        LogUtil.E("退出腾讯云失败 errCode = " + errCode + ", errMsg = " + errMsg);
        ToastUtil.show(this, errMsg);
    }

    /**
     * 资料传递待处理数量
     */
    private void getDataCount(){
        Map<String, Object> map = new HashMap<>();

        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录不用请求
            return;
        }

        List<String> statusList = new ArrayList<>();
        statusList.add("0");
        statusList.add("1");
        statusList.add("3");

        if (UserHelper.isYWY()){
            map.put("userId", SPUtilHelper.getUserId());
            map.put("statusList", statusList);
        }

        Call call = RetrofitUtils.createApi(MyApiServer.class).getDataTransferList("632157", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseListCallBack<DataTransferModel>(this) {
            @Override
            protected void onSuccess(List<DataTransferModel> data, String SucMessage) {
                if (data == null)
                    return;

                mBinding.mySrZlcd.setPointCount(data.size());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
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
        Call call = RetrofitUtils.getBaseAPiService().successRequest("630059", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                UITipDialog.showSuccess(MainActivity.this, "头像更改成功");
                ImgUtils.loadQiniuLogo(MainActivity.this, key, mBinding.imAvatar);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                UITipDialog.showFail(MainActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
