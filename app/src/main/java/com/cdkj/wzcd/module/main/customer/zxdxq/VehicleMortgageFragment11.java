package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentVehicleMortgage11Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 车辆抵押
 */
public class VehicleMortgageFragment11 extends BaseLazyFragment {

    private FragmentVehicleMortgage11Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        VehicleMortgageFragment11 fragment = new VehicleMortgageFragment11();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_vehicle_mortgage11, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.myIlClxszsmj.setActivity(mActivity, 1,1);
        mBinding.myIlCys.setActivity(mActivity, 2,2);
        mBinding.myIlDbsmj.setActivity(mActivity, 3,3);
        mBinding.myIlIdCard.setActivity(mActivity, 4,4);
        mBinding.myIlJdcdjzs.setActivity(mActivity, 5,5);
        mBinding.myIlPd.setActivity(mActivity, 6,6);
        mBinding.myIlWszmsmj.setActivity(mActivity, 7,7);

    }

    public void setView() {

    }
}
