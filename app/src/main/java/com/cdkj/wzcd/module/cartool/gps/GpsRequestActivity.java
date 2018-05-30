package com.cdkj.wzcd.module.cartool.gps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityGpsRequestBinding;

public class GpsRequestActivity extends AbsBaseLoadActivity {
    ActivityGpsRequestBinding mBinding;

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_gps_request, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("申领");

    }
}
