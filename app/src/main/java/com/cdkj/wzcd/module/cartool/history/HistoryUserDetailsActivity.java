package com.cdkj.wzcd.module.cartool.history;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityHistoryUserDetailsBinding;

public class HistoryUserDetailsActivity extends AbsBaseLoadActivity {
    ActivityHistoryUserDetailsBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, HistoryUserDetailsActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_history_user_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("详情");

    }
}
