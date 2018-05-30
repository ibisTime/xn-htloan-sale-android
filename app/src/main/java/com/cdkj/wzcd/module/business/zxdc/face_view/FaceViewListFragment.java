package com.cdkj.wzcd.module.business.zxdc.face_view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapters.FaceViewListAdapter;
import com.cdkj.wzcd.adapters.MyApplyListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 面签
 * Created by cdkj on 2018/5/30.
 */
public class FaceViewListFragment extends AbsRefreshListFragment {

    public static FaceViewListFragment getInstance() {
        FaceViewListFragment fragment = new FaceViewListFragment();
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
        FaceViewListAdapter faceViewListAdapter = new FaceViewListAdapter(listData);
        faceViewListAdapter.setOnItemClickListener((adapter, view, position) -> {
            FaceViewStartActivity.open(mActivity);
        });
        return faceViewListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

    }
}
