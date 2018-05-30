package com.cdkj.wzcd.module.cartool.uservoid;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityBlankOutBinding;

/**
 * 申请作废界面
 */
public class BlankOutActivity extends AbsBaseLoadActivity {
    ActivityBlankOutBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, BlankOutActivity.class);
            context.startActivity(intent);
        }

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_blank_out, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("申请");
    }
}
