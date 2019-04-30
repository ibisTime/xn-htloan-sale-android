package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.NodeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class logAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean.BizLogsBean, BaseViewHolder> {

    public logAdapter(@Nullable List<ZXDetialsBean.ListBean.BizLogsBean> data) {
        super(R.layout.item_log, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean.BizLogsBean item) {
//        String cdbiz_status = BizTypeHelper.getNameOnTheKey("cdbiz_status", item.getRefType());
        helper.setText(R.id.tv_type, item.getDealNote());
        helper.setText(R.id.tv_step, NodeHelper.getNameOnTheCode(item.getDealNode()));
        helper.setText(R.id.tv_name, item.getOperatorName());
        helper.setText(R.id.tv_date, DateUtil.formatStringData(item.getEndDatetime(), DateUtil.DEFAULT_DATE_FMT));

    }
}
