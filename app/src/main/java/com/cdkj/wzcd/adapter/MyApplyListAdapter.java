package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 贷前准入列表
 * Created by cdkj on 2018/4/9.
 */

public class MyApplyListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyApplyListAdapter(@Nullable List<String> data) {
        super(R.layout.item_daikuan_zhunru, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item == null) return;

    }
}
