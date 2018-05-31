package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivitySendAndExamineBinding;

public class SendAndExamineActivity extends AbsBaseLoadActivity {


    private ActivitySendAndExamineBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, SendAndExamineActivity.class);
            context.startActivity(intent);
        }

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_send_and_examine, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("收件");

    }
}
