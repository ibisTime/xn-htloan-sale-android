package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityLocalUpEnclosureBinding;

public class LocalUpEnclosureActivity extends AbsBaseLoadActivity {
    private ActivityLocalUpEnclosureBinding mBinding;

    public static void open(Context context) {
        Intent intent = new Intent(context, LocalUpEnclosureActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_local_up_enclosure, null, false);
        mBaseBinding.titleView.setMidTitle("本地上传");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBinding.myVlBankVideo.build(this, 10, 1);
    }
}
