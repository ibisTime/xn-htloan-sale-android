package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.MessageTobeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class MessageTobeAdapter extends BaseQuickAdapter<MessageTobeBean.ListBean, BaseViewHolder> {

    public MessageTobeAdapter(@Nullable List<MessageTobeBean.ListBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageTobeBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getContent());
        helper.setText(R.id.tv_date,DateUtil.formatStringData(item.getCreateDatetime(),DateUtil.DEFAULT_DATE_FMT));
        helper.setText(R.id.tv_type,"待办事项");
    }
}
