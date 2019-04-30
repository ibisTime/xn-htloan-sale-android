package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditListBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.home.front.ZXLRDetails;
import com.cdkj.wzcd.module.home.front.ZXSHDetails;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditListAdapter2 extends BaseQuickAdapter<ZXDetialsBean.ListBean, BaseViewHolder> {

    private ItemCreditListBinding mBinding;
    private List<DataDictionary> mType;


    public CreditListAdapter2(@Nullable List<ZXDetialsBean.ListBean> data) {
        super(R.layout.item_credit_list, data);
        mType = BizTypeHelper.getParentList("budget_orde_biz_typer");
    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));
        mBinding.myIlType.setText(BizTypeHelper.getNameOnTheKey(item.getBizType(), mType));
        mBinding.myIlName.setText(item.getCreditUser() == null ? "" : item.getCreditUser().getUserName());
        mBinding.myIlAmount.setText(MoneyUtils.MONEYSING + StringUtils.formatNum(item.getLoanAmount()));
//        mBinding.myIlOperatorName.setText(item.getOperatorName());
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));
        new BankHelper(mContext).getValueOnTheKey(item.getLoanBank(), mBinding.myIlBank, null);

        mBinding.myItemCblConfirm.setContent("", "");


        if (TextUtils.equals(item.getCurNodeCode(), "a2")) {
            //征信录入
            mBinding.myItemCblConfirm.setRightTextAndListener("录入", view -> {
                ZXLRDetails.open(mContext, item.getCode());
            });
        } else if (TextUtils.equals(item.getCurNodeCode(), "a3")) {
            //征信审核
            mBinding.myItemCblConfirm.setRightTextAndListener("审核", view -> {
                ZXSHDetails.open(mContext, item.getCode());
            });
        } else {
            mBinding.myItemCblConfirm.setContent("", "");
        }
    }
}
