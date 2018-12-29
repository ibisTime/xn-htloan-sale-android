package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemCllhListBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.module.business.cldy.CldyApplyActivity;
import com.cdkj.wzcd.module.business.cldy.CldyInputMessageActivity;
import com.cdkj.wzcd.module.business.cldy.DysqActivity;
import com.cdkj.wzcd.module.business.cldy.LrdyActivity;
import com.cdkj.wzcd.module.business.cldy.MortgageFinishActivity;
import com.cdkj.wzcd.module.business.cldy.NqlrActivity;
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

public class CldyListAdapter extends BaseQuickAdapter<NodeListModel, BaseViewHolder> {

    private ItemCllhListBinding mBinding;

    public CldyListAdapter(@Nullable List<NodeListModel> data) {
        super(R.layout.item_cllh_list, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, NodeListModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getCurNodeCode()));

        mBinding.myIlName.setText(item.getApplyUserName());
        mBinding.myIlType.setText(BizTypeHelper.getNameOnTheKey(item.getBizType()));
        mBinding.myIlAmount.setText(RequestUtil.formatAmountDivSign(item.getAdvanceFundAmount()));
        mBinding.myIlBank.setText(item.getLoanBankName());
        mBinding.myIlAdvanceFund.setText(TextUtils.equals(item.getIsAdvanceFund(), "1") ? "已垫资" : "未垫资");
        mBinding.myIlDateTime.setText(DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        mBinding.myItemCblConfirm.setContent("", "");

//        if (UserHelper.isZHRY()) {

            if (TextUtils.equals(item.getCurNodeCode(), "002_21")) { // 抵押完成
                mBinding.myItemCblConfirm.setRightTextAndListener("抵押完成", view -> {
                    MortgageFinishActivity.open(mContext, item.getCode());
                });
            } else if (TextUtils.equals(item.getCurNodeCode(), "002_20")) { // 抵押完成
                mBinding.myItemCblConfirm.setRightTextAndListener("抵押提交银行", view -> {
                    CldyApplyActivity.open(mContext, item.getCode());
                });
            }

//        } else {
//            if (UserHelper.isYWY()){
            if (TextUtils.equals(item.getCurNodeCode(), "002_18")) { // 业务团队车辆抵押
                mBinding.myItemCblConfirm.setRightTextAndListener("录入抵押信息", view -> {
                    CldyInputMessageActivity.open(mContext, item.getCode());
                });
            }
//        }
//        }

        if (TextUtils.equals(item.getCurNodeCode(), "002_33")) { // 业务团队车辆抵押
            mBinding.myItemCblConfirm.setRightTextAndListener("抵押申请", view -> {
                DysqActivity.open(mContext, item.getCode());
            });
        }
        if (TextUtils.equals(item.getCurNodeCode(), "002_34")) { // 业务团队车辆抵押
            mBinding.myItemCblConfirm.setRightTextAndListener("内勤确认", view -> {
                NqlrActivity.open(mContext, item.getCode());
            });
        }

        if (TextUtils.equals(item.getCurNodeCode(), "002_21")) { // 抵押完成
            mBinding.myItemCblConfirm.setRightTextAndListener("录入抵押信息", view -> {
                LrdyActivity.open(mContext, item.getCode());
            });
        }
    }
}
