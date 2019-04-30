package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemAuditBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class ZRFlowingWaterAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    private ItemAuditBinding mBinding;

    public ZRFlowingWaterAdapter(@Nullable List<Object> data) {
        super(R.layout.item_zrflowing_water, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
