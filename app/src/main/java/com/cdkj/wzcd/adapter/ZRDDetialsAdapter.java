package com.cdkj.wzcd.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.ZRDDetialsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class ZRDDetialsAdapter extends BaseQuickAdapter<ZRDDetialsBean, BaseViewHolder> {


    public ZRDDetialsAdapter(@Nullable List<ZRDDetialsBean> data) {
        super(R.layout.item_zrddetials, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ZRDDetialsBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.isSelect()) {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setBackgroundColor(R.id.tv_title, Color.parseColor("#662F93ED"));

        } else {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text_999));
            helper.setBackgroundColor(R.id.tv_title, mContext.getResources().getColor(R.color.color_f6));
        }
    }
}
