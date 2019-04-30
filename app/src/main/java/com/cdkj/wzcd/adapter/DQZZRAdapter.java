package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemInterviewBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.zrdxq.ZRActivity;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class DQZZRAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean, BaseViewHolder> {

    private ItemInterviewBinding mBinding;
    private List<DataDictionary> mType;

    public DQZZRAdapter(@Nullable List<ZXDetialsBean.ListBean> data) {
        super(R.layout.item_interview, data);
        ArrayList<DataDictionary> parentList = BizTypeHelper.getParentList(BizTypeHelper.budget_orde_biz_typer);
        mType = parentList;
    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));
        mBinding.myIlType.setText(DataDictionaryHelper.getValueOnTheKey(item.getBizType(), mType));
        mBinding.myIlName.setText(item.getCreditUser().getUserName());
        mBinding.myIlBank.setText(item.getLoanBankName());
        mBinding.myIlAmount.setText(MoneyUtils.MONEYSING + StringUtils.formatNum(item.getLoanAmount()));
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        mBinding.myItemCblConfirm.setRightTextAndListener(NodeHelper.getNameOnTheCode(item.getCurNodeCode()), view -> {
            ZRActivity.open(mContext, item);
        });
    }
}
