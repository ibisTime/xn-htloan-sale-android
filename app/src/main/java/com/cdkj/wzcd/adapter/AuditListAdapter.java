package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemAuditBinding;
import com.cdkj.wzcd.model.RepaymentModel;
import com.cdkj.wzcd.module.business.audit.AuditActivity;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class AuditListAdapter extends BaseQuickAdapter<RepaymentModel, BaseViewHolder> {

    private ItemAuditBinding mBinding;
    private List<DataDictionary> mType;

    public AuditListAdapter(@Nullable List<RepaymentModel> data, List<DataDictionary> type) {
        super(R.layout.item_audit, data);

        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, RepaymentModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));

        mBinding.myIlType.setText(BizTypeHelper.getNameOnTheKey(item.getBudgetOrder().getBizType()));
        mBinding.myIlName.setText(item.getBudgetOrder().getApplyUserName());
        mBinding.myIlBank.setText(item.getLoanBankName());
        mBinding.myIlAmount.setText(MoneyUtils.MONEYSING + RequestUtil.formatAmountDiv(item.getLoanAmount()));
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getBudgetOrder().getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        if (TextUtils.equals(item.getCurNodeCode(),"003_03")){ // 驻行人员审核
            mBinding.myItemCblConfirm.setRightTextAndListener("审核", view -> {
                AuditActivity.open(mContext, item.getCode());
            });
        }else {
            mBinding.myItemCblConfirm.setContent("","");
        }
    }
}
