package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCreditPeopleDetailsBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;

public class CreditPeopleDetailsActivity extends AbsBaseLoadActivity {

    private ActivityCreditPeopleDetailsBinding mBinding;
    private ZXDetialsBean.ListBean.CreditUserListBean creditUserListBean;//列表bean
    private ZXDetialsBean.ListBean.CreditUserBean creditUserBean;//主贷人bean

//    public static void open(Context context) {
//        Intent intent = new Intent(context, CreditPeopleDetailsActivity.class);
//        context.startActivity(intent);
//    }

    public static void open(Context context, ZXDetialsBean.ListBean.CreditUserListBean creditUserListBean) {
        Intent intent = new Intent(context, CreditPeopleDetailsActivity.class);
        intent.putExtra("creditUserListBean", creditUserListBean);
        context.startActivity(intent);
    }

    public static void open(Context context, ZXDetialsBean.ListBean.CreditUserBean creditUserBean) {
        Intent intent = new Intent(context, CreditPeopleDetailsActivity.class);
        intent.putExtra("creditUserBean", creditUserBean);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_people_details, null, false);
        mBaseBinding.titleView.setMidTitle("征信人详情");
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        //这里暂时不用上传照片 所以  后面的请求码和回调码随便写了一下
        mBinding.myIlIdCardImg.setActivity(this, 0, 0);
        mBinding.myIlZxcxsqsImg.setActivity(this, 0, 0);
        mBinding.myIlMqImg.setActivity(this, 0, 0);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            creditUserListBean = (ZXDetialsBean.ListBean.CreditUserListBean) getIntent().getSerializableExtra("creditUserListBean");
            creditUserBean = (ZXDetialsBean.ListBean.CreditUserBean) getIntent().getSerializableExtra("creditUserBean");
        }
        setViewData();
    }

    private void setViewData() {
        if (creditUserListBean != null) {
            //设置列表的数据
            setCreditUserListViewData();
        }
        if (creditUserBean != null) {
            setCreditUserBeanViewData();
        }

    }

    private void setCreditUserBeanViewData() {
        mBinding.tvName.setText(creditUserBean.getUserName());
        mBinding.tvPhone.setText(creditUserBean.getMobile());
        //身份证照片
        mBinding.myIlIdCardImg.setFlImgByRequest(creditUserBean.getIdFront());
        mBinding.myIlIdCardImg.setFlImgRightByRequest(creditUserBean.getIdReverse());
        //征信报告书
        mBinding.myIlZxcxsqsImg.setFlImgByRequest(creditUserBean.getAuthPdf());
        //面签照片
        mBinding.myIlMqImg.setFlImgByRequest(creditUserBean.getInterviewPic());
    }

    private void setCreditUserListViewData() {
        mBinding.tvName.setText(creditUserListBean.getUserName());
        mBinding.tvPhone.setText(creditUserListBean.getMobile());
        //身份证照片
        mBinding.myIlIdCardImg.setFlImgByRequest(creditUserListBean.getIdFront());
        mBinding.myIlIdCardImg.setFlImgRightByRequest(creditUserListBean.getIdReverse());
        //征信报告书
        mBinding.myIlZxcxsqsImg.setFlImgByRequest(creditUserListBean.getAuthPdf());
        //面签照片
        mBinding.myIlMqImg.setFlImgByRequest(creditUserListBean.getInterviewPic());
    }
}
