package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentApplicant4Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 申请人基本信息
 */
public class ApplicantFragment4 extends BaseLazyFragment {

    private FragmentApplicant4Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ApplicantFragment4 fragment = new ApplicantFragment4();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_applicant4, container, false);
        initView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.mymlDydlrsfzfyj.build(getActivity(), 0);
        mBinding.mymlGfhtjfcb.build(mActivity, 1);
        mBinding.mymlJfzp.build(mActivity, 2);
        mBinding.mymlQtfzzc.build(mActivity, 3);

        mBinding.mymlDydlrsfzfyj.setListData("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1393987749,3422146058&fm=27&gp=0.jpg");

    }

    public void setView() {

    }
}
