package com.cdkj.wzcd.module.business.cllh;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCllhInputMessageBinding;

/**
 * 车辆落户跳转的界面
 */
public class CllhInputMessageActivity extends AbsBaseLoadActivity {
    ActivityCllhInputMessageBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CllhInputMessageActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_cllh_input_message, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("录入");
    }
}
