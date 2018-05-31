package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditListBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditListAdapter extends BaseQuickAdapter<CreditModel, BaseViewHolder> {

    private ItemCreditListBinding mBinding;

    public CreditListAdapter(@Nullable List<CreditModel> data) {
        super(R.layout.item_credit_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myItemNlIdStatus.setContext(item.getCode(),item.getStatus());

        if (item.getCreditUser() != null)
            mBinding.myItemNlNameAmount.setContext(item.getCreditUser().getUserName(),
                    MoneyUtils.MONEYSING + RequestUtil.formatAmountDiv(item.getLoanAmount()));

        new DataDictionaryHelper(mContext).getValueOnTheKey(DataDictionaryHelper.budget_orde_biz_typer, item.getBizType(),
                mBinding.myItemNlType, null);

        new BankHelper(mContext).getValueOnTheKey(item.getLoanBankCode(), mBinding.myItemNlBank, null);

        mBinding.myItemNlDateTime.setContext(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));
    }
}
