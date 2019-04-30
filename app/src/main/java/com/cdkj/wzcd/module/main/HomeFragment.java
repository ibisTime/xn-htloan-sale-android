package com.cdkj.wzcd.module.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.activitys.ExpectActivity;
import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentHomeBinding;
import com.cdkj.wzcd.module.business.audit.AuditListActivity;
import com.cdkj.wzcd.module.business.bank_loan.BankLoanListActivity;
import com.cdkj.wzcd.module.business.cldy.BssCldyListActivity;
import com.cdkj.wzcd.module.business.cllh.CllhListActivity;
import com.cdkj.wzcd.module.business.credit.BssCreditListActivity;
import com.cdkj.wzcd.module.business.credit.BssCreditListActivity2;
import com.cdkj.wzcd.module.business.gps_install.GPSInstallListActivity;
import com.cdkj.wzcd.module.business.interview.InterviewActivity;
import com.cdkj.wzcd.module.business.interview.InterviewExamineActivity;
import com.cdkj.wzcd.module.cartool.gps.GpsListActivity;
import com.cdkj.wzcd.module.cartool.history.HistoryUserActivity;
import com.cdkj.wzcd.module.cartool.uservoid.UserToVoidActivity;
import com.cdkj.wzcd.module.datatransfer.DataTransferActivity2;
import com.cdkj.wzcd.module.home.front.ZXLRActivity;
import com.cdkj.wzcd.module.home.front.ZXSHActivity;
import com.cdkj.wzcd.module.main.customer.zrdxq.DQZRListActivity;
import com.cdkj.wzcd.module.user.SignInActivity;
import com.cdkj.wzcd.tencent.TencentLogoutHelper;
import com.cdkj.wzcd.tencent.logininterface.TencentLogoutInterface;


/**
 * 首页fragment
 */
public class HomeFragment extends BaseLazyFragment implements TencentLogoutInterface {

    private FragmentHomeBinding mBinding;

    private int PHOTOFLAG = 111;
    private TencentLogoutHelper mLogoutHelper;

    public static Fragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
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
        // Inflate the layout for mActivity fragment

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, container, false);
        mBinding.myFML.build(mActivity, 1);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        mLogoutHelper = new TencentLogoutHelper(this);
        initListener();
