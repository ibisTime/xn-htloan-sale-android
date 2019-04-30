package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.RepaymentListAdapter;
import com.cdkj.wzcd.databinding.ActivityRepaymentList2Binding;
import com.cdkj.wzcd.model.RepaymentListBean;

import java.util.ArrayList;

public class RepaymentListActivity extends AbsBaseLoadActivity {

    private ActivityRepaymentList2Binding mBinding;

    public static void open(Context context) {
        Intent intent = new Intent(context,RepaymentListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_repayment_list2, null, false);
        mBaseBinding.titleView.setMidTitle("还款计划表");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initRecyelcrView();
    }

    private void initRecyelcrView() {
        ArrayList<RepaymentListBean> data = new ArrayList<>();
        data.add(new RepaymentListBean());
        data.add(new RepaymentListBean());
        RepaymentListAdapter repaymentListAdapter = new RepaymentListAdapter(data);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(repaymentListAdapter);
    }
}
