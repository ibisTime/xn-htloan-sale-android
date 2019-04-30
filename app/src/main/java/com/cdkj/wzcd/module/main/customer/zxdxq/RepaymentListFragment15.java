package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.RepaymentListAdapter;
import com.cdkj.wzcd.databinding.ActivityRepaymentList2Binding;
import com.cdkj.wzcd.model.RepaymentListBean;

import java.util.ArrayList;

/**
 * 还款列表
 */
public class RepaymentListFragment15 extends BaseLazyFragment {

    private ActivityRepaymentList2Binding mBinding;

    public static Fragment getInstance() {
        RepaymentListFragment15 fragment = new RepaymentListFragment15();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_repayment_list2, container, false);
        initRecyclerView();
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    private void initRecyclerView() {
        ArrayList<RepaymentListBean> data = new ArrayList<>();
        data.add(new RepaymentListBean());
        data.add(new RepaymentListBean());
        RepaymentListAdapter repaymentListAdapter = new RepaymentListAdapter(data);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(repaymentListAdapter);
    }
}
