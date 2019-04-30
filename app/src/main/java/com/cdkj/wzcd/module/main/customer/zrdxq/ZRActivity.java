package com.cdkj.wzcd.module.main.customer.zrdxq;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.ZRDDetialsAdapter;
import com.cdkj.wzcd.databinding.ActivityZrBinding;
import com.cdkj.wzcd.model.ZRDDetialsBean;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ZRActivity extends AbsBaseLoadActivity {


    private ArrayList<ZRDDetialsBean> titleData;
    private ActivityZrBinding mBinding;
    private ArrayList<Fragment> fragments;
    public static ZXDetialsBean.ListBean currentData;
    private int pageIndex;

    public static void open(Context mContext, ZXDetialsBean.ListBean data) {
        Intent intent = new Intent(mContext, ZRActivity.class);
        intent.putExtra(CdRouteHelper.DATA_SIGN, data);
        mContext.startActivity(intent);
    }

    //        setContentView(R.layout.activity_zr);
    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zr, null, false);
        mBaseBinding.titleView.setMidTitle("准入申请");
        mBaseBinding.titleView.setRightTitle("申请");
        mBaseBinding.titleView.setRightFraClickListener(view -> {
            ToastUtil.show(ZRActivity.this, "申请啊啊啊啊啊");
            check();
        });

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            currentData = (ZXDetialsBean.ListBean) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
        }
        initRecyclerView();
        initViewPager();
    }

    /**
     * 初始化  左侧的 标题
     */
    private void initRecyclerView() {
        titleData = new ArrayList<>();
        titleData.add(new ZRDDetialsBean("贷款信息", true));
        titleData.add(new ZRDDetialsBean("车辆信息"));
        titleData.add(new ZRDDetialsBean("客户信息"));
        titleData.add(new ZRDDetialsBean("家庭情况"));
        titleData.add(new ZRDDetialsBean("工作情况"));
        titleData.add(new ZRDDetialsBean("共换人信息"));
        titleData.add(new ZRDDetialsBean("担保人信息"));
        titleData.add(new ZRDDetialsBean("流水信息"));

        mBinding.tvTitle.setText(titleData.get(0).getTitle());//设置默认的标题

        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ZRDDetialsAdapter zrdDetialsAdapter = new ZRDDetialsAdapter(titleData);
        mBinding.rv.setAdapter(zrdDetialsAdapter);
        zrdDetialsAdapter.setOnItemClickListener((adapter, view, position) -> {
            pageIndex = position;
            //切换右侧的 fragment
            if (position <= fragments.size() - 1) {
                mBinding.viewpager.setCurrentItem(position, false);//切换界面  去掉默认的切换动画的
            }
            for (int i = 0; i < titleData.size(); i++) {
                ZRDDetialsBean zrdDetialsBean = titleData.get(i);
                if (position == i) {
                    mBinding.tvTitle.setText(zrdDetialsBean.getTitle());//设置标题
                    zrdDetialsBean.setSelect(true);
                    adapter.notifyItemChanged(i);
                } else {
                    //做了优化不是每次都刷新全部的列表刷新  上一个选中的  和本次选中的条目
                    if (zrdDetialsBean.isSelect()) {
                        zrdDetialsBean.setSelect(false);
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(ZRLoanFragment1.getInstance());//贷款信息
        fragments.add(ZRCarFragment2.getInstance());//车辆信息
        fragments.add(ZRCustomerFragment3.getInstance());//客户信息
        fragments.add(ZRFamilyFragment4.getInstance());//家庭情况
        fragments.add(ZRWorkFragment5.getInstance());//工作情况
        fragments.add(ZRCopayerFragment6.getInstance());//共换人信息
        fragments.add(ZRGuarantorFragment7.getInstance());//担保人信息
        fragments.add(ZRFlowingFragment8.getInstance());//流水信息


        mBinding.viewpager.setPagingEnabled(false);
        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpager.setCurrentItem(0, false);
    }

    private boolean check() {
        ZRLoanFragment1 fragment1 = (ZRLoanFragment1) fragments.get(0);
        if (!fragment1.check()) {
            return false;
        }
        ZRCarFragment2 fragment2 = (ZRCarFragment2) fragments.get(1);
        if (!fragment2.check()) {
            return false;
        }
        ZRCustomerFragment3 fragment3 = (ZRCustomerFragment3) fragments.get(2);
        if (!fragment3.check()) {
            return false;
        }
        ZRFamilyFragment4 fragment4 = (ZRFamilyFragment4) fragments.get(3);
        if (!fragment4.check()) {
            return false;
        }
        ZRWorkFragment5 fragment5 = (ZRWorkFragment5) fragments.get(4);
        if (!fragment5.check()) {
            return false;
        }
        ZRCopayerFragment6 fragment6 = (ZRCopayerFragment6) fragments.get(5);
        if (!fragment6.check()) {
            return false;
        }
        ZRGuarantorFragment7 fragment7 = (ZRGuarantorFragment7) fragments.get(6);
        if (!fragment7.check()) {
            return false;
        }
//        ZRFlowingFragment8 fragment8 = (ZRFlowingFragment8) fragments.get(7);
//        if (!fragment8.check()) {
//            return false;
//        }
        return true;
    }

    private void getData() {
        Map<String, Object> allMap = new HashMap<>();

        ZRLoanFragment1 fragment1 = (ZRLoanFragment1) fragments.get(0);
        ZRCarFragment2 fragment2 = (ZRCarFragment2) fragments.get(1);
        ZRCustomerFragment3 fragment3 = (ZRCustomerFragment3) fragments.get(2);
        ZRFamilyFragment4 fragment4 = (ZRFamilyFragment4) fragments.get(3);
        ZRWorkFragment5 fragment5 = (ZRWorkFragment5) fragments.get(4);
        ZRCopayerFragment6 fragment6 = (ZRCopayerFragment6) fragments.get(5);
        ZRGuarantorFragment7 fragment7 = (ZRGuarantorFragment7) fragments.get(6);
        allMap.putAll(fragment1.getData());
        allMap.putAll(fragment2.getData());
        allMap.putAll(fragment3.getData());
        allMap.putAll(fragment4.getData());
        allMap.putAll(fragment5.getData());
        allMap.putAll(fragment6.getData());
        allMap.putAll(fragment7.getData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = fragments.get(pageIndex);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
