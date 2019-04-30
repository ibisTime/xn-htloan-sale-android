package com.cdkj.wzcd.module.home.front;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CreditUserAdapter2;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityZxlrdetailsBinding;
import com.cdkj.wzcd.model.CreditUserUpModel;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 征信录入选择界面
 */
public class ZXLRDetails extends AbsBaseLoadActivity {

    private ActivityZxlrdetailsBinding mBinding;
    private ZXDetialsBean.ListBean mData;
    private String creditCode;
    private ArrayList<CreditUserUpModel> creditList;

    private int currentPosition;

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ZXLRDetails.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
//        CreditUserAdapter
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zxlrdetails, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("录入");
        if (getIntent() != null) {
            creditCode = getIntent().getStringExtra(CdRouteHelper.DATA_SIGN);
        }

        getData();
        initListener();
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                submit();
            }
        });
    }

    /**
     * 获取征信详情
     */
    private void getData() {

        if (TextUtils.isEmpty(creditCode))
            return;

        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("code", creditCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCredit2("632117", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ZXDetialsBean.ListBean>(this) {

            @Override
            protected void onSuccess(ZXDetialsBean.ListBean data, String SucMessage) {

                if (data == null)
                    return;
                mData = data;
                setViewData();
                initRecyclerView();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void setViewData() {
        ZXDetialsBean.ListBean.CreditUserBean creditUser = mData.getCreditUser();

        mBinding.myNLName.setText(creditUser.getUserName());
        mBinding.myNLBank.setText(mData.getLoanBankName());
        String typeStr = BizTypeHelper.getNameOnTheKey(BizTypeHelper.budget_orde_biz_typer, mData.getBizType());
        mBinding.myNLType.setText(typeStr);
        mBinding.myNLMoney.setText(StringUtils.formatNum(mData.getLoanAmount()));
        mBinding.myNLYwgs.setText(mData.getCompanyName() + "-" + mData.getTeamName() + "-" + mData.getSaleUserName());
        mBinding.myNLZpgs.setText(mData.getCompanyName() + "-" + mData.getTeamName() + "-" + mData.getSaleUserName());
        mBinding.myNLCurrentType.setText(NodeHelper.getNameOnTheCode(mData.getCurNodeCode()));

    }

    private void initRecyclerView() {
        //构造入参数据
        creditList = new ArrayList<>();
        for (int i = 0; i < mData.getCreditUserList().size(); i++) {
            creditList.add(new CreditUserUpModel());
        }

        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        CreditUserAdapter2 adapter = new CreditUserAdapter2(mData.getCreditUserList(), true);
        mBinding.rv.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_input:
                    currentPosition = position;
                    ZXDetialsBean.ListBean.CreditUserListBean item = (ZXDetialsBean.ListBean.CreditUserListBean) adapter1.getItem(position);
                    ZXLRInputActivity.open(ZXLRDetails.this, item, creditList.get(position), false);
                    break;
            }
        });

        adapter.setOnItemClickListener((adapter12, view, position) -> {
            ZXDetialsBean.ListBean.CreditUserListBean item = (ZXDetialsBean.ListBean.CreditUserListBean) adapter12.getItem(position);
            ZXLRInputActivity.open(ZXLRDetails.this, item, null, true);

        });
    }

    /**
     * 提交录入
     */
    private void submit() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", SPUtilHelper.getUserToken());
        map.put("bizCode", mData.getCode());
        map.put("creditList", creditList);
        map.put("operator", SPUtilHelper.getUserId());
        Call<BaseResponseModel<SuccessBean>> success = RetrofitUtils.createApi(MyApiServer.class).success("632111", StringUtils.getJsonToString(map));
        showLoadingDialog();
        success.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {
                UITipDialog.showSuccess(ZXLRDetails.this, "录入成功", dialogInterface -> finish());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 检查入参
     */
    private boolean check() {
        String errorStr = "";
        boolean isYes = true;
        for (int i = 0; i < creditList.size(); i++) {
            CreditUserUpModel creditUserUpModel = creditList.get(i);
            errorStr = "请将" + mData.getCreditUserList().get(i).getUserName() + "信息补完整";
            if (TextUtils.isEmpty(creditUserUpModel.getBankCreditReport())) {
                isYes = false;
                break;
            }
            if (TextUtils.isEmpty(creditUserUpModel.getBankResult())) {
                isYes = false;
                break;
            }
            if (TextUtils.isEmpty(creditUserUpModel.getCreditNote())) {
                isYes = false;
                break;
            }
            if (TextUtils.isEmpty(creditUserUpModel.getCreditUserCode())) {
                isYes = false;
                break;
            }
            if (TextUtils.isEmpty(creditUserUpModel.getDataCreditReport())) {
                isYes = false;
                break;
            }
        }
        if (!isYes) {
            UITipDialog.showFail(this, errorStr);
        }
        return isYes;
    }


    @Subscribe
    public void setUpData(CreditUserUpModel mode) {
        creditList.set(currentPosition, mode);
    }
}
