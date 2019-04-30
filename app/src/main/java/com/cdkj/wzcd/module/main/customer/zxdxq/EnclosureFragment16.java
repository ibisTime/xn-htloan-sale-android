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
import com.cdkj.wzcd.adapter.EnclosureAdapter;
import com.cdkj.wzcd.databinding.ActivityEnclosureBinding;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 附件池
 */
public class EnclosureFragment16 extends BaseLazyFragment {

    private ActivityEnclosureBinding mBinding;

    public static Fragment getInstance() {
        EnclosureFragment16 fragment = new EnclosureFragment16();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_enclosure, container, false);
        initRecyclerView();
        mBinding.myCbConfirm.setVisibility(View.GONE);
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    private void initRecyclerView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        if (activity.currentItem == null || activity.currentItem.getAttachments() == null) {
            return;
        }
        EnclosureAdapter adapter = new EnclosureAdapter(activity.currentItem.getAttachments());
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(adapter);
    }
}
