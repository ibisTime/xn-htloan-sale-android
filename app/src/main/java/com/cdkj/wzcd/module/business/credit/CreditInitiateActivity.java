package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
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
import com.cdkj.wzcd.databinding.ActivityCreditInitiateBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.model.CreditUserReplaceModel;
import com.cdkj.wzcd.model.ExchangeBankModel;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.RequestUtil;
import com.cdkj.wzcd.view.interfaces.MySelectInterface;

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

    private ActivityCreditInitiateBinding mBinding;

    private String creditCode;
    private CreditModel mData;

    // 银行
    private List<DataDictionary> mBank;


    private CreditUserAdapter mAdapter;
    private List<CreditUserModel> mList = new ArrayList<>();

    /**
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditInitiateActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_initiate, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("发起征信");

        init();
        initListener();
        initListAdapter();
    }

    private void init() {
        mBank = new ArrayList<>();

        if (getIntent() != null) {
            creditCode = getIntent().getStringExtra(DATA_SIGN);
        }
    }

    private void initListener() {
        mBinding.llAdd.setOnClickListener(view -> {
            CreditUserActivity.open(this);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                creditAddRequest("1");
            }
        });

        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            if (check()) {
                creditAddRequest("0");
            }
        });
    }

    private boolean check() {
        // 银行
        if (mBinding.mySlBank.check()) {
            return false;
        }
        // 业务种类
        if (mBinding.mySlWay.check()) {
            return false;
        }
//        // 贷款金额
//        if (TextUtils.isEmpty(mBinding.myElAmount.check())){
//            return false;
//        }
//        if (mBinding.myIlReport.getVisibility() == View.VISIBLE){
        // 二手车评估报告
//            if (TextUtils.isEmpty(mBinding.myIlReport.check())){
//                LogUtil.E("二手车评估报告");
//                return false;
//            }
//            // 行驶证正面
//            if (TextUtils.isEmpty(mBinding.myIlDocuments.check())){
//                LogUtil.E("行驶证正面");
//                return false;
//            }
//            // 行驶证反面
//            if (TextUtils.isEmpty(mBinding.myIlDocuments.check())){
//                LogUtil.E("行驶证反面");
//                return false;
//            }
//        }

        // 征信人
        if (mList.size() == 0) {
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

        Call call = RetrofitUtils.createApi(MyApiServer.class).getExchangeBank("632037", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<ExchangeBankModel>(this) {

            @Override
            protected void onSuccess(List<ExchangeBankModel> data, String SucMessage) {

                if (data == null)
                    return;

                for (ExchangeBankModel model : data) {
                    mBank.add(new DataDictionary().setDkey(model.getCode()).setDvalue(model.getBankName() + model.getSubbranch()));
                }

                mBinding.mySlBank.setData(mBank, null);

                initCustomView();
                getCredit();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    private void initCustomView() {

        mBinding.mySlWay.setData(BizTypeHelper.getParentList(BizTypeHelper.budget_orde_biz_typer), new MySelectInterface() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBinding.myFlReport.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
            }
        });

//        mBinding.mySlWay.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.budget_orde_biz_typer, (dialog, which) -> {
//            // 新车则隐藏证件
//            mBinding.myFlReport.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
//        });

        mBinding.myFlReport.build(this, 3);
    }

    public void initListAdapter() {
        mAdapter = new CreditUserAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CreditUserModel model = mAdapter.getItem(position);

            CreditUserActivity.open(this, model, position, true);

        });

        mBinding.rvZxr.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvZxr.setAdapter(mAdapter);

        getBank();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();

        if (requestCode == mBinding.myFlReport.getRequestCode()) {

            showLoadingDialog();
            new QiNiuHelper(this).uploadSingleFile(new QiNiuHelper.QiNiuFileCallBack() {
                @Override
                public void onSuccess(String key) {
                    mBinding.myFlReport.addList(key);
                    disMissLoading();
                }

                @Override
                public void onFal(String info) {
                    UITipDialog.showFail(CreditInitiateActivity.this, info);
                    disMissLoading();
                }

                @Override
                public void progress(String key, double percent) {
                    LogUtil.E("进度:" + percent);
                }

                @Override
                public void complete() {
                    disMissLoading();
                }
            }, path);

        }

//        new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
//            @Override
//            public void onSuccess(String key) {
//
//                if (requestCode == mBinding.myIlReport.getRequestCode()) {
//                    mBinding.myIlReport.setFlImg(key);
//                }
//
//                disMissLoading();
//            }
//
//            @Override
//            public void onFal(String info) {
//                disMissLoading();
//            }
//        }, path);
    }

    @Subscribe
    public void doAddCreditPerson(CreditUserModel model) {

        mList.add(model);

        mAdapter.notifyDataSetChanged();

    }

    @Subscribe
    public void doReplaceCreditPerson(CreditUserReplaceModel model) {

        mList.set(model.getLocation(), model.getCreditUserModel());

        mAdapter.notifyDataSetChanged();

    }

    /**
     * 发起征信
     */
    private void creditAddRequest(String buttonCode) {
        Map<String, Object> map = new HashMap<>();

        if (!TextUtils.isEmpty(creditCode)) {
//            map.put("creditCode", creditCode);
            map.put("bizCode", creditCode);
        }

        map.put("bizType", mBinding.mySlWay.getDataKey());
        map.put("buttonCode", buttonCode);
        map.put("creditUserList", mList);
        map.put("loanAmount", TextUtils.isEmpty(mBinding.myElAmount.getText()) ? "" : formatAmountMul(mBinding.myElAmount.getText()));
        map.put("loanBankCode", mBinding.mySlBank.getDataKey());
        map.put("operator", SPUtilHelper.getUserId());
        map.put("secondCarReport", mBinding.myFlReport.getListData());
        map.put("note", mBinding.myElNote.getText());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest(TextUtils.isEmpty(creditCode) ? "632110" : "632112", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(CreditInitiateActivity.this, TextUtils.equals(buttonCode, "1") ? "发起成功" : "保存成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
//                super.onReqFailure(errorCode, errorMessage);
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

                mData = data;

                setView();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void setView() {
        new BankHelper(this).getValueOnTheKey(mData.getLoanBankCode(), null, data -> {
            mBinding.mySlBank.setTextAndKey(mData.getLoanBankCode(), data.getBankName());
        });

        DataDictionaryHelper.getValueOnTheKeyRequest(this, DataDictionaryHelper.budget_orde_biz_typer, mData.getBizType(), data -> {
            mBinding.mySlWay.setTextAndKey(mData.getBizType(), data.getDvalue());
        });

        mBinding.myElAmount.setText(RequestUtil.formatAmountDiv(mData.getLoanAmount()));

        if (TextUtils.equals(mData.getBizType(), "1")) { //二手车
            // 新车则隐藏证件
            mBinding.myFlReport.setVisibility(View.VISIBLE);
            mBinding.myFlReport.setListData(mData.getSecondCarReport());
        }

        mBaseBinding.titleView.setMidTitle("修改征信信息");
        mList.addAll(mData.getCreditUserList());
        mAdapter.notifyDataSetChanged();

    }

}
