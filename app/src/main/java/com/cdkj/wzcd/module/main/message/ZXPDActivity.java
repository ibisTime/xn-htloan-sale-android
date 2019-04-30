package com.cdkj.wzcd.module.main.message;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.databinding.ActivityZxpdBinding;
import com.cdkj.wzcd.R;

public class ZXPDActivity extends AbsBaseLoadActivity {


    private ActivityZxpdBinding mBinding;

    public static void open(Context context) {
        Intent intent = new Intent(context, ZXPDActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zxpd, null, false);
        mBaseBinding.titleView.setMidTitle("征信派单");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
