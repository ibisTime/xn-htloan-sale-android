package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
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

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class DataTransferAdapter extends BaseQuickAdapter<DataTransferModel, BaseViewHolder> {

    private ItemDataTransferBinding mBinding;
    private List<DataDictionary> mCompany;
    boolean isGps;//是不是  gps收件

    public DataTransferAdapter(@Nullable List<DataTransferModel> data, List<DataDictionary> company) {
        super(R.layout.item_data_transfer, data);

        mCompany = company;
    }

    public DataTransferAdapter(@Nullable List<DataTransferModel> data, List<DataDictionary> company, boolean isGps) {
        super(R.layout.item_data_transfer, data);
        this.isGps = isGps;
        mCompany = company;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataTransferModel item) {

        mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.myTlIdStatus.setText(item.getBizCode(), "");

        mBinding.myIlFrom.setText(NodeHelper.getNameOnTheCode(item.getFromNodeCode()));
        mBinding.myIlTo.setText(NodeHelper.getNameOnTheCode(item.getToNodeCode()));
        if (isGps) {
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
                if (!isGps) {
//                    SendActivity.open(mContext, item.getCode(), true);
                    mBinding.myItemCblConfirm.setRightTextAndListener("发件", view -> {
                        //发件
                        SendActivity.open(mContext, item.getCode());
//                        if (isGps) {
//                            SendActivity.open(mContext, item.getCode(), true);
//                        } else {
//                            SendActivity.open(mContext, item.getCode());
//                        }
                    });
                }

                return "待发件";

            case "1":

                mBinding.myItemCblConfirm.setRightTextAndListener("收件并审核", view -> {
                    //收件并审核
                    SendAndExamineActivity.open(mContext, item.getCode(), isGps);

                });
                return "已发件待收件";

            case "2":
                return "已收件审核";

            case "3":
                if (!isGps) {
                    mBinding.myItemCblConfirm.setRightTextAndListener("发件", view -> {
                        //发件
                        if (isGps) {
                            SendActivity.open(mContext, item.getCode(), true);
                        } else {
                            SendActivity.open(mContext, item.getCode());
                        }
                    });
                }
                return "已收件待补件";

            default:
                return "";

        }
    }
}
