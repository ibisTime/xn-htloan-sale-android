package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCllhListBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.module.business.cllh.CllhInputMessageActivity;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class CllhListAdapter extends BaseQuickAdapter<NodeListModel, BaseViewHolder> {

    private ItemCllhListBinding mBinding;

    public CllhListAdapter(@Nullable List<NodeListModel> data) {
        super(R.layout.item_cllh_list, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, NodeListModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getAdvanfCurNodeCode()));

        mBinding.myIlName.setText(item.getApplyUserName());
        mBinding.myIlType.setText(BizTypeHelper.getNameOnTheKey(item.getBizType()));
        mBinding.myIlAmount.setText(RequestUtil.formatAmountDivSign(item.getLoanAmount()));
        mBinding.myIlBank.setText(item.getLoanBankName());
        mBinding.myIlAdvanceFund.setText(TextUtils.equals(item.getIsAdvanceFund(), "1") ? "已垫资" : "未垫资");
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

//        if (TextUtils.equals(item.getCurNodeCode(),"002_11")){ // 业务团队车辆落户
//            mBinding.myItemCblConfirm.setRightTextAndListener("录入", view -> {
//                CllhInputMessageActivity.open(mContext, item.getCode());
//            });
        if (TextUtils.equals(item.getAdvanfCurNodeCode(), "002_18")) { // 业务团队车辆落户
            mBinding.myItemCblConfirm.setRightTextAndListener("录入", view -> {
                CllhInputMessageActivity.open(mContext, item.getCode(),false);
            });
        } else {
            mBinding.myItemCblConfirm.setContent("", "");
        }

    }
}
