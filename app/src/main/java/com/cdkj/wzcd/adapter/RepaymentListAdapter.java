package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.RepaymentListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * X
 *
 * @updateDts 2018/5/30
 */

public class RepaymentListAdapter extends BaseQuickAdapter<RepaymentListBean, BaseViewHolder> {

    public RepaymentListAdapter(@Nullable List<RepaymentListBean> data) {
        super(R.layout.item_repayment_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepaymentListBean item) {

    }
}
