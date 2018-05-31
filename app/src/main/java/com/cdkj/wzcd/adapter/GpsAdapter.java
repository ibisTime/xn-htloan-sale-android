package com.cdkj.wzcd.adapter;

import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.model.GpsBean;
import com.cdkj.wzcd.view.MyNormalLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author qi
 * @updateDts 2018/5/30
 */

public class GpsAdapter extends BaseQuickAdapter<GpsBean, BaseViewHolder> {
    public GpsAdapter(@Nullable List<GpsBean> data) {
        super(R.layout.item_gps, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GpsBean item) {

        MyNormalLayout mnl_number_time = helper.getView(R.id.mnl_number_time);//申请数量--时间
        MyNormalLayout mnl_company = helper.getView(R.id.mnl_company);//所属公司  就一个
        MyNormalLayout mnl_bank_name = helper.getView(R.id.mnl_user_type);//申领人   状态


    }
}
