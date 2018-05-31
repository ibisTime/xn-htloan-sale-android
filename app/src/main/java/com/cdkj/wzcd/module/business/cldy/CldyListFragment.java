package com.cdkj.wzcd.module.business.cldy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.wzcd.adapter.CllhListAdapter;
import com.cdkj.wzcd.model.CllhListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.baselibrary.appmanager.CdRouteHelper.IS_FIRST_REQUEST;

/**
 * 车辆抵押  嵌套的Fragment
 */
public class CldyListFragment extends AbsRefreshListFragment {

    /**
     * @param
     * @return
     */
    public static CldyListFragment getInstance(Boolean isFirstRequest, String status) {
        CldyListFragment fragment = new CldyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, status);
        bundle.putBoolean(IS_FIRST_REQUEST, isFirstRequest);
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
                CldyInputMessageActivity.open(mActivity);
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