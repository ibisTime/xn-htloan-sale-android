package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditPersonBinding;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditUserAdapter extends BaseQuickAdapter<CreditUserModel, BaseViewHolder> {

    private ItemCreditPersonBinding mBinding;

    public CreditUserAdapter(@Nullable List<CreditUserModel> data) {
        super(R.layout.item_credit_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditUserModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myItemNlName.setContext(item.getUserName());
        mBinding.myItemNlPhone.setContext(item.getMobile());
        mBinding.myItemNlId.setContext(item.getIdNo());

        new DataDictionaryHelper(mContext).getValueOnTheKey(DataDictionaryHelper.credit_user_loan_role,
                 item.getLoanRole(), mBinding.myItemNlRole,null);

        new DataDictionaryHelper(mContext).getValueOnTheKey(DataDictionaryHelper.credit_user_relation,
                item.getRelation() ,mBinding.myItemNlRelation,null);


    }
}
