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
import com.cdkj.wzcd.databinding.FargmentZrFamily4Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 申请人基本信息
 */
public class ZRFamilyFragment4 extends BaseLazyFragment {

    private FargmentZrFamily4Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRFamilyFragment4 fragment = new ZRFamilyFragment4();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_family4, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;
        mBinding.myMLHkb.build(mActivity, 1);
        mBinding.myMLJhzLhz.build(mActivity, 2);
        mBinding.myMLGfhtFcb.build(mActivity, 3);
        mBinding.myMLGffp.build(mActivity, 4);
        mBinding.myMLJzzm.build(mActivity, 5);
        mBinding.myMLZjfzm.build(mActivity, 6);
        mBinding.myMLJfzp.build(mActivity, 7);
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

                if (1 == mBinding.myMLHkb.getRequestCode()) {
                    mBinding.myMLHkb.addList(key);
                }
                if (2 == mBinding.myMLJhzLhz.getRequestCode()) {
                    mBinding.myMLJhzLhz.addList(key);
                }
                if (3 == mBinding.myMLGfhtFcb.getRequestCode()) {
                    mBinding.myMLGfhtFcb.addList(key);
                }
                if (4 == mBinding.myMLGffp.getRequestCode()) {
                    mBinding.myMLGffp.addList(key);
                }
                if (5 == mBinding.myMLJzzm.getRequestCode()) {
                    mBinding.myMLJzzm.addList(key);
                }
                if (6 == mBinding.myMLZjfzm.getRequestCode()) {
                    mBinding.myMLZjfzm.addList(key);
                }
                if (7 == mBinding.myMLJfzp.getRequestCode()) {
                    mBinding.myMLJfzp.addList(key);
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

        if (mBinding.mySLHyzk.check()) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJtrk.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJtdh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJtzycc.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJtzyccsm.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELHjd.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELHjdyb.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJzd.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELJzdyb.check())) {
            return false;
        }
        if (mBinding.myMLHkb.check()) {
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
        map.put("customerType", mBinding.mySLHyzk.getDataKey());

        return map;
    }
}
