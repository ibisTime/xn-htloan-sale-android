package com.cdkj.wzcd.module.business.audit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityAuditBinding;
import com.cdkj.wzcd.model.RepaymentModel;
import com.cdkj.wzcd.util.DatePickerHelper;
import com.cdkj.wzcd.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/18.
 */

public class AuditActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivityAuditBinding mBinding;

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AuditActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_audit, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("审核");
        initCustomView();
        initListener();

        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

    }

    private void initListener() {
        mBinding.myNlDateTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true,  true, false, false, false);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check())
                auditRequest("1");
        });

        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            if (check())
                auditRequest("0");
        });
    }

    private void initCustomView() {

        mBinding.myMlProve.build(this, 1);

    }

    public void getNode() {

        Map<String, String> map = new HashMap<>();

        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getRepayment("630521", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<RepaymentModel>(this) {

            @Override
            protected void onSuccess(RepaymentModel data, String SucMessage) {
                if (data == null)
                    return;

                setView(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();

            }
        });

    }

    private void setView(RepaymentModel data) {
        mBinding.myNlName.setText(data.getBudgetOrder().getApplyUserName());
        mBinding.myNlIdNumber.setText(data.getBudgetOrder().getIdNo());
        mBinding.myNlCode.setText(data.getCode());
        mBinding.myNlCompanyName.setText(data.getBudgetOrder().getCompanyName());
        mBinding.myNlAmount.setText(RequestUtil.formatAmountDivSign(data.getLoanAmount()));
        mBinding.myNlBank.setText(data.getLoanBankName());

    }

    private boolean check(){
        if (TextUtils.isEmpty(mBinding.myNlDateTime.check())){
            return false;
        }

        if (mBinding.myMlProve.check()){
            return false;
        }

        if (TextUtils.isEmpty(mBinding.myElRemark.check())){
            return false;
        }

        return true;
    }

    private void auditRequest(String approveResult){
        Map<String, Object> map = new HashMap<>();

        map.put("approveResult", approveResult);
        map.put("code", code);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("remark", mBinding.myElRemark.getText());
        map.put("settleDatetime", mBinding.myNlDateTime.getText());
        map.put("settlePdf", mBinding.myMlProve.getListData());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("630551", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(AuditActivity.this,"操作成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(AuditActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
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


                if (requestCode == mBinding.myMlProve.getRequestCode()){
                    mBinding.myMlProve.addList(key);
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
