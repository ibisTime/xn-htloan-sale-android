package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.base.AbsTabLayoutActivity;

import java.util.ArrayList;
import java.util.List;


public class DataTransferActivity2 extends AbsTabLayoutActivity {

    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, DataTransferActivity2.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("资料传递");

        mTitleList = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        initViewPagerData();
    }

    @Override
    public List<Fragment> getFragments() {
        return mFragmentList;
    }

    @Override
    public List<String> getFragmentTitles() {
        return mTitleList;
    }

    private void initViewPagerData() {

        mTitleList.add("资料发件");
        mFragmentList.add(DataTransferListFragment.getInstance(true, DataTransferListFragment.DATA_SEND));

        mTitleList.add("资料收件");
        mFragmentList.add(DataTransferListFragment.getInstance(false, DataTransferListFragment.DATA_OTHER));

        mTitleList.add("GPS收件");
        mFragmentList.add(DataTransferListFragment.getInstance(false, DataTransferListFragment.DATA_GPS));

        initViewPager();
        mTabLayoutBinding.viewpager.setOffscreenPageLimit(2);
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
