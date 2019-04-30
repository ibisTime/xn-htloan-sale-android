package com.cdkj.wzcd.module.main.customer.zrdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FargmentZrCustomer3Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户信息
 */
public class ZRCustomerFragment3 extends BaseLazyFragment {

    private FargmentZrCustomer3Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRCustomerFragment3 fragment = new ZRCustomerFragment3();
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_customer3, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;

    }

    public void setView() {

    }


    /**
     * 检查必填参数
     *
     * @return
     */
    public boolean check() {

        if (mBinding.mySLXb.check()) {
            return false;
        }
        if (mBinding.mySLZzmm.check()) {
            return false;
        }
        if (mBinding.mySLXl.check()) {
            return false;
        }
        if (mBinding.mySLYwjz.check()) {
            return false;
        }
        if (mBinding.mySLYzdrgx1.check()) {
            return false;
        }
        if (mBinding.mySLYzdrgx2.check()) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELNl.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELMz.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELZy.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELZc.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELXycl.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELZysrly.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJjlxr1.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSjhm1.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJjlxr2.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSjhm2.check())) {
            return false;
        }

        return true;
    }
//

    /**
     * 获取本页入参数据
     *
     * @return
     */
    public Map<String, Object> getData() {

        Map<String, Object> map = new HashMap<>();
        map.put("customerType", mBinding.mySLYzdrgx1.getDataKey());

        return map;
    }
}
