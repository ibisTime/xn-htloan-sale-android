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
import com.cdkj.wzcd.databinding.FargmentZrCar2Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 申请人基本信息
 */
public class ZRCarFragment2 extends BaseLazyFragment {

    private FargmentZrCar2Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRCarFragment2 fragment = new ZRCarFragment2();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_car2, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;
        mBinding.myILHgz.setActivity(mActivity, 1, 0);
        mBinding.myMLClzp.build(mActivity, 2);
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

                if (1 == mBinding.myILHgz.getRequestCode()) {
                    mBinding.myILHgz.setFlImg(key);
                }
                if (2 == mBinding.myMLClzp.getRequestCode()) {
                    mBinding.myMLClzp.addList(key);
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
        if (mBinding.mySLJdcxsgs.check()) {
            return false;
        }
        if (mBinding.mySLSsqy.check()) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELKpdj.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELKpjg.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELCllx.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELClpp.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELClcx.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELClcx2.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELClys.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELCjh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELFdjh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSczdj.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYgje.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELCjtx.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYbgls.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELYb.check())) {
            return false;
        }
//        if (TextUtils.isEmpty(mBinding.myELDydlr.check())) {
//            //抵押代理人
//            return false;
//        }
//        if (TextUtils.isEmpty(mBinding.myELDydd.check())) {
//            //抵押地点
//            return false;
//        }
        if (TextUtils.isEmpty(mBinding.myELLhdd.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myILHgz.check())) {
            return false;
        }
        if (mBinding.myMLClzp.check()) {
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
        map.put("customerType", mBinding.mySLSsqy.getDataKey());

        return map;
    }
}
