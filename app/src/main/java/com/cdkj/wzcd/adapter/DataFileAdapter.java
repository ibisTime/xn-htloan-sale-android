package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemDataReFileBinding;
import com.cdkj.wzcd.model.CLQDBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/6/5.
 */

public class DataFileAdapter extends BaseQuickAdapter<CLQDBean, BaseViewHolder> {

    private ItemDataReFileBinding mBinding;

    public DataFileAdapter(@Nullable List<CLQDBean> data) {
        super(R.layout.item_data_re_file, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CLQDBean item) {
        mBinding = DataBindingUtil.bind(helper.itemView);
        mBinding.tvId.setText("序号" + item.getId());
        mBinding.tvName.setText(item.getName());
        mBinding.tvNo.setText(item.getNo());
//        mBinding.myItemNlFile.setTitle(item.getName());
//        mBinding.myItemNlFile.setContent(item.getNo());
//        LogUtil.E("item = "+item.getFile());
//        mBinding.myItemNlFile.setContent(item.getFile());
    }
}
