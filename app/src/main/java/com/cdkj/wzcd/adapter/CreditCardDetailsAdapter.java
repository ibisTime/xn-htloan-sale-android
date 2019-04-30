package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 面签列表
 * Created by cdkj on 2018/4/9.
 */

public class CreditCardDetailsAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean.CreditUserListBean, BaseViewHolder> {


    private final ArrayList<DataDictionary> credit_user_relation;
    private final ArrayList<DataDictionary> credit_user_loan_role;

    public CreditCardDetailsAdapter(@Nullable List<ZXDetialsBean.ListBean.CreditUserListBean> data) {
        super(R.layout.item_credit_card_details, data);
        credit_user_relation = BizTypeHelper.getParentList("credit_user_relation");
        credit_user_loan_role = BizTypeHelper.getParentList("credit_user_loan_role");

    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean.CreditUserListBean item) {
        helper.setText(R.id.tv_name, item.getUserName());
        helper.setText(R.id.tv_phone, item.getMobile());
        helper.setText(R.id.tv_idNumber, item.getIdNo());
        String relationNmae = BizTypeHelper.getNameOnTheKey(item.getLoanRole(), credit_user_relation);
        String roleName = BizTypeHelper.getNameOnTheKey(item.getLoanRole(), credit_user_loan_role);
        helper.setText(R.id.tv_people, relationNmae + "/" + roleName);

        if (TextUtils.equals("1", item.getBizCode())) {
            helper.setText(R.id.tv_type, "征信通过");
        } else {
            helper.setText(R.id.tv_type, "征信未通过");
        }
    }
}
