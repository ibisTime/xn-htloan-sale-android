package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemInterviewBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class InterviewListAdapter extends BaseQuickAdapter<NodeListModel, BaseViewHolder> {

    private ItemInterviewBinding mBinding;
    private List<DataDictionary> mType;

    public InterviewListAdapter(@Nullable List<NodeListModel> data, List<DataDictionary> type) {
        super(R.layout.item_interview, data);

        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, NodeListModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));

        mBinding.myIlType.setText(DataDictionaryHelper.getValueOnTheKey(item.getBizType(), mType));
        mBinding.myIlName.setText(item.getApplyUserName());
        mBinding.myIlBank.setText(item.getLoanBankName());
        mBinding.myIlAmount.setText(MoneyUtils.MONEYSING + RequestUtil.formatAmountDiv(item.getLoanAmount()));
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));
    }
}
