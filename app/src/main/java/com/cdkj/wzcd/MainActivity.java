package com.cdkj.wzcd;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.databinding.ActivityMainBinding;
import com.cdkj.wzcd.model.NodeModel;
import com.cdkj.wzcd.module.main.CustomerFragment;
import com.cdkj.wzcd.module.main.HomeFragment;
import com.cdkj.wzcd.module.main.MessageFragment;
import com.cdkj.wzcd.module.main.UserFragment;
import com.cdkj.wzcd.module.user.SignInActivity;
import com.cdkj.wzcd.tencent.logininterface.TencentLogoutInterface;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseLoadActivity implements TencentLogoutInterface {

    // 节点列表
    public static List<NodeModel> BASE_NODE_LIST = new ArrayList<>();
    // 业务种类
    public static List<DataDictionary> BASE_BIZ_TYPE = new ArrayList<>();

    private ActivityMainBinding mBinding;
    private int currentPostion;
    private ArrayList<Fragment> fragments;

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

        getNodeHelperData();

        initListener();
    }

    /**
     * 获取所有的节点
     */
    public void getNodeHelperData() {
        getNodeAndDictionary();
    }

    /**
     * 获取所有的节点和  数字字典  因为已经在  闪屏界面获取过了  所以这里判断一下  如果闪屏界面获取成功这里就不在重新获取了
     * <p>
     * 如果闪屏界面获取失败 这里再重新获取,优化一下  否在每次进入这个界面  由于要先获取节点  会导致出现白屏的问题
     */
    private void getNodeAndDictionary() {
        this.BASE_NODE_LIST = SplashActivity.BASE_NODE_LIST;
        this.BASE_BIZ_TYPE = SplashActivity.BASE_BIZ_TYPE;
        if (BASE_NODE_LIST == null || BASE_NODE_LIST.size() == 0 || BASE_BIZ_TYPE == null || BASE_BIZ_TYPE.size() == 0) {

            NodeHelper.getNodeBaseDataRequest(null, "", "", new NodeHelper.NodeInterface() {
                @Override
                public void onSuccess(List<NodeModel> list) {
                    BASE_NODE_LIST.clear();
                    BASE_NODE_LIST.addAll(list);

                    BizTypeHelper.getBizTypeBaseDataRequest(null, "", new BizTypeHelper.BizTypeInterface() {
                        @Override
                        public void onSuccess(List<DataDictionary> list) {

                            BASE_BIZ_TYPE.clear();
                            BASE_BIZ_TYPE.addAll(list);
                            initViewPager();
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
        } else {
            initViewPager();
        }
    }

    private void initViewPager() {

        mBinding.viewpager.setOffscreenPageLimit(3);
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.getInstance());
        fragments.add(CustomerFragment.getInstance());
        fragments.add(MessageFragment.getInstance());
        fragments.add(UserFragment.getInstance());
        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpager.setCurrentItem(currentPostion);

    }

    private void initListener() {

        mBinding.layoutTab.radiogroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.radio_main_tab_1:
                    currentPostion = 0;
                    break;
                case R.id.radio_main_tab_2:
                    currentPostion = 1;
                    break;
                case R.id.radio_main_tab_3:
                    currentPostion = 2;
                    break;
                case R.id.radio_main_tab_4:
                    currentPostion = 3;
                    break;
            }
            mBinding.viewpager.setCurrentItem(currentPostion);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragments.get(0);
        fragment.onActivityResult(requestCode, resultCode, data);
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

    //    /**
//     * 退出登录
//     */
    private void logOut() {
        showDoubleWarnListen("你确定要退出当前账号吗?", view -> {
            SPUtilHelper.logOutClear();
            UITipDialog.showSuccess(this, "退出成功", dialogInterface -> {
                finish();

                SignInActivity.open(this, false);
            });
        });
    }
}
