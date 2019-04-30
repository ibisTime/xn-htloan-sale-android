package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.logAdapter;
import com.cdkj.wzcd.databinding.ActivityLogBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 流转日志
 */
public class LogFragment14 extends BaseLazyFragment {

    private ActivityLogBinding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        LogFragment14 fragment = new LogFragment14();
        return fragment;
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_log, container, false);
        initView();
        initRecyvlerView();
        return mBinding.getRoot();
    }

    public void initView() {
        mBinding.myCbConfirm.setVisibility(View.GONE);
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;

    }

    private void initRecyvlerView() {
        if (data == null) {
            return;
        }
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        logAdapter logAdapter = new logAdapter(data.getBizLogs());
        mBinding.rv.setAdapter(logAdapter);
    }
}
