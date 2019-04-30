package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityCreditPersonAddBinding;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.model.CreditUserReplaceModel;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.baselibrary.utils.StringUtils.isIDCard;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditUserActivity extends AbsBaseLoadActivity {

    private ActivityCreditPersonAddBinding mBinding;

    private CreditUserModel model;

    private boolean isCanEdit;
    // List的position
    private int position;

    private List<DataDictionary> role;
    private List<DataDictionary> relation;

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditUserActivity.class);
        context.startActivity(intent);
    }

    /**
     * @param context 上下文
     * @param model   征信人Model
     *                //     * @param isCanEdit 当前页面是否可编辑,true:可编辑,false:不可编辑
     */
    public static void open(Context context, CreditUserModel model, int position, boolean isCanEdit) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditUserActivity.class);
        intent.putExtra(DATA_SIGN, model);
        intent.putExtra("position", position);
        intent.putExtra("isCanEdit", isCanEdit);
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

        role = BizTypeHelper.getParentList("credit_user_loan_role");
        relation = BizTypeHelper.getParentList("credit_user_relation");
        initCustomView();
        initListener();
        if (getIntent() != null && getIntent().getExtras() != null) {
            model = (CreditUserModel) getIntent().getSerializableExtra(DATA_SIGN);
            position = getIntent().getIntExtra("position", 0);
            isCanEdit = getIntent().getBooleanExtra("isCanEdit", false);
            setView();
        }

    }

    private void setView() {
        if (isCanEdit) {
            mBinding.myElName.setText(model.getUserName());
            mBinding.myElPhone.setText(model.getMobile());

            mBinding.mySlRole.setTextAndKey(model.getLoanRole(), DataDictionaryHelper.getValueOnTheKey(model.getLoanRole(), role));
            mBinding.mySlRelation.setTextAndKey(model.getRelation(), DataDictionaryHelper.getValueOnTheKey(model.getRelation(), relation));

            mBinding.myElId.setText(model.getIdNo());
            mBinding.myIlIdCard.setFlImg(model.getIdFront());
            mBinding.myIlIdCard.setFlImgRight(model.getIdReverse());
            mBinding.myMlCredit.setListData(model.getAuthPdf());
            mBinding.myMlInterview.setListData(model.getInterviewPic());

        } else {
            mBinding.myElName.setTextByRequest(model.getUserName());
            mBinding.myElPhone.setTextByRequest(model.getMobile());

            mBinding.mySlRole.setTextByRequest(DataDictionaryHelper.getValueOnTheKey(model.getLoanRole(), role));
            mBinding.mySlRelation.setTextByRequest(DataDictionaryHelper.getValueOnTheKey(model.getRelation(), relation));

            mBinding.myElId.setTextByRequest(model.getIdNo());
            mBinding.myIlIdCard.setFlImgByRequest(model.getIdFront());
            mBinding.myIlIdCard.setFlImgRightByRequest(model.getIdReverse());
            mBinding.myMlCredit.setListDataByRequest(model.getAuthPdf());
            mBinding.myMlInterview.setListDataByRequest(model.getInterviewPic());

//            mBinding.myElCreditCardOccupation.setVisibility(View.VISIBLE);
//            mBinding.myMlBankCreditResultPdf.setVisibility(View.VISIBLE);
            mBinding.myElBankCreditResultRemark.setVisibility(View.VISIBLE);

//            mBinding.myElCreditCardOccupation.setTextByRequest(model.getCreditCardOccupation() == null ? "暂无" : model.getCreditCardOccupation());
//            mBinding.myMlBankCreditResultPdf.setListDataByRequest(model.getBankCreditResultPdf());
            mBinding.myElBankCreditResultRemark.setTextByRequest(model.getBankCreditResultRemark());

            mBinding.myCbConfirm.setVisibility(View.GONE);
        }

    }

    private void initCustomView() {

        mBinding.mySlRole.setData(role, null);
        mBinding.mySlRelation.setData(relation, null);

        mBinding.myIlIdCard.setActivity(this, 1, 2);
        mBinding.myMlCredit.build(this, 3);
        mBinding.myMlInterview.build(this, 4);

//        mBinding.myMlBankCreditResultPdf.build(this, 5);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                // 组装数据
                CreditUserModel model = new CreditUserModel();
                model.setUserName(mBinding.myElName.getText());
                model.setMobile(mBinding.myElPhone.getText());
                model.setLoanRole(mBinding.mySlRole.getDataKey());
                model.setRelation(mBinding.mySlRelation.getDataKey());
                model.setIdNo(mBinding.myElId.getText());
                model.setIdFront(mBinding.myIlIdCard.getFlImgUrl());
                model.setIdReverse(mBinding.myIlIdCard.getFlImgRightUrl());
                model.setAuthPdf(mBinding.myMlCredit.getListData());
                model.setInterviewPic(mBinding.myMlInterview.getListData());

                // 发送数据
                if (getIntent() != null && getIntent().getExtras() != null) {
                    // 替换
                    EventBus.getDefault().post(new CreditUserReplaceModel().setLocation(position).setCreditUserModel(model));
                    finish();
                } else {
                    // 新增
                    EventBus.getDefault().post(model);
                    finish();
                }

            }

        });
    }

    private boolean check() {

        // 姓名
        if (TextUtils.isEmpty(mBinding.myElName.check())) {
            return false;
        }
        // 手机号
        if (TextUtils.isEmpty(mBinding.myElPhone.check())) {
            return false;
        }
        // 贷款角色
        if (mBinding.mySlRole.check()) {
            return false;
        }
        // 与借款人关系
        if (mBinding.mySlRelation.check()) {
            return false;
        }
        // 身份证号
        if (TextUtils.isEmpty(mBinding.myElId.check())) {
            return false;
        }

        if (!isIDCard(mBinding.myElId.getText())) {
            ToastUtil.show(this, "请输入合法身份证号");
            return false;
        }

        // 身份证正面
        if (TextUtils.isEmpty(mBinding.myIlIdCard.check())) {
            return false;
        }
        // 身份证反面
        if (TextUtils.isEmpty(mBinding.myIlIdCard.check())) {
            return false;
        }
        // 征信查询授权书
        if (mBinding.myMlCredit.check()) {
            return false;
        }
        // 面签照片
        if (mBinding.myMlInterview.check()) {
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

                LogUtil.E("requestCode=" + requestCode);

                if (requestCode == mBinding.myIlIdCard.getRequestCode()) {
                    mBinding.myIlIdCard.setFlImg(key);
                }

                if (requestCode == mBinding.myIlIdCard.getRightRequestCode()) {
                    mBinding.myIlIdCard.setFlImgRight(key);
                }

                if (requestCode == mBinding.myMlCredit.getRequestCode()) {
                    mBinding.myMlCredit.addList(key);
                }

                if (requestCode == mBinding.myMlInterview.getRequestCode()) {
                    mBinding.myMlInterview.addList(key);
                }

//                if (requestCode == mBinding.myMlBankCreditResultPdf.getRequestCode()) {
//                    mBinding.myMlBankCreditResultPdf.addList(key);
//                }

                disMissLoading();

            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }
}
