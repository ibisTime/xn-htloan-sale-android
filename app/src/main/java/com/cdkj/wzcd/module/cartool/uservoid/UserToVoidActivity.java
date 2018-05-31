package com.cdkj.wzcd.module.cartool.uservoid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.adapter.UserToVoidAdapter;
import com.cdkj.wzcd.model.UserToVoidBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户作废
 */
public class UserToVoidActivity extends AbsRefreshListActivity<UserToVoidBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, UserToVoidActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("客户作废");
        mBaseBinding.titleView.setRightTitle("申请作废");
        mBaseBinding.titleView.setRightFraClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请作废
                BlankOutActivity.open(UserToVoidActivity.this);
            }
        });

        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        ArrayList<UserToVoidBean> data = new ArrayList<UserToVoidBean>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        UserToVoidAdapter mAdapter = new UserToVoidAdapter(data);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserToVoidDetailsActivity.open(UserToVoidActivity.this);
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