package com.cdkj.wzcd.module.business.zxdc.join_approval;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapters.MyApplyListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷前准入 我的申请
 * Created by cdkj on 2018/5/30.
 */
public class MyApplyListFragment extends AbsRefreshListFragment {

    public static MyApplyListFragment getInstance() {
        MyApplyListFragment fragment = new MyApplyListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRefreshHelper(10);

        List listData = new ArrayList();
        listData.add("");
        listData.add("");
        listData.add("");
        listData.add("");

        mRefreshHelper.setData(listData, getString(R.string.no_apply), 0);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        MyApplyListAdapter myApplyListAdapter = new MyApplyListAdapter(listData);
        myApplyListAdapter.setOnItemClickListener((adapter, view, position) -> {
            JoinApplyInfoInputActivity.open(mActivity);
        });
        return myApplyListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

    }
}
