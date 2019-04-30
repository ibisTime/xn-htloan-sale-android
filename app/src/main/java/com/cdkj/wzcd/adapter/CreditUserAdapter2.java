package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.View;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCreditPersonBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditUserAdapter2 extends BaseQuickAdapter<ZXDetialsBean.ListBean.CreditUserListBean, BaseViewHolder> {

    boolean isInput;
    private ItemCreditPersonBinding mBinding;

    List<DataDictionary> mRole = new ArrayList<>();
    List<DataDictionary> mRelation = new ArrayList<>();

    public CreditUserAdapter2(@Nullable List<ZXDetialsBean.ListBean.CreditUserListBean> data, boolean isInput) {
        super(R.layout.item_credit_person, data);
        this.isInput = isInput;
        mRole = BizTypeHelper.getParentList("credit_user_loan_role");
        mRelation = BizTypeHelper.getParentList("credit_user_relation");
    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean.CreditUserListBean item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        //是否显示录入按钮
        if (isInput) {
            mBinding.tvInput.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvInput.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.tv_input);

        mBinding.myItemNlName.setContent(item.getUserName());
        mBinding.myItemNlPhone.setContent(item.getMobile());
        mBinding.myItemNlId.setContent(item.getIdNo());

        mBinding.myItemNlRole.setContent(DataDictionaryHelper.getValueOnTheKey(item.getLoanRole(), mRole));
        mBinding.myItemNlRelation.setContent(DataDictionaryHelper.getValueOnTheKey(item.getRelation(), mRelation));
    }
}
