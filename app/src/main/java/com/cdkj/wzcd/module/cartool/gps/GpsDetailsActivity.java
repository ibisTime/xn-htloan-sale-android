package com.cdkj.wzcd.module.cartool.gps;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityGpsDetailsBinding;

/**
 * gps申领
 */
public class GpsDetailsActivity extends AbsBaseLoadActivity {
    ActivityGpsDetailsBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, GpsDetailsActivity.class);
            context.startActivity(intent);
        }

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_gps_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("详情");
    }
}