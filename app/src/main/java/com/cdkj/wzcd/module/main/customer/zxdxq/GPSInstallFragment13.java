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
import com.cdkj.wzcd.adapter.GPSInstall13Adapter;
import com.cdkj.wzcd.databinding.FragmentGpsInstall13Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

import java.util.ArrayList;

/**
 * GPS安装列表
 */
public class GPSInstallFragment13 extends BaseLazyFragment {

    private FragmentGpsInstall13Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        GPSInstallFragment13 fragment = new GPSInstallFragment13();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_gps_install13, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        GPSInstall13Adapter gpsInstall13Adapter = new GPSInstall13Adapter(list);
        mBinding.rv.setAdapter(gpsInstall13Adapter);

    }

    public void setView() {

    }
}
