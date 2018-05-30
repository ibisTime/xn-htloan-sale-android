package com.cdkj.wzcd.module.cartool.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.adapter.HistoryUserAdapter;
import com.cdkj.wzcd.model.HistoryBean;
import com.cdkj.wzcd.module.cartool.gps.GpsDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryUserActivity extends AbsRefreshListActivity<HistoryBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, HistoryUserActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("历史客户");
        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        ArrayList<HistoryBean> data = new ArrayList<HistoryBean>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        HistoryUserAdapter mAdapter = new HistoryUserAdapter(data);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GpsDetailsActivity.open(HistoryUserActivity.this);
            }
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        initDatas(pageIndex, limit, isShowDialog);
    }

    /**
     * 获取数据
     *
     * @param pageIndex
     * @param limit
     * @param isShowDialog
     */
    private void initDatas(int pageIndex, int limit, boolean isShowDialog) {


    }
}
