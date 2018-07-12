package com.cdkj.wzcd.module.business.credit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cdkj.baselibrary.base.AbsTabLayoutActivity;
import com.cdkj.wzcd.model.SearchModel;
import com.cdkj.wzcd.util.UserHelper;
import com.cdkj.wzcd.view.popup.MySearchPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * 车贷业务（business）下，咨询调查列表Activity
 * Created by cdkj on 2018/5/29.
 */

public class BssCreditListActivity extends AbsTabLayoutActivity {

    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    private String[] mSelectList;

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BssCreditListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("资信调查");

        if (!UserHelper.isZHRY()){
            mBaseBinding.titleView.setRightTitle("发起征信");
            mBaseBinding.titleView.setRightFraClickListener(view -> {

//                select();

                CreditInitiateActivity.open(this, null);

            });
        }

        mTitleList = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        mSelectList = new String[]{"发起征信","搜索"};

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

        mTitleList.add("已通过");
        mFragmentList.add(CreditListFragment.getInstance(true, UserHelper.isZHRY() ? "" : "1"));

        mTitleList.add("未通过");
        mFragmentList.add(CreditListFragment.getInstance(false, UserHelper.isZHRY() ? "" : "0"));

        initViewPager();
        mTabLayoutBinding.viewpager.setOffscreenPageLimit(2);
        mTabLayoutBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);

        if (UserHelper.isZHRY()){
            mTabLayoutBinding.tablayout.setVisibility(View.GONE);
        }

    }

    private void select(){
        new AlertDialog.Builder(this).setTitle("请选择").setSingleChoiceItems(
                mSelectList, -1, (dialog, which) -> {

                    if (which == 0){
                        CreditInitiateActivity.open(this, null);
                    }else {
                        List<SearchModel> list = new ArrayList<>();
                        list.add(new SearchModel().setKeyTypeText("业务编号").setSearchKey("code").setSearchType(MySearchPopup.SEARCH_EDIT));
                        list.add(new SearchModel().setKeyTypeText("客户姓名").setSearchKey("userName").setSearchType(MySearchPopup.SEARCH_EDIT));
                        list.add(new SearchModel().setKeyTypeText("关键字搜索").setSearchKey("keyWord").setSearchType(MySearchPopup.SEARCH_EDIT));
                        list.add(new SearchModel().setKeyTypeText("业务员").setSearchType(MySearchPopup.SEARCH_EDIT_SELECT));
//                list.add(new SearchModel().setKeyTypeText("申请日期").setSearchType(MySearchPopup.SEARCH_DATE_TIME));

                        new MySearchPopup(BssCreditListActivity.this, list).show(mBaseBinding.llRoot);
                    }

                    dialog.dismiss();
                }).setNegativeButton("取消", null).show();
    }

}
