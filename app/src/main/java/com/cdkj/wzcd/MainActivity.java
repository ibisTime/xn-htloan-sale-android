package com.cdkj.wzcd;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.databinding.ActivityMainBinding;
import com.cdkj.wzcd.module.business.cldy.BssCldyListActivity;
import com.cdkj.wzcd.module.business.cllh.CllhListActivity;
import com.cdkj.wzcd.module.business.zxdc.BssZxdcListActivity;
import com.cdkj.wzcd.module.cartool.gps.GpsActivity;
import com.cdkj.wzcd.module.cartool.history.HistoryUserActivity;
import com.cdkj.wzcd.module.cartool.uservoid.UserToVoidActivity;
import com.cdkj.wzcd.module.datatransfer.DataTransferActivity;

public class MainActivity extends AbsBaseLoadActivity {

    private ActivityMainBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return false;
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {


        initListener();
    }

    private void initListener() {
        mBinding.mySrZxdc.setOnClickListener(view -> {
            BssZxdcListActivity.open(this);
        });
        mBinding.mySrCllh.setOnClickListener(view -> {
            //车辆落户
            CllhListActivity.open(this);
        });
        mBinding.mySrCwdz.setOnClickListener(view -> {
            //车辆抵押
            BssCldyListActivity.open(this);
        });
        mBinding.mySrZlcd.setOnClickListener(view -> {
            //资料上传
            DataTransferActivity.open(this);
        });
        mBinding.mySrKhzf.setOnClickListener(view -> {
            //客户作废
            UserToVoidActivity.open(this);
        });
        mBinding.mySrGpssl.setOnClickListener(view -> {
            //GPS申领
            GpsActivity.open(this);
        });
        mBinding.mySrLskh.setOnClickListener(view -> {
            //历史客户
            HistoryUserActivity.open(this);
        });


    }
}
