package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemGpsInstallBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.module.business.gps_install.GPSInstallInfoActivity;
import com.cdkj.wzcd.util.NodeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * GPS 安装列表
 * Created by cdkj on 2018/4/9.
 */

public class GpsInstallListAdapter extends BaseQuickAdapter<NodeListModel, BaseViewHolder> {

    private ItemGpsInstallBinding mBinding;
    private List<DataDictionary> mList;

    public GpsInstallListAdapter(@Nullable List<NodeListModel> data, List<DataDictionary> list) {
        super(R.layout.item_gps_install, data);

        mList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, NodeListModel item) {
        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getCode(), NodeHelper.getNameOnTheCode(item.getAdvanfCurNodeCode()));

        mBinding.myIlName.setText(item.getApplyUserName());
        mBinding.myIlCompany.setText(item.getCompanyName());
        mBinding.myIlCode.setText(item.getCarBrand());

        if (TextUtils.equals(item.getAdvanfCurNodeCode(),"002_09") || TextUtils.equals(item.getAdvanfCurNodeCode(),"002_12")){ // 业务团队安装GPS / 业务团队重新安装GPS

            mBinding.myItemCblConfirm.setRightTextAndListener("录入", view -> {
                GPSInstallInfoActivity.open(mContext, item.getCode());
            });
        }else {
            mBinding.myItemCblConfirm.setContent("","");
        }
    }
}
