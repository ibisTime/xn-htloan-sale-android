package com.cdkj.wzcd.adpter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditPersonBinding;
import com.cdkj.wzcd.model.CreditPersonModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditPersonAdapter extends BaseQuickAdapter<CreditPersonModel, BaseViewHolder> {

    private ItemCreditPersonBinding mBinding;

    public CreditPersonAdapter(@Nullable List<CreditPersonModel> data) {
        super(R.layout.item_credit_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditPersonModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myItemNlName.setContext(item.getUserName());
        mBinding.myItemNlPhone.setContext(item.getMobile());
        mBinding.myItemNlId.setContext(item.getIdNo());
        mBinding.myItemNlRole.setContext(item.getLoanRole());
        mBinding.myItemNlRelation.setContext(item.getRelation());
    }
}
