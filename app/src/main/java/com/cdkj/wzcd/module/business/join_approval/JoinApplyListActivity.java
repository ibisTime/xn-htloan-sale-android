package com.cdkj.wzcd.module.business.join_approval;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.base.AbsTabLayoutActivity;
import com.cdkj.wzcd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 准入申请
 * Created by cdkj on 2018/5/30.
 */

public class JoinApplyListActivity extends AbsTabLayoutActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, JoinApplyListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle(getString(R.string.daiqianzhunru));
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MyApplyListFragment.getInstance());
        fragments.add(MyApplyListFragment.getInstance());

        return fragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.help_do_thing));
        titles.add(getString(R.string.my_apply));
        return titles;
    }


}
