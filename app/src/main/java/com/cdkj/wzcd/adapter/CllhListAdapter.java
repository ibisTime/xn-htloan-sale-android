package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;
import android.widget.Button;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.CllhListBean;
import com.cdkj.wzcd.view.MyNormalLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author qi
 * @updateDts 2018/5/30
 */

public class CllhListAdapter extends BaseQuickAdapter<CllhListBean, BaseViewHolder> {
    public CllhListAdapter(@Nullable List<CllhListBean> data) {
        super(R.layout.item_cllh_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CllhListBean item) {

        MyNormalLayout mnl_code_type = helper.getView(R.id.mnl_code_type);//业务编号--状态
        MyNormalLayout mnl_name_type = helper.getView(R.id.mnl_name_type);//客户姓名--垫资金额
        MyNormalLayout mnl_bank_name = helper.getView(R.id.mnl_bank_name);//贷款银行   只有一个
        MyNormalLayout mnl_is_time = helper.getView(R.id.mnl_is_time);//是都垫资金--申请日期

        Button btn_input = helper.getView(R.id.btn_input);//录入按钮


    }
}
