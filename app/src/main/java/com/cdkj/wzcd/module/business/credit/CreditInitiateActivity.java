package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.BankModel;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CreditUserAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityZxLaunchBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.cdkj.wzcd.view.MySelectLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.wzcd.util.RequestUtil.formatAmountMul;

/**
 * 发起征信
 * Created by cdkj on 2018/5/29.
 */

public class CreditInitiateActivity extends AbsBaseLoadActivity {

    private ActivityZxLaunchBinding mBinding;

    private String creditCode;

    // 银行
    private List<DataDictionary> mMapBank;

    private CreditUserAdapter mAdapter;
    private List<CreditUserModel> mList = new ArrayList<>();

    /**
     * @param context
     */
    public static void open(Context context,String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditInitiateActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zx_launch, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("发起征信");

        initListener();
        initListAdapter();

        init();

        getBank();
        initCustomView();

    }

    private void init() {
        mMapBank = new ArrayList<>();

        if (getIntent() != null){
            creditCode = getIntent().getStringExtra(DATA_SIGN);
        }

    }

    private void initListener() {
        mBinding.llAdd.setOnClickListener(view -> {
            CreditUserAddActivity.open(this);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){
                creditAddRequest();
            }
        });
    }

    private boolean check(){
        // 银行
        if (TextUtils.isEmpty(mBinding.mySlBank.getDataKey())){
            return false;
        }
        // 购车途径
        if (TextUtils.isEmpty(mBinding.mySlWay.getDataKey())){
            return false;
        }
        // 贷款金额
        if (TextUtils.isEmpty(mBinding.myElAmount.getText())){
            return false;
        }
        if (mBinding.myIlDocuments.getVisibility() == View.VISIBLE && mBinding.myIlReport.getVisibility() == View.VISIBLE){
            // 二手车评估报告
            if (TextUtils.isEmpty(mBinding.myIlReport.getFlImgUrl())){
                LogUtil.E("二手车评估报告");
                return false;
            }
            // 行驶证正面
            if (TextUtils.isEmpty(mBinding.myIlDocuments.getFlImgUrl())){
                LogUtil.E("行驶证正面");
                return false;
            }
            // 行驶证反面
            if (TextUtils.isEmpty(mBinding.myIlDocuments.getFlImgRightUrl())){
                LogUtil.E("行驶证反面");
                return false;
            }
        }

        // 征信人
        if (mList.size() == 0){
            ToastUtil.show(this, "请添加征信人");
            return false;
        }

        return true;
    }

    /**
     * 获取银行卡渠道
     */
    private void getBank() {
        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("bankCode", "");
        map.put("bankName", "");
        map.put("channelType", "");
        map.put("status", "");
        map.put("paybank", "");

        Call call = RetrofitUtils.getBaseAPiService().getBackModel("802116", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BankModel>(this) {

            @Override
            protected void onSuccess(List<BankModel> data, String SucMessage) {

                if (data == null)
                    return;

                for (BankModel model : data){
                    mMapBank.add(new DataDictionary().setDkey(model.getBankCode()).setDvalue(model.getBankName()));
                }

                mBinding.mySlBank.setData(mMapBank, null);

                getCredit();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    private void initCustomView() {

        mBinding.mySlWay.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.budget_orde_biz_typer, (dialog, which) -> {
            // 新车则隐藏证件
            mBinding.myIlDocuments.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
            mBinding.myIlReport.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
        });

        mBinding.myIlDocuments.setActivity(this,1,2);
        mBinding.myIlReport.setActivity(this,3,0);
    }

    public void initListAdapter() {
        mAdapter = new CreditUserAdapter(mList);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CreditUserModel model = mAdapter.getItem(position);
        });

        mBinding.rvZxr.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvZxr.setAdapter(mAdapter);
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

                if (requestCode == mBinding.myIlDocuments.getRequestCode()){
                    mBinding.myIlDocuments.setFlImgImageView(key);
                }

                if (requestCode == mBinding.myIlDocuments.getRightRequestCode()){
                    mBinding.myIlDocuments.setFlImgRightImageView(key);
                }

                if (requestCode == mBinding.myIlReport.getRequestCode()){
                    mBinding.myIlReport.setFlImgImageView(key);
                }

                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }

    @Subscribe
    public void doAddCreditPerson(CreditUserModel model){

        mList.add(model);

        mAdapter.notifyDataSetChanged();

    }

    private void creditAddRequest(){
        Map<String, Object> map = new HashMap<>();

        map.put("bizType", mBinding.mySlWay.getDataKey());
        map.put("buttonCode", "1");
        map.put("creditUserList", mList);
        map.put("loanAmount", formatAmountMul(mBinding.myElAmount.getText()));
        map.put("loanBankCode", mBinding.mySlBank.getDataKey());
        map.put("operator", SPUtilHelper.getUserId());
        map.put("secondCarReport", mBinding.myIlReport.getFlImgUrl());
        map.put("xszFront", mBinding.myIlDocuments.getFlImgUrl());
        map.put("xszReverse", mBinding.myIlDocuments.getFlImgRightUrl());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632110", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(CreditInitiateActivity.this, "发起成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(CreditInitiateActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 获取征信详情
     */
    private void getCredit() {
        if (TextUtils.isEmpty(creditCode))
            return;

        mBinding.myCbConfirm.setText("修改");

        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("code", creditCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCredit("632117", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CreditModel>(this) {

            @Override
            protected void onSuccess(CreditModel data, String SucMessage) {

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

    private void setView(CreditModel model) {
        new BankHelper(this).getValueOnTheKey(model.getLoanBankCode(), null, data -> {
            mBinding.mySlBank.setContent(data.getBankName());
        });

        new DataDictionaryHelper(this).getValueOnTheKey(DataDictionaryHelper.budget_orde_biz_typer, model.getBizType(), null, data -> {
            mBinding.mySlWay.setContent(data.getDvalue());
        });

        mBinding.myElAmount.setText(RequestUtil.formatAmountDiv(model.getLoanAmount()));

        if (TextUtils.equals(model.getBizType(), "1")){ //二手车
            // 新车则隐藏证件
            mBinding.myIlDocuments.setVisibility(View.VISIBLE);
            mBinding.myIlReport.setVisibility( View.VISIBLE);

            mBinding.myIlDocuments.setFlImgImageView(model.getXszFront());
            mBinding.myIlDocuments.setFlImgRightImageView(model.getXszReverse());

            mBinding.myIlReport.setFlImgImageView(model.getSecondCarReport());
        }


        mList.addAll(model.getCreditUserList());

        mAdapter.notifyDataSetChanged();
    }

}
