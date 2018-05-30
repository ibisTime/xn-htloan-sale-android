package com.cdkj.wzcd.module.business.cllh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.adapter.CllhListAdapter;
import com.cdkj.wzcd.model.CllhListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆落户
 */
public class CllhListActivity extends AbsRefreshListActivity<CllhListBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CllhListActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("车辆落户");

        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        ArrayList<CllhListBean> data = new ArrayList<CllhListBean>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        CllhListAdapter mAdapter = new CllhListAdapter(data);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CllhInputMessageActivity.open(CllhListActivity.this);
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
