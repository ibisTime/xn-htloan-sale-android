package com.cdkj.wzcd.module.main.customer.zrdxq;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FargmentZrWork5Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 工作情况
 */
public class ZRWorkFragment5 extends BaseLazyFragment {

    private FargmentZrWork5Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRWorkFragment5 fragment = new ZRWorkFragment5();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_work5, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;
        mBinding.myMLSrzm.build(mActivity, 1);
        mBinding.myMLDwqtzp.build(mActivity, 2);
        mBinding.myMLBgcdzp.build(mActivity, 3);
        mBinding.myMLKhhy.build(mActivity, 4);

    }

    public void setView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();
        new QiNiuHelper(mActivity).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {

                if (1 == mBinding.myMLSrzm.getRequestCode()) {
                    mBinding.myMLSrzm.addList(key);
                }
                if (2 == mBinding.myMLDwqtzp.getRequestCode()) {
                    mBinding.myMLDwqtzp.addList(key);
                }
                if (3 == mBinding.myMLBgcdzp.getRequestCode()) {
                    mBinding.myMLBgcdzp.addList(key);
                }
                if (4 == mBinding.myMLKhhy.getRequestCode()) {
                    mBinding.myMLKhhy.addList(key);
                }
                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }

    /**
     * 检查必填参数
     *
     * @return
     */
    public boolean check() {

        if (mBinding.mySLSfzjdw.check()) {
            return false;
        }
        if (mBinding.mySLDwjjxz.check()) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELSshy.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwmc.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwdh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwdz.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELHsjrgdw.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELZw.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYsr.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzmsjhklyfx.check())) {
            return false;
        }
        if (mBinding.myMLSrzm.check()) {
            return false;
        }
        if (mBinding.myMLDwqtzp.check()) {
            return false;
        }
        if (mBinding.myMLBgcdzp.check()) {
            return false;
        }
        if (mBinding.myMLKhhy.check()) {
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
        map.put("customerType", mBinding.mySLDwjjxz.getDataKey());

        return map;
    }
}
