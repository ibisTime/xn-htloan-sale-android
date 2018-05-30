package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.UserToVoidBean;
import com.cdkj.wzcd.view.MyNormalLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author qi
 * @updateDts 2018/5/30
 */

public class UserToVoidAdapter extends BaseQuickAdapter<UserToVoidBean, BaseViewHolder> {
    public UserToVoidAdapter(@Nullable List<UserToVoidBean> data) {
        super(R.layout.item_user_to_void, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserToVoidBean item) {

        MyNormalLayout mnl_code_type = helper.getView(R.id.mnl_code_type);//业务编号--状态
        MyNormalLayout mnl_name_money = helper.getView(R.id.mnl_name_money);//客户姓名--金额
        MyNormalLayout mnl_bank_name = helper.getView(R.id.mnl_bank_name);//贷款银行   只有一个
        MyNormalLayout mnl_is_time = helper.getView(R.id.mnl_is_time);//是否垫资--申请日期


    }
}
