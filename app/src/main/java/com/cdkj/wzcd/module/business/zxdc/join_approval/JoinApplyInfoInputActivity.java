package com.cdkj.wzcd.module.business.zxdc.join_approval;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.base.AbsTabLayoutActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityJoinApplyInputBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 准入申请 填写
 * Created by cdkj on 2018/5/30.
 */

public class JoinApplyInfoInputActivity extends AbsBaseLoadActivity {

    private ActivityJoinApplyInputBinding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, JoinApplyInfoInputActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_join_apply_input, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.daiqianzhunru));
    }


}
