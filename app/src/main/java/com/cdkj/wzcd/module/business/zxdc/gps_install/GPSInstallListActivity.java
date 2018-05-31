package com.cdkj.wzcd.module.business.zxdc.gps_install;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.GPSInstallListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * GPS 安装
 * Created by cdkj on 2018/5/30.
 */

public class GPSInstallListActivity extends AbsRefreshListActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GPSInstallListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.gps_install));
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
        GPSInstallListAdapter gpsInstallListAdapter = new GPSInstallListAdapter(listData);
        gpsInstallListAdapter.setOnItemClickListener((adapter, view, position) -> {
            GPSInstallInfoActivity.open(GPSInstallListActivity.this);
        });
        return gpsInstallListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

    }
}
