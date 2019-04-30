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
import com.cdkj.wzcd.adapter.ZXLBFragmentAdapter;
import com.cdkj.wzcd.databinding.FragmentZxdfragment2Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 征信列表
 */
public class ZXLBFragment2 extends BaseLazyFragment {

    private FragmentZxdfragment2Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZXLBFragment2 fragment = new ZXLBFragment2();
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_zxdfragment2, container, false);
        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        if (data == null)
            return;
        ZXLBFragmentAdapter adapter = new ZXLBFragmentAdapter(data.getCreditUserList());
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(adapter);
//        EmptyViewBinding emptyViewBinding = DataBindingUtil.inflate(getLayoutInflater(), com.cdkj.baselibrary.R.layout.empty_view, null, false);
//        emptyViewBinding.tv.setText("暂无数据");
//        adapter.setEmptyView(emptyViewBinding.getRoot());
    }
}
