package com.cdkj.wzcd.module.business.credit.audit;

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
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CreditUserAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityCreditAuditBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.CreditResult;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.model.CreditUserReplaceModel;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.RequestUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 发起征信
 * Created by cdkj on 2018/5/29.
 */

public class AuditCreditActivity extends AbsBaseLoadActivity {

    private ActivityCreditAuditBinding mBinding;

    private String creditCode;
    private CreditModel mData;


    // 角色
    private List<DataDictionary> mRole = new ArrayList<>();
    // 关系
    private List<DataDictionary> mRelation = new ArrayList<>();

    private CreditUserAdapter mAdapter;
    private List<CreditUserModel> mList = new ArrayList<>();

    /**
     * @param context
     */
    public static void open(Context context,String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AuditCreditActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_audit, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("录入征信结果");

        init();

        initListener();
        initListAdapter();

    }

    private void init() {

        if (getIntent() != null){
            creditCode = getIntent().getStringExtra(DATA_SIGN);
        }

    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){
                inputResultRequest(false);
            }
        });

        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            showDoubleWarnListen("您确定要退回此条征信调查单吗?",view1 -> {
                inputResultRequest(true);
            });
        });
    }

    private boolean check(){
        for (CreditUserModel model : mList){

            if (TextUtils.isEmpty(model.getCreditCardOccupation())){
                ToastUtil.show(this, "请上传"+DataDictionaryHelper.getValueOnTheKey(model.getLoanRole(), mRole)+"信用卡使用占比");
                return false;
            }

            if (TextUtils.isEmpty(model.getBankCreditResultPdf())){
                ToastUtil.show(this, "请上传"+DataDictionaryHelper.getValueOnTheKey(model.getLoanRole(), mRole)+"征信报告");
                return false;
            }

            if (TextUtils.isEmpty(model.getBankCreditResultRemark())){
                ToastUtil.show(this, "请上传"+DataDictionaryHelper.getValueOnTheKey(model.getLoanRole(), mRole)+"征信结果说明");
                return false;
            }

        }

        return true;
    }


    public void initListAdapter() {
        mAdapter = new CreditUserAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CreditUserModel model = mAdapter.getItem(position);

            AuditUserActivity.open(this, model, position, mRole, mRelation);

        });

        mBinding.rvZxr.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvZxr.setAdapter(mAdapter);

        getCredit();


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
                    mBinding.myIlDocuments.setFlImg(key);
                }

                if (requestCode == mBinding.myIlDocuments.getRightRequestCode()){
                    mBinding.myIlDocuments.setFlImgRight(key);
                }

                if (requestCode == mBinding.myIlReport.getRequestCode()){
                    mBinding.myIlReport.setFlImg(key);
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

    @Subscribe
    public void doReplaceCreditPerson(CreditUserReplaceModel model){

        mList.set(model.getLocation(), model.getCreditUserModel());

        mAdapter.notifyDataSetChanged();

    }


    /**
     * 录入征信结果
     */
    private void inputResultRequest(boolean isReturn){
        Map<String, Object> map = new HashMap<>();

        List<CreditResult> creditResult = new ArrayList<>();

        for (CreditUserModel model : mList){
            creditResult.add(new CreditResult().setCreditUserCode(model.getCode())
                    .setCreditCardOccupation(model.getCreditCardOccupation())
                    .setBankCreditResultPdf(model.getBankCreditResultPdf())
                    .setBankCreditResultRemark(model.getBankCreditResultRemark()));
        }

        map.put("creditCode", creditCode);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("dealType", isReturn ? "0" : "1"); // 是否退回

        if (!isReturn){
            map.put("creditResult", creditResult);
        }

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632111", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(AuditCreditActivity.this, "录入成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(AuditCreditActivity.this, errorMessage);
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
            mBinding.mySlBank.setTextByRequest(data.getBankName());
        });

        DataDictionaryHelper.getValueOnTheKeyRequest(this, DataDictionaryHelper.budget_orde_biz_typer, mData.getBizType(), data -> {
            mBinding.mySlWay.setTextByRequest(data.getDvalue());
        });

        mBinding.myElAmount.setTextByRequest(RequestUtil.formatAmountDiv(mData.getLoanAmount()));

        if (TextUtils.equals(mData.getBizType(), "1")){ //二手车
            // 新车则隐藏证件
            mBinding.myIlDocuments.setVisibility(View.VISIBLE);
            mBinding.myIlReport.setVisibility( View.VISIBLE);

            mBinding.myIlDocuments.setFlImgByRequest(mData.getXszFront());
            mBinding.myIlDocuments.setFlImgRightByRequest(mData.getXszReverse());

            mBinding.myIlReport.setFlImgByRequest(mData.getSecondCarReport());
        }

        mList.addAll(mData.getCreditUserList());
        mAdapter.notifyDataSetChanged();

        mBinding.myElNote.setTextByRequest(mData.getNote());

    }

}
