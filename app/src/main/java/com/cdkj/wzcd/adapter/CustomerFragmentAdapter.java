package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.view.MyNormalLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 客户列表
 * Created by cdkj on 2018/4/9.
 */

public class CustomerFragmentAdapter extends BaseQuickAdapter<ZXDetialsBean.ListBean, BaseViewHolder> {


    private List<DataDictionary> dataDictionaryList;

    public CustomerFragmentAdapter(@Nullable List<ZXDetialsBean.ListBean> data) {
        super(R.layout.item_customer_fragment, data);
        dataDictionaryList = BizTypeHelper.getParentList(BizTypeHelper.cdbiz_status);

    }

    @Override
    protected void convert(BaseViewHolder helper, ZXDetialsBean.ListBean item) {
        MyNormalLayout myNLTitle = helper.getView(R.id.myNL_title);
        myNLTitle.setTvTitle(item.getCode());
        String valueOnTheKey = DataDictionaryHelper.getValueOnTheKey(item.getStatus(), dataDictionaryList);
        myNLTitle.setText(valueOnTheKey);//这个状态根据数据字典获取设置
        helper.setText(R.id.tv_bank, item.getLoanBankName());
        helper.setText(R.id.tv_money, StringUtils.formatNum(item.getLoanAmount()));
        helper.setText(R.id.tv_date, DateUtil.formatStringData(item.getApplyDatetime(), DateUtil.DEFAULT_DATE_FMT));

        ZXDetialsBean.ListBean.CreditUserBean creditUser = item.getCreditUser();
        if (creditUser != null) {
            helper.setText(R.id.tv_name, creditUser.getUserName());
            if (TextUtils.equals("0", creditUser.getBizCode())) {
                helper.setText(R.id.tv_type, "新车");
            } else {
                helper.setText(R.id.tv_type, "二手车");
            }
        }
    }
}
