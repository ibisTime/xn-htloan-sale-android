package com.cdkj.wzcd.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemFragmentGpsInstall13Binding;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/30.
 */

public class GPSInstall13Adapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    private List<DataDictionary> mType;
    private ItemFragmentGpsInstall13Binding mBinding;


    public GPSInstall13Adapter(@Nullable List<Object> data) {
        super(R.layout.item_fragment_gps_install13, data);
        mType = BizTypeHelper.getParentList("budget_orde_biz_typer");
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        mBinding = DataBindingUtil.bind(helper.itemView);
        mBinding.myMlAztp.build((Activity) mContext,1);
        mBinding.myMlSbtp.build((Activity) mContext,1);
    }
}
