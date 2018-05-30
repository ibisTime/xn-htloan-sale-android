package com.cdkj.wzcd.module.business.zxdc.gps_install;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapters.GPSInstallListAdapter;
import com.cdkj.wzcd.databinding.ActivityGpsInfoInputBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * GPS 安装回录信息填写
 * Created by cdkj on 2018/5/30.
 */

public class GPSInstallInfoActivity extends AbsBaseLoadActivity {

    private ActivityGpsInfoInputBinding mBinding;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GPSInstallInfoActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_gps_info_input, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("安装回录");
        initListener();
    }


    private void initListener() {

        mBinding.linLayoutAddGps.setOnClickListener(v -> {
            GPSInfoAddActivity.open(this);
        });
    }


}
