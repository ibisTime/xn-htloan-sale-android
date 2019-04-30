package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentZxdfragment1Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

/**
 * 基本信息
 */
public class ZXDFragment1 extends BaseLazyFragment {

    private FragmentZxdfragment1Binding mBinding;
    private ZXDetialsBean.ListBean data;

    public static Fragment getInstance() {
        ZXDFragment1 fragment = new ZXDFragment1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_zxdfragment1, container, false);
        initView();
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    public void initView() {

        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        if (data == null)
            return;

        mBinding.myDTCode.setTitle(data.getCode());
        mBinding.myDTCompany.setTitle(data.getCompanyName());
//            mBinding.myDTManager.steTitle(credit.get());//区域经理
        mBinding.myDTTeam.setTitle(data.getTeamName());//团队
//        mBinding.myDTXdzy.setTitle(data.getTeamName());//信贷专员
//        mBinding.myDTYwnq.setTitle(data.getTeamName());//业务内勤
        mBinding.myDTMoney.setTitle(StringUtils.formatNum(data.getLoanAmount()));//贷款金额
        mBinding.myDTBank.setTitle(data.getLoanBankName());
//        mBinding.myDTLocation.setTitle(data.getLoanBankName());//档案存放位置
//        mBinding.myDTCatalog.setTitle(data.getLoanBankName());//档案目录
//        mBinding.myDTCatalog.setTitle(data.getLoanBankName());//档案目录
        ZXDetialsBean.ListBean.CreditUserBean credit = data.getCreditUser();
        if (credit != null) {
            mBinding.myDTName.setTitle(credit.getUserName());
        }
    }
}
