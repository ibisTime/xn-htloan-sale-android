package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditListBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.module.business.credit.CreditInitiateActivity;
import com.cdkj.wzcd.module.business.credit.audit.AuditCreditActivity;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.cdkj.wzcd.util.UserHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditListAdapter extends BaseQuickAdapter<CreditModel, BaseViewHolder> {

    private ItemCreditListBinding mBinding;
    private List<DataDictionary> mType;

    private CreditCancelInterface mCancelInterface;

    public CreditListAdapter(@Nullable List<CreditModel> data, List<DataDictionary> type, CreditCancelInterface cancelInterface) {
        super(R.layout.item_credit_list, data);

        mType = type;
        mCancelInterface = cancelInterface;
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));

        mBinding.myIlType.setText(DataDictionaryHelper.getValueOnTheKey(item.getBizType(), mType));
        mBinding.myIlName.setText(item.getCreditUser() == null ? "" : item.getCreditUser().getUserName());
        mBinding.myIlAmount.setText(MoneyUtils.MONEYSING + RequestUtil.formatAmountDiv(item.getLoanAmount()));
        mBinding.myIlOperatorName.setText(item.getOperatorName());
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        new BankHelper(mContext).getValueOnTheKey(item.getLoanBankCode(), mBinding.myIlBank, null);

        mBinding.myItemCblConfirm.setContent("", "");

        if (UserHelper.isZHRY()) {

            if (TextUtils.equals(item.getCurNodeCode(), "001_02")  // 录入征信结果
                    || TextUtils.equals(item.getCurNodeCode(), "001_06")) { // 风控专员审核不通过
                mBinding.myItemCblConfirm.setRightTextAndListener("录入银行征信结果", view -> {
                    AuditCreditActivity.open(mContext, item.getCode());
                });
            } else {
                mBinding.myItemCblConfirm.setContent("", "");
            }

        } else {

            mBinding.myItemCblConfirm.setContent("", "");

            if (TextUtils.equals(item.getCurNodeCode(), "001_01") // 填写征信单
                    || TextUtils.equals(item.getCurNodeCode(), "001_05") // 征信退回，重新发起征信
                    || TextUtils.equals(item.getCurNodeCode(), "001_07")) { // 征信撤回，重新发起征信
                mBinding.myItemCblConfirm.setRightTextAndListener("修改征信信息", view -> {
                    CreditInitiateActivity.open(mContext, item.getCode());
                });
            }

            // 发起征信环节增加撤回功能，发起后直接撤回,如果驻行已录入征信结果则不能撤回
            if (TextUtils.equals(item.getCurNodeCode(), "001_02")) { // 录入征信结果
                mBinding.myItemCblConfirm.setRightTextAndListener("撤回", view -> {
                    mCancelInterface.click(view, item.getCode());
                });
            }
        }

    }

    public interface CreditCancelInterface {
        void click(View view, String code);
    }

}
