package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CreditUserAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityCreditDetailBinding;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.CreditUserModel;
import com.cdkj.wzcd.util.BankHelper;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.RequestUtil;

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

public class CreditDetailActivity extends AbsBaseLoadActivity {

    private ActivityCreditDetailBinding mBinding;

    private String creditCode;
    private CreditModel mData;


    private CreditUserAdapter mAdapter;
    private List<CreditUserModel> mList = new ArrayList<>();

    /**
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditDetailActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_detail, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("征信详情");

        if (getIntent() != null) {
            creditCode = getIntent().getStringExtra(DATA_SIGN);

            initAdapter();

        }

    }

    public void initAdapter() {
        mAdapter = new CreditUserAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CreditUserModel model = mAdapter.getItem(position);
            CreditUserActivity.open(this, model, position, false);
        });

        mBinding.rvZxr.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvZxr.setAdapter(mAdapter);

        getCredit();

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
            mBinding.mySlBank.setTextByRequest(data.getBankName() + data.getSubbranch());
        });

        DataDictionaryHelper.getValueOnTheKeyRequest(this, DataDictionaryHelper.budget_orde_biz_typer, mData.getBizType(), data -> {
            mBinding.mySlWay.setTextByRequest(data.getDvalue());
        });

        mBinding.myElAmount.setTextByRequest(RequestUtil.formatAmountDiv(mData.getLoanAmount()));

        if (TextUtils.equals(mData.getBizType(), "1")) { //二手车
            mBinding.myFlReport.build(this, 1);
            //二手车也隐藏  oss端没有了  不知道是去掉 了 还是 咋滴  只显示
            // 新车则隐藏证件
//            mBinding.myIlDocuments.setVisibility(View.VISIBLE);
            mBinding.myFlReport.setVisibility(View.VISIBLE);

//            mBinding.myIlDocuments.setFlImgByRequest(mData.getXszFront());
//            mBinding.myIlDocuments.setFlImgRightByRequest(mData.getXszReverse());
            mBinding.myFlReport.setListDataByRequest(mData.getSecondCarReport());
        }

        mList.addAll(mData.getCreditUserList());
        mAdapter.notifyDataSetChanged();

        mBinding.myElNote.setTextByRequest(mData.getNote());

    }

}
