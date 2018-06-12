package com.cdkj.wzcd.module.business.join_approval.edit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentJoinStep4Binding;

/**
 * 准入申请 填写
 * Created by cdkj on 2018/5/30.
 */

public class JoinStep4Activity extends AbsBaseLoadActivity {

    private FragmentJoinStep4Binding mBinding;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, JoinStep4Activity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_join_step4, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.daiqianzhunru)+"(担保人信息)");

        initListener();
    }

    private void initListener() {
    }


}
