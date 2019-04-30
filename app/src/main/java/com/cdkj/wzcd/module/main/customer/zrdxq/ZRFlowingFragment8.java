package com.cdkj.wzcd.module.main.customer.zrdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.ZRFlowingWaterAdapter;
import com.cdkj.wzcd.databinding.FargmentZrFlowing8Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;

/**
 * 流水信息
 */
public class ZRFlowingFragment8 extends BaseLazyFragment {

    private FargmentZrFlowing8Binding mBinding;

    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZRFlowingFragment8 fragment = new ZRFlowingFragment8();
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

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_zr_flowing8, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRActivity activity = (ZRActivity) mActivity;
        this.data = activity.currentData;
        setRecyclerView();
    }

    public void setRecyclerView() {
        ArrayList<Object> data = new ArrayList<>();
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        ZRFlowingWaterAdapter adapter = new ZRFlowingWaterAdapter(data);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(adapter);
    }
}
