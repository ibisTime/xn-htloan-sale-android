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
import com.cdkj.wzcd.databinding.FargmentZrLoan1Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请人基本信息
 */
public class ZRLoanFragment1 extends BaseLazyFragment {

    private FargmentZrLoan1Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRLoanFragment1 fragment = new ZRLoanFragment1();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_loan1, container, false);
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
        if (mBinding.mySLDkqx.check()) {
            return false;
        }
        if (mBinding.mySLDkcp.check()) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSfje.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSfbl.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSyyg.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYgje.check())) {
            return false;
        }
        if (mBinding.mySLSfrz.check()) {
            return false;
        }
        if (mBinding.mySLSfdz.check()) {
            return false;
        }

        if (mBinding.mySLSfangps.check()) {
            return false;
        }
        if (mBinding.mySLSfdy.check()) {
            return false;
        }
        if (mBinding.mySLSfwsxb.check()) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYgbzj.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELLybzj.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELFwf.check())) {
            return false;
        }
        return true;
    }

    /**
     * 获取本页入参数据
     *
     * @return
     */
    public Map<String, Object> getData() {

        Map<String, Object> map = new HashMap<>();
        map.put("customerType", mBinding.mySLSfdy.getDataKey());

        return map;
    }
}
