package com.cdkj.wzcd.module.business.face_view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.base.AbsTabLayoutActivity;
import com.cdkj.wzcd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 面签
 * Created by cdkj on 2018/5/30.
 */

public class InterviewActivity extends AbsTabLayoutActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, InterviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);
        mBaseBinding.titleView.setMidTitle(getString(R.string.face_view));
        mTabLayoutBinding.tablayout.setVisibility(View.GONE);

//        mBaseBinding.titleView.setRightTitle("MQ");
//        mBaseBinding.titleView.setRightFraClickListener(view -> {
//            InterviewStartActivity.open(this,"BO201806121135203066628");

//            Intent intent = new Intent(InterviewActivity.this, MainActivity.class);
//            startActivity(intent);
//        });
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(InterviewListFragment.getInstance());
        fragments.add(InterviewListFragment.getInstance());

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
