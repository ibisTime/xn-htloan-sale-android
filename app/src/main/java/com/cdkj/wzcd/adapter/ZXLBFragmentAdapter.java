package com.cdkj.wzcd.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemZxdfragment2Binding;
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

public class ZXLBFragmentAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean.CreditUserListBean, BaseViewHolder> {


    private final ArrayList<DataDictionary> credit_user_relation;
    private final ArrayList<DataDictionary> credit_user_loan_role;
    ItemZxdfragment2Binding mbinding;

    public ZXLBFragmentAdapter(@Nullable List<ZXDetialsBean.ListBean.CreditUserListBean> data) {
        super(R.layout.item_zxdfragment2, data);
        credit_user_relation = BizTypeHelper.getParentList(BizTypeHelper.credit_user_relation);
        credit_user_loan_role = BizTypeHelper.getParentList(BizTypeHelper.credit_user_loan_role);

    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean.CreditUserListBean item) {
        mbinding = DataBindingUtil.bind(helper.itemView);
        initView();
        mbinding.myDTName.setTitle(item.getUserName());
        String relationNmae = BizTypeHelper.getNameOnTheKey(item.getLoanRole(), credit_user_relation);
        mbinding.myDTRelationship.setTitle(relationNmae);
        String roleName = BizTypeHelper.getNameOnTheKey(item.getLoanRole(), credit_user_loan_role);
        mbinding.myDTRole.setTitle(roleName);
        mbinding.myDTPhone.setTitle(item.getMobile());
        mbinding.myDTIdNo.setTitle(item.getIdNo());
        mbinding.myIlZxsqsImg.setFlImgByRequest(item.getDataCreditReport());
//        mbinding.myIlMqImg.setFlImgByRequest(item.getDataCreditReport());
//        mbinding.myIlZxbgImg.setFlImgByRequest(item.getDataCreditReport());
        mbinding.myDTZxjg.setTitle(item.getBankCreditResultRemark());
        mbinding.myDTZxjg.setTitle(item.getBankCreditResultRemark());


//        helper.setText(R.id.tv_phone, item.getMobile());
//        helper.setText(R.id.tv_idNumber, item.getIdNo());
//
//        String roleName = BizTypeHelper.getNameOnTheKey(item.getLoanRole(), credit_user_loan_role);
//        helper.setText(R.id.tv_people, relationNmae + "/" + roleName);
//
//        if (TextUtils.equals("1", item.getBizCode())) {
//            helper.setText(R.id.tv_type, "征信通过");
//        } else {
//            helper.setText(R.id.tv_type, "征信未通过");
//        }


        mbinding.myIlIdCard.setFlImgByRequest(item.getIdFront());
        mbinding.myIlIdCard.setFlImgRightByRequest(item.getIdReverse());
    }

    private void initView() {
        mbinding.myIlIdCard.setActivity((Activity) mContext, 0, 0);
        mbinding.myIlZxsqsImg.setActivity((Activity) mContext, 0, 0);
        mbinding.myIlMqImg.setActivity((Activity) mContext, 0, 0);
        mbinding.myIlZxbgImg.setActivity((Activity) mContext, 0, 0);
    }
}
