package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemDataTransferBinding;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.module.datatransfer.SendActivity;
import com.cdkj.wzcd.module.datatransfer.SendAndExamineActivity;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.NodeHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import static com.cdkj.wzcd.module.datatransfer.DataTransferListFragment.DATA_GPS;
import static com.cdkj.wzcd.module.datatransfer.DataTransferListFragment.DATA_OTHER;
import static com.cdkj.wzcd.module.datatransfer.DataTransferListFragment.DATA_SEND;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class DataTransferAdapter extends BaseQuickAdapter<DataTransferModel, BaseViewHolder> {

    private ItemDataTransferBinding mBinding;
    private List<DataDictionary> mCompany;
    String type;
    //再发件界面的时候只能点击  发件操作  收件的操作 只展示不能点击    再收件界面道理一样 只能点击收件的操作

    public DataTransferAdapter(@Nullable List<DataTransferModel> data, List<DataDictionary> company) {
        super(R.layout.item_data_transfer, data);

        mCompany = company;
    }

    public DataTransferAdapter(@Nullable List<DataTransferModel> data, List<DataDictionary> company, String type) {
        super(R.layout.item_data_transfer, data);
        this.type = type;
        mCompany = company;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataTransferModel item) {

        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getBizCode(), "");

        mBinding.myIlFrom.setText(NodeHelper.getNameOnTheCode(item.getFromNodeCode()));
        mBinding.myIlTo.setText(NodeHelper.getNameOnTheCode(item.getToNodeCode()));
        if (DATA_GPS.equals(type)) {
            mBinding.myIlFrom.setVisibility(View.GONE);
            mBinding.myIlTo.setVisibility(View.GONE);
            mBinding.llGps.setVisibility(View.VISIBLE);
            DataTransferModel.GpsApply gpsApply = item.getGpsApply();
            mBinding.myIlName.setText(gpsApply == null ? "" : gpsApply.getCustomerName());
            mBinding.myIlApplyWiredCount.setText(gpsApply == null ? "" : gpsApply.getApplyWiredCount() + "");//gps有限个数
            mBinding.myIlApplyWirelessCount.setText(gpsApply == null ? "" : gpsApply.getApplyWirelessCount() + "");//gps无线个数

        } else {
            mBinding.myIlName.setText(item.getCustomerName());
            mBinding.llGps.setVisibility(View.GONE);
        }
        mBinding.myIlCompany.setText(DataDictionaryHelper.getValueOnTheKey(item.getLogisticsCompany(), mCompany));
        mBinding.myIlExpress.setText(item.getLogisticsCode());
        mBinding.myItemCblConfirm.setContent("", "");
        mBinding.myIlStatus.setText(getStatus(item));

        mBinding.myIlSenderName.setText(item.getSenderName());
        mBinding.myIlReceiverName.setText(item.getReceiverName());
        mBinding.myIlTeamName.setText(item.getTeamName());
        mBinding.myIlXinDai.setText(item.getSaleUserName());
        mBinding.myIlNeiQin.setText(item.getInsideJobName());
    }

    private String getStatus(DataTransferModel item) {
        // 状态(0 待发件 1已发件待收件 2已收件审核 3已收件待补件)
        switch (item.getStatus()) {
            case "0":
                //发件
                if (TextUtils.equals(DATA_SEND, type)) {
                    mBinding.myItemCblConfirm.setRightTextAndListener("发件", view -> {
                        //发件
                        SendActivity.open(mContext, item.getCode());
                    });
                } else {
                    mBinding.myItemCblConfirm.setRightTextAndListener("", null);
                }
                return "待发件";

            case "1":
                if (TextUtils.equals(DATA_SEND, type)) {
                    mBinding.myItemCblConfirm.setRightTextAndListener("", null);
                } else if (TextUtils.equals(DATA_OTHER, type) || TextUtils.equals(DATA_GPS, type)) {
                    mBinding.myItemCblConfirm.setRightTextAndListener("收件并审核", view -> {
                        SendAndExamineActivity.open(mContext, item.getCode(), TextUtils.equals(DATA_GPS, type));
                    });
                }
                return "已发件待收件";
            case "2":
                return "已收件审核";
            case "3":

                if (TextUtils.equals(DATA_SEND, type)) {
                    mBinding.myItemCblConfirm.setRightTextAndListener("发件", view -> {
                        SendActivity.open(mContext, item.getCode());
                    });
                } else {
                    mBinding.myItemCblConfirm.setRightTextAndListener("", null);
                }

                return "已收件待补件";

            default:
                return "";

        }
    }
}
