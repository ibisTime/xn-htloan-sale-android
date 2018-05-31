package com.cdkj.wzcd.module.cartool.uservoid;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityUserToVoidDetailsBinding;

/**
 * 用户作废
 */
public class UserToVoidDetailsActivity extends AbsBaseLoadActivity {
    ActivityUserToVoidDetailsBinding mBinding;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, UserToVoidDetailsActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
//        activity_user_to_void_details.xml
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_user_to_void_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}