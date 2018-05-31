package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class FaceViewListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FaceViewListAdapter(@Nullable List<String> data) {
        super(R.layout.item_face_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item == null) return;

    }
}
