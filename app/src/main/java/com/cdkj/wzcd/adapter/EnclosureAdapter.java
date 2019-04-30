package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class EnclosureAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean.AttachmentsBean, BaseViewHolder> {

    public EnclosureAdapter(@Nullable List<ZXDetialsBean.ListBean.AttachmentsBean> data) {
        super(R.layout.item_eclosure_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean.AttachmentsBean item) {
        helper.setText(R.id.tv_fjlx, item.getVname());
        helper.setText(R.id.tv_zylx, item.getAttachType());
        List<String> strings = StringUtils.splitAsPicList(item.getUrl());
        helper.setText(R.id.tv_zysl, (strings == null ? 0 : strings.size()) + "张");
        helper.setText(R.id.tv_date, item.getBizCode());
    }
}
