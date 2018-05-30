package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCreditPersonAddBinding;
import com.cdkj.wzcd.model.CreditPersonModel;
import com.cdkj.wzcd.view.MySelectLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditPersonAddActivity extends AbsBaseLoadActivity {

    private ActivityCreditPersonAddBinding mBinding;

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditPersonAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_person_add, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("添加征信人");


        initCustomView();

        initListener();
    }

    private void initCustomView() {

        mBinding.mySlRole.setData(this, MySelectLayout.DATA_DICTIONARY, "credit_user_loan_role",null);
        mBinding.mySlRelation.setData(this, MySelectLayout.DATA_DICTIONARY, "credit_user_relation",null);

        mBinding.myIlIdCard.setActivity(this,1,2);
        mBinding.myIlCredit.setActivity(this,3,0);
        mBinding.myIlInterview.setActivity(this,4,0);
    }

    private void initListener() {
        mBinding.myCbConfirm.onConfirmListener(view -> {
            if (check()){
                // 组装数据
                CreditPersonModel model = new CreditPersonModel();
                model.setUserName(mBinding.myElName.getText());
                model.setMobile(mBinding.myElPhone.getText());
                model.setLoanRole(mBinding.mySlRole.getDataKey());
                model.setRelation(mBinding.mySlRelation.getDataKey());
                model.setIdNo(mBinding.myElId.getText());
                model.setIdNoFront(mBinding.myIlIdCard.getFlImgUrl());
                model.setIdNoReverse(mBinding.myIlIdCard.getFlImgRightUrl());
                model.setAuthPdf(mBinding.myIlCredit.getFlImgUrl());
                model.setInterviewPic(mBinding.myIlInterview.getFlImgUrl());

                // 发送数据
                EventBus.getDefault().post(model);
                finish();
            }

        });
    }

    private boolean check(){
        // 姓名
        if (TextUtils.isEmpty(mBinding.myElName.getText())){
            return false;
        }
        // 手机号
        if (TextUtils.isEmpty(mBinding.myElPhone.getText())){
            return false;
        }
        // 贷款角色
        if (TextUtils.isEmpty(mBinding.mySlRole.getDataKey())){
            return false;
        }
        // 与借款人关系
        if (TextUtils.isEmpty(mBinding.mySlRelation.getDataKey())){
            return false;
        }
        // 身份证号
        if (TextUtils.isEmpty(mBinding.myElId.getText())){
            return false;
        }
        // 身份证正面
        if (TextUtils.isEmpty(mBinding.myIlIdCard.getFlImgUrl())){
            return false;
        }
        // 身份证反面
        if (TextUtils.isEmpty(mBinding.myIlIdCard.getFlImgRightUrl())){
            return false;
        }
        // 征信查询授权书
        if (TextUtils.isEmpty(mBinding.myIlCredit.getFlImgUrl())){
            return false;
        }
        // 面签照片
        if (TextUtils.isEmpty(mBinding.myIlInterview.getFlImgUrl())){
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();
        new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {

                LogUtil.E("requestCode="+requestCode);

                if (requestCode == mBinding.myIlIdCard.getRequestCode()){
                    mBinding.myIlIdCard.setFlImgImageView(key);
                }

                if (requestCode == mBinding.myIlIdCard.getRightRequestCode()){
                    mBinding.myIlIdCard.setFlImgRightImageView(key);
                }

                if (requestCode == mBinding.myIlCredit.getRequestCode()){
                    mBinding.myIlCredit.setFlImgImageView(key);
                }

                if (requestCode == mBinding.myIlInterview.getRequestCode()){
                    mBinding.myIlInterview.setFlImgImageView(key);
                }

                disMissLoading();

            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }
}
