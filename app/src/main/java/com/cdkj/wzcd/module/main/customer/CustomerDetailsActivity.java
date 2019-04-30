package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCustomerDetailsBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailsActivity extends AbsBaseLoadActivity {
    private ActivityCustomerDetailsBinding mBinding;
    private ZXDetialsBean.ListBean currentItem;
    public static ArrayList<DataDictionary> dataDictionaryList;

    public static void open(Context context, ZXDetialsBean.ListBean currentItem) {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra("item", currentItem);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_customer_details, null, false);
        mBaseBinding.titleView.setMidTitle("业务详情");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            currentItem = (ZXDetialsBean.ListBean) getIntent().getSerializableExtra("item");
        }
        dataDictionaryList = BizTypeHelper.getParentList(BizTypeHelper.cdbiz_status);

        setView();
        initListener();
    }

    private void setView() {
        if (currentItem == null)
            return;

        mBinding.tvCode.setText(currentItem.getCode());
        ZXDetialsBean.ListBean.CreditUserBean credit = currentItem.getCreditUser();
        if (credit != null) {
            mBinding.tvBank.setText(currentItem.getLoanBankName());
            mBinding.tvName.setText(credit.getUserName());
            if (TextUtils.equals(credit.getBizCode(), "0")) {
                mBinding.tvType.setText("新车");
            } else {
                mBinding.tvType.setText("二手车");
            }
            mBinding.tvMoney.setText(StringUtils.formatNum(currentItem.getLoanAmount()));
            mBinding.tvYwy.setText(currentItem.getTeamName());
//            mBinding.tvNq.setText(currentItem.getInsideJobName());
        }
        String valueOnTheKey = DataDictionaryHelper.getValueOnTheKey(currentItem.getStatus(), dataDictionaryList);
        mBinding.tvJiedian.setText(valueOnTheKey);
        mBinding.tvDb.setText("(" + currentItem.getBizTasks().size() + ")个");
        mBinding.tvRz.setText("(" + currentItem.getBizLogs().size() + ")条");
        mBinding.tvFjxq.setText("(" + currentItem.getAttachments().size() + ")个");
    }

    private void initListener() {
        //代办事项
        mBinding.llDb.setOnClickListener(v -> {
            List<ZXDetialsBean.ListBean.BizTasksBean> bizTasks = currentItem.getBizTasks();
            if (bizTasks == null || bizTasks.size() == 0) {
                UITipDialog.showFail(this, "暂无待办事项");
                return;
            }
            ToBeActivity.open(this, (ArrayList<ZXDetialsBean.ListBean.BizTasksBean>) bizTasks);
        });
        //日志
        mBinding.llRz.setOnClickListener(v -> {
            List<ZXDetialsBean.ListBean.BizLogsBean> bizLogs = currentItem.getBizLogs();
            if (bizLogs == null || bizLogs.size() == 0) {
                UITipDialog.showFail(this, "暂无日志数据");
                return;
            }
            LogActivity.open(this, (ArrayList<ZXDetialsBean.ListBean.BizLogsBean>) currentItem.getBizLogs());
        });

        //征信详情
        mBinding.llZxdxq.setOnClickListener(v -> {
            CreditCardDetailsActivity.open(this, currentItem);
        });
        //准入单详情
        mBinding.llZrdxq.setOnClickListener(v -> {
            ZRDDetialsActivity.open(this,currentItem);
        });
        //附件详情
        mBinding.llFjxq.setOnClickListener(v -> {
            ArrayList<ZXDetialsBean.ListBean.AttachmentsBean> attachments = (ArrayList<ZXDetialsBean.ListBean.AttachmentsBean>) currentItem.getAttachments();
            if (attachments == null || attachments.size() == 0) {
                UITipDialog.showFail(this, "暂无附件数据");
                return;
            }
            EnclosureActivity.open(this, attachments);
        });
        //还款计划表
        mBinding.llHkjhb.setOnClickListener(v -> {
            RepaymentListActivity.open(this);
        });
    }
}
