package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityAuditResultInputBinding;
import com.cdkj.wzcd.model.CreditUserModel;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/8.
 */

public class AuditResultInputActivity extends AbsBaseLoadActivity {

    private ActivityAuditResultInputBinding mBinding;

    private CreditUserModel model;
    // 页面是否可编辑
    private boolean isCanEdit;
    // 当前节点
    private String nodeCode;

    /**
     *
     * @param context 上下文
     * @param model 征信人Model
     * @param isCanEdit 当前页面是否可编辑,true:可编辑,false:不可编辑
     */
    public static void open(Context context, CreditUserModel model, String nodeCode, boolean isCanEdit) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditUserActivity.class);
        intent.putExtra(DATA_SIGN, model);
        intent.putExtra("isCanEdit", isCanEdit);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_audit_result_input, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("添加征信人");

        initListener();

        if (getIntent() == null)
            return;
        model = (CreditUserModel) getIntent().getSerializableExtra(DATA_SIGN);
        isCanEdit = getIntent().getBooleanExtra("isCanEdit",false);

        setView();
    }

    private void setView() {
        if (isCanEdit){

            mBinding.myElName.setText(model.getUserName());
            mBinding.myElPhone.setText(model.getMobile());
            mBinding.mySlRole.setText(model.getLoanRole());
            mBinding.mySlRelation.setText(model.getRelation());
            mBinding.myElId.setText(model.getIdNo());
            mBinding.myIlIdCard.setFlImg(model.getIdNoFront());
            mBinding.myIlIdCard.setFlImgRight(model.getIdNoReverse());
            mBinding.myIlCredit.setFlImg(model.getAuthPdf());
            mBinding.myIlInterview.setFlImg(model.getInterviewPic());

        }else {
            mBinding.myElName.setTextByRequest(model.getUserName());
            mBinding.myElPhone.setTextByRequest(model.getMobile());
            mBinding.mySlRole.setTextByRequest(model.getLoanRole());
            mBinding.mySlRelation.setTextByRequest(model.getRelation());
            mBinding.myElId.setTextByRequest(model.getIdNo());
            mBinding.myIlIdCard.setFlImgByRequest(model.getIdNoFront());
            mBinding.myIlIdCard.setFlImgRightByRequest(model.getIdNoReverse());
            mBinding.myIlCredit.setFlImgByRequest(model.getAuthPdf());
            mBinding.myIlInterview.setFlImgByRequest(model.getInterviewPic());
        }


        // 是否是 录入征信结果节点
        if (TextUtils.equals(nodeCode, "001_02")){
            mBinding.myIlBankCreditResultPdf.setActivity(this, 5, 0);
        }else {
            mBinding.myIlBankCreditResultPdf.setFlImgByRequest(model.getBankCreditResultPdf());
            mBinding.myElBankCreditResultRemark.setTextByRequest(model.getBankCreditResultRemark());

            mBinding.myCbConfirm.setVisibility(View.GONE);
        }


    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){

            }

        });
    }

    private boolean check(){
        // 征信报告
        if (TextUtils.isEmpty(mBinding.myIlBankCreditResultPdf.check())){
            return false;
        }

        // 征信结果说明
        if (TextUtils.isEmpty(mBinding.myElBankCreditResultRemark.check())){
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


                if (requestCode == mBinding.myIlBankCreditResultPdf.getRequestCode()){
                    mBinding.myIlBankCreditResultPdf.setFlImg(key);
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