//        getUserInfoRequest(true);

    }

    private void initListener() {

        mBinding.llUser.setOnClickListener(view -> {
            ImageSelectActivity.launch(mActivity, PHOTOFLAG, false);
        });

        mBinding.flRight.setOnClickListener(view -> {

            mLogoutHelper.logout();
        });

        //新的点击事件

        //征信发起
        mBinding.mySrZxfq.setOnClickListener(view -> BssCreditListActivity2.open(mActivity));
        //征信录入
        mBinding.mySrZxlr.setOnClickListener(view -> ZXLRActivity.open(mActivity));
        //征信审核
        mBinding.mySrZxsh.setOnClickListener(view -> ZXSHActivity.open(mActivity));
        //面签录入
        mBinding.mySrMqlr.setOnClickListener(view -> InterviewActivity.open(mActivity));
        //面签审核
        mBinding.mySrMqsh.setOnClickListener(view -> InterviewExamineActivity.open(mActivity));
//        InterviewStartDetialsActivity
        //准入申请
        mBinding.mySrZrsq2.setOnClickListener(view -> DQZRListActivity.open(mActivity));//ExpectActivity.open(mActivity)     JoinApplyListActivity.open(mActivity)

        //下面是老的点击事件

        //资信调查
        mBinding.mySrCredit.setOnClickListener(view -> {
            BssCreditListActivity.open(mActivity);
        });

        //准入申请
        mBinding.mySrZrsq.setOnClickListener(v -> {
//            JoinApplyListActivity.open(mActivity);
            ExpectActivity.open(mActivity);
        });

        //面签
        mBinding.mySrMq.setOnClickListener(v -> {
            InterviewActivity.open(mActivity);
        });

        //gps 安装
        mBinding.mySrGpsaz.setOnClickListener(v -> {
            GPSInstallListActivity.open(mActivity);
        });

        //车辆落户 改为  发保合
        mBinding.mySrCllh.setOnClickListener(view -> {
            CllhListActivity.open(mActivity);
        });

        //车辆抵押
        mBinding.mySrCldy.setOnClickListener(view -> {
            BssCldyListActivity.open(mActivity);
        });

        //结清审核
        mBinding.mySrAudit.setOnClickListener(view -> {
            AuditListActivity.open(mActivity);
        });

        //资料传递
        mBinding.mySrZlcd.setOnClickListener(view -> {
            DataTransferActivity2.open(mActivity);
        });

        //客户作废
        mBinding.mySrKhzf.setOnClickListener(view -> {
            UserToVoidActivity.open(mActivity);
        });

        //GPS申领
        mBinding.mySrGpssl.setOnClickListener(view -> {
            GpsListActivity.open(mActivity);
        });

        //历史客户
        mBinding.mySrLskh.setOnClickListener(view -> {
            HistoryUserActivity.open(mActivity);
        });

        // 银行放款
        mBinding.mySrLoan.setOnClickListener(view -> {
            BankLoanListActivity.open(mActivity);
        });
    }


    /**
     * 退出登录
     */
    private void logOut() {
        showDoubleWarnListen("你确定要退出当前账号吗?", view -> {
            SPUtilHelper.logOutClear();
            UITipDialog.showSuccess(mActivity, "退出成功", dialogInterface -> {
                SignInActivity.open(mActivity, false);
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
        ToastUtil.show(mActivity, errMsg);
    }


//    /**
//     * 获取用户信息
//     */
//    public void getUserInfoRequest(boolean isShowDialog) {
//
//        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录不用请求
//            return;
//        }
//
//        Map<String, String> map = new HashMap<>();
//
//        map.put("userId", SPUtilHelper.getUserId());
//        map.put("token", SPUtilHelper.getUserToken());
//
//        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("630067", StringUtils.getJsonToString(map));
//
//        addCall(call);
//
//        if (isShowDialog) showLoadingDialog();
//
//        call.enqueue(new BaseResponseModelCallBack<UserModel>(mActivity) {
//            @Override
//            protected void onSuccess(UserModel data, String SucMessage) {
//                mUserModel = data;
//
//                saveUserInfo(data);
//                setView(data);
////                getDataCount();
//
//                //获取代办事项的数量
//                getTodoData();
//            }
//
//            @Override
//            protected void onReqFailure(String errorCode, String errorMessage) {
//                UITipDialog.showFail(mActivity, errorMessage);
//            }
//
//            @Override
//            protected void onFinish() {
//                if (isShowDialog) disMissLoading();
//            }
//        });
//    }
//
//    /**
//     * 保存用户相关信息
//     *
//     * @param data
//     */
//    private void saveUserInfo(UserModel data) {
//        SPUtilHelper.saveisTradepwdFlag(data.isTradepwdFlag());
//        SPUtilHelper.saveUserPhoneNum(data.getMobile());
//        SPUtilHelper.saveUserName(data.getRealName());
//        SPUtilHelper.saveUserNickName(data.getNickname());
//        SPUtilHelper.saveUserPhoto(data.getPhoto());
//        SPUtilHelper.saveRoleCode(data.getRoleCode());
//        SPUtilHelper.saveTeamCode(data.getTeamCode());
//    }
//
//    private void setView(UserModel data) {
//
//        ImgUtils.loadQiNiuBorderLogo(mActivity, data.getPhoto(), mBinding.imAvatar, R.color.white);
////        mBinding.tvNick.setText(data.getLoginName() + "——" + data.getRealName());
//        mBinding.tvNick.setText(data.getRealName());
//        mBinding.tvCompany.setText(data.getCompanyName());
//
//        if (TextUtils.equals(data.getRoleCode(), ZHRY)) { // 驻行人员
//            mBinding.tvRole.setText("[驻行人员]");
//
//            mBinding.mySrZrsq.setVisibility(View.GONE);
//            mBinding.lineZrsq.setVisibility(View.GONE);
//
//            mBinding.mySrGpsaz.setVisibility(View.GONE);
//            mBinding.lineGpsaz.setVisibility(View.GONE);
//
//            mBinding.mySrCllh.setVisibility(View.GONE);
//            mBinding.lineCllh.setVisibility(View.GONE);
//
//            mBinding.llUtil.setVisibility(View.GONE);
//
//            mBinding.mySrAudit.setVisibility(View.VISIBLE);
//            mBinding.lineAudit.setVisibility(View.VISIBLE);
//        } else if (TextUtils.equals(data.getRoleCode(), YWY)) {// 业务员
//            mBinding.tvRole.setText("[信贷专员]");
//
//        } else if (TextUtils.equals(data.getRoleCode(), NQZY)) {// 内勤专员
//            mBinding.tvRole.setText("[内勤专员]");
//        } else {
//            //与ios保持一致
//            mBinding.tvRole.setText(TextUtils.isEmpty(data.getPostName()) ? "[其他]" : "[" + data.getPostName() + "]");
//        }
//
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK || data == null) {
//            return;
//        }
//        if (requestCode == PHOTOFLAG) {
//            String path = data.getStringExtra(CameraHelper.staticPath);
//            LogUtil.E("拍照获取路径" + path);
//            showLoadingDialog();
//            new QiNiuHelper(mActivity).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
//                @Override
//                public void onSuccess(String key) {
//                    updateUserPhoto(key);
//                }
//
//                @Override
//                public void onFal(String info) {
//                    disMissLoading();
//                }
//            }, path);
//
//        } else if (requestCode == 1) {
//            String path = data.getStringExtra(CameraHelper.staticPath);
//            new QiNiuHelper(mActivity).uploadSingleFile(new QiNiuHelper.QiNiuFileCallBack() {
//                @Override
//                public void onSuccess(String key) {
//                    LogUtil.E("上传成功" + key);
//                    mBinding.myFML.addList(key);
//                }
//
//                @Override
//                public void onFal(String info) {
//                    LogUtil.E("上传失败" + info);
//                }
//
//                @Override
//                public void progress(String key, double percent) {
//                    Log.e("pppppp", "progress: 进度" + percent);
//
//                }
//
//                //完成
//                @Override
//                public void complete() {
//
//                }
//            }, path);
////            uploadSingleFile
//        }
//    }

//    /**
//     * 更新头像
//     *
//     * @param key
//     */
//    private void updateUserPhoto(final String key) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("userId", SPUtilHelper.getUserId());
//        map.put("photo", key);
//        map.put("token", SPUtilHelper.getUserToken());
//        Call call = RetrofitUtils.getBaseAPiService().successRequest("630059", StringUtils.getJsonToString(map));
//        addCall(call);
//        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
//            @Override
//            protected void onSuccess(IsSuccessModes data, String SucMessage) {
//                UITipDialog.showSuccess(mActivity, "头像更改成功");
//                ImgUtils.loadQiniuLogo(mActivity, key, mBinding.imAvatar);
//            }
//
//            @Override
//            protected void onReqFailure(String errorCode, String errorMessage) {
//                UITipDialog.showFail(mActivity, errorMessage);
//            }
//
//            @Override
//            protected void onFinish() {
//                disMissLoading();
//            }
//        });
//    }

//    //获取代办事项的数量
//    private void getTodoData() {
//        showLoadingDialog();
////        Map map = RetrofitUtils.getNodeListMap();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("teamCode", SPUtilHelper.getTeamCode());
//        map.put("roleCode", SPUtilHelper.getRoleCode());
//        Call<BaseResponseModel<TodoModel>> todo = RetrofitUtils.createApi(MyApiServer.class).getTodo("632912", StringUtils.getJsonToString(map));
//        todo.enqueue(new BaseResponseModelCallBack<TodoModel>(mActivity) {
//            @Override
//            protected void onSuccess(TodoModel data, String SucMessage) {
//                mBinding.mySrCredit.setPointCount(data.getCreditTodo());//资信调查
//                mBinding.mySrMq.setPointCount(data.getInterviewTodo());//面签
//                mBinding.mySrGpsaz.setPointCount(data.getGpsInstallTodo());//gps安装;
//                mBinding.mySrCllh.setPointCount(data.getCarSettleTodo());//车辆落户;
//                mBinding.mySrCldy.setPointCount(data.getEntryMortgageTodo());//车辆抵押
//                mBinding.mySrZlcd.setPointCount(data.getLogisticsTodo());//资料传递
//
//                int sub = data.getCreditTodo() + data.getInterviewTodo() + data.getGpsInstallTodo() + data.getCarSettleTodo() + data.getEntryMortgageTodo() + data.getLogisticsTodo();
////                mBinding.tvRedPoint.setVisibility((sub > 0) ? View.VISIBLE : View.GONE);
//                if (sub > 99) {
//                    mBinding.tvRedPoint.setText("99+");
//                } else {
//                    mBinding.tvRedPoint.setText(sub + "");
//                }
//
//            }
//
//            @Override
//            protected void onFinish() {
//                disMissLoading();
//            }
//        });
//    }

//    /**
//     * 资料传递待处理数量
//     */
//    private void getDataCount() {
//        Map<String, Object> map = new HashMap<>();
//
//        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录不用请求
//            return;
//        }
//
//        List<String> statusList = new ArrayList<>();
//        statusList.add("0");
//        statusList.add("1");
//        statusList.add("3");
//
//        if (UserHelper.isYWY()) {
//            map.put("userId", SPUtilHelper.getUserId());
//            map.put("statusList", statusList);
//        }
//
//        Call call = RetrofitUtils.createApi(MyApiServer.class).getDataTransferList("632157", StringUtils.getJsonToString(map));
//        addCall(call);
//
//        call.enqueue(new BaseResponseListCallBack<DataTransferModel>(mActivity) {
//            @Override
//            protected void onSuccess(List<DataTransferModel> data, String SucMessage) {
//                if (data == null)
//                    return;
//
//                mBinding.mySrZlcd.setPointCount(data.size());
//            }
//
//            @Override
//            protected void onFinish() {
//                disMissLoading();
//            }
//        });
//    }

}
