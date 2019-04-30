package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FargmentOtherBasic6Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 其他基本资料
 */
public class OtherBasicDetialsFragment6 extends BaseLazyFragment {

    private FargmentOtherBasic6Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        OtherBasicDetialsFragment6 fragment = new OtherBasicDetialsFragment6();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fargment_other_basic6, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.mymlHkb.build(mActivity, 0);
        mBinding.mymlId.build(mActivity, 1);
        mBinding.mymlQtzl.build(mActivity, 2);

    }

    public void setView() {

    }
}
