package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentLoanVehicle3Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 贷款车辆
 */
public class LoanVehicleFragment3 extends BaseLazyFragment {

    private FragmentLoanVehicle3Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        LoanVehicleFragment3 fragment = new LoanVehicleFragment3();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_loan_vehicle3, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.mymlClzp.build(mActivity, 0);
        mBinding.mymlHgzzp.build(mActivity, 1);
        mBinding.mymlCljghsbg.build(mActivity, 2);

    }

    public void setView() {
    }
}
