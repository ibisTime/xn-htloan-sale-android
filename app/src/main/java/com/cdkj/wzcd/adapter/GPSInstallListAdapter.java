package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * GPS 安装列表
 * Created by cdkj on 2018/4/9.
 */

public class GPSInstallListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GPSInstallListAdapter(@Nullable List<String> data) {
        super(R.layout.item_gps_installa, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item == null) return;

    }
}
