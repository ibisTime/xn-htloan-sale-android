package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.logAdapter;
import com.cdkj.wzcd.databinding.ActivityLogBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;

/**
 * 征信派单
 */
public class LogActivity extends AbsBaseLoadActivity {

    //    private RefreshHelper mRefreshHelper;
    private ActivityLogBinding mBinding;
    ArrayList<ZXDetialsBean.ListBean.BizLogsBean> logsData;


    public static void open(Context context, ArrayList<ZXDetialsBean.ListBean.BizLogsBean> logsData) {
        Intent intent = new Intent(context, LogActivity.class);
        intent.putExtra(CdRouteHelper.DATA_SIGN, logsData);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_log, null, false);
        mBaseBinding.titleView.setMidTitle("操作日志");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            logsData = (ArrayList<ZXDetialsBean.ListBean.BizLogsBean>) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
        }
        initListener();
        initRecyvlerView();
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            finish();
        });
    }

    private void initRecyvlerView() {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        logAdapter logAdapter = new logAdapter(logsData);
        mBinding.rv.setAdapter(logAdapter);
    }
    //下面是有刷新的代码  这个数据是从上个界面传递过来的  是全部的数据 所以不用刷新
//    private void initRecyvlerView() {
//
//        mRefreshHelper = new RefreshHelper<LogBean>(this, new BaseRefreshCallBack(this) {
//            @Override
//            public View getRefreshLayout() {
//                return mBinding.refreshLayout;
//            }
//
//            @Override
//            public RecyclerView getRecyclerView() {
//                return mBinding.rv;
//            }
//
//            @Override
//            public RecyclerView.Adapter getAdapter(List listData) {
//                logAdapter logAdapter = new logAdapter(listData);
//                return logAdapter;
//            }
//
//            @Override
//            public void getListDataRequest(int pageIndex, int limit, boolean isShowDialog) {
//                getData(pageIndex, limit, isShowDialog);
//            }
//        });
//        mRefreshHelper.init(10);
//        mRefreshHelper.onDefaultMRefresh(true);
//
//    }
//
//    private void getData(int pageIndex, int limit, boolean isShowDialog) {
//        ArrayList<LogBean> data = new ArrayList<>();
//        data.add(new LogBean());
//        data.add(new LogBean());
//        mRefreshHelper.setData(data, "暂无数据", 0);
//    }
}
