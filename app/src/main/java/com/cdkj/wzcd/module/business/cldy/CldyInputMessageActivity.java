package com.cdkj.wzcd.module.business.cldy;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCldyInputMessageBinding;


/**
 * 车辆抵押跳转的界面
 */
public class CldyInputMessageActivity extends AbsBaseLoadActivity {
    ActivityCldyInputMessageBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CldyInputMessageActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_cldy_input_message, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("录入");
    }
}
