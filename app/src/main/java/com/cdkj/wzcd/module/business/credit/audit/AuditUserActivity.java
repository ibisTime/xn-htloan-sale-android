package com.cdkj.wzcd.module.business.credit.audit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCreditPersonAddBinding;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.model.CreditUserReplaceModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.view.MySelectLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/5/30.
 */

public class AuditUserActivity extends AbsBaseLoadActivity {

    private ActivityCreditPersonAddBinding mBinding;

    private CreditUserModel model;

    // List的position
    private int position;

    private List<DataDictionary> role;
    private List<DataDictionary> relation;

    /**
     *
     * @param context 上下文
     * @param model 征信人Model
//     * @param isCanEdit 当前页面是否可编辑,true:可编辑,false:不可编辑
     */
    public static void open(Context context, CreditUserModel model, int position) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AuditUserActivity.class);
        intent.putExtra(DATA_SIGN, model);
        intent.putExtra("position", position);
//        intent.putExtra("role", (Serializable) role);
//        intent.putExtra("relation", (Serializable) relation);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_person_add, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("征信人");

        initCustomView();
        initListener();

        if (getIntent() != null && getIntent().getExtras() != null){
            model = (CreditUserModel) getIntent().getSerializableExtra(DATA_SIGN);
            position = getIntent().getIntExtra("position", 0);

            role = (List<DataDictionary>) getIntent().getSerializableExtra("role");
            relation = (List<DataDictionary>) getIntent().getSerializableExtra("relation");

            setView();
        }

    }

    private void setView() {

        mBinding.myElName.setTextByRequest(model.getUserName());
        mBinding.myElPhone.setTextByRequest(model.getMobile());
        mBinding.mySlRole.setTextByRequest(model.getLoanRole());
        mBinding.mySlRelation.setTextByRequest(model.getRelation());
        mBinding.myElId.setTextByRequest(model.getIdNo());
        mBinding.myIlIdCard.setFlImgByRequest(model.getIdNoFront());
        mBinding.myIlIdCard.setFlImgRightByRequest(model.getIdNoReverse());
        mBinding.myIlCredit.setFlImgByRequest(model.getAuthPdf());
        mBinding.myIlInterview.setFlImgByRequest(model.getInterviewPic());


        mBinding.myIlBankCreditResultPdf.setActivity(this, 5, 0);

        mBinding.myIlBankCreditResultPdf.setVisibility(View.VISIBLE);
        mBinding.myElBankCreditResultRemark.setVisibility(View.VISIBLE);

        mBinding.myIlBankCreditResultPdf.setFlImg(model.getBankCreditResultPdf());
        mBinding.myElBankCreditResultRemark.setText(model.getBankCreditResultRemark());


    }

    private void initCustomView() {

        mBinding.mySlRole.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.credit_user_loan_role,null);
        mBinding.mySlRelation.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.credit_user_relation,null);

        mBinding.myIlIdCard.setActivity(this,1,2);
        mBinding.myIlCredit.setActivity(this,3,0);
        mBinding.myIlInterview.setActivity(this,4,0);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){

                // 将新增的两条数据插入到当前的征信人数据中并返回给上个页面进行替换
                model.setBankCreditResultPdf(mBinding.myIlBankCreditResultPdf.getFlImgUrl());
                model.setBankCreditResultRemark(mBinding.myElBankCreditResultRemark.getText());

                // 发送数据
                EventBus.getDefault().post(new CreditUserReplaceModel().setLocation(position).setCreditUserModel(model));
                finish();

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
