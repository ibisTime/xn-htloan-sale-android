package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.MessageSystemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class MessageSystemAdapter extends BaseQuickAdapter<MessageSystemBean.ListBean, BaseViewHolder> {

    public MessageSystemAdapter(@Nullable List<MessageSystemBean.ListBean> data) {
        super(R.layout.item_message, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MessageSystemBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getContent());
        helper.setText(R.id.tv_date,DateUtil.formatStringData(item.getCreateDatetime(),DateUtil.DEFAULT_DATE_FMT));
        helper.setText(R.id.tv_type,"系统公告");
    }
}
