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
import com.cdkj.wzcd.databinding.FargmentZrCopayer6Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 工作情况
 */
public class ZRCopayerFragment6 extends BaseLazyFragment {

    private FargmentZrCopayer6Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRCopayerFragment6 fragment = new ZRCopayerFragment6();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_copayer6, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;
        mBinding.myMLQtzl.build(mActivity, 1);

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

                if (1 == mBinding.myMLQtzl.getRequestCode()) {
                    mBinding.myMLQtzl.addList(key);
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
        if (mBinding.mySLYzdrgx.check()) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELXm.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSjh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELSfzh.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELXl.check())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myELHjd.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELHjdyb.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwmc.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwdz.check())) {
            return false;
        }
        if (TextUtils.isEmpty(mBinding.myELGzdwdh.check())) {
            return false;
        }
        if (mBinding.myMLQtzl.check()) {
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
        map.put("customerType", mBinding.mySLYzdrgx.getDataKey());

        return map;
    }
}
