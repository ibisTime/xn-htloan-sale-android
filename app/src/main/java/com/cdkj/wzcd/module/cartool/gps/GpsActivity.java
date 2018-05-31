package com.cdkj.wzcd.module.cartool.gps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.adapter.GpsAdapter;
import com.cdkj.wzcd.model.GpsBean;
import com.cdkj.wzcd.module.cartool.uservoid.BlankOutActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * gps申领
 */
public class GpsActivity extends AbsRefreshListActivity<GpsBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, GpsActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("GPS申领");
        mBaseBinding.titleView.setRightTitle("申领");
        mBaseBinding.titleView.setRightFraClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请作废
                GpsRequestActivity.open(GpsActivity.this);
            }
        });

        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        ArrayList<GpsBean> data = new ArrayList<GpsBean>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        GpsAdapter mAdapter = new GpsAdapter(data);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GpsDetailsActivity.open(GpsActivity.this);
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