package com.cdkj.wzcd.module.cartool.gps;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityGpsRequestBinding;
import com.cdkj.wzcd.module.cartool.uservoid.BlankOutActivity;

public class GpsRequestActivity extends AbsBaseLoadActivity {
    ActivityGpsRequestBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, GpsRequestActivity.class);
            context.startActivity(intent);
        }

    }

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
