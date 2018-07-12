package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.SearchModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/7/9.
 */

public class SearchAdapter extends BaseQuickAdapter<SearchModel, BaseViewHolder> {

    public SearchAdapter(@Nullable List<SearchModel> data) {
        super(R.layout.item_search_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchModel item) {
        helper.setText(R.id.tv_search_type, item.getKeyTypeText());
    }
}
