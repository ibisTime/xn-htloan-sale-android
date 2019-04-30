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
import com.cdkj.wzcd.databinding.ActivityZxshdetailsBinding;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 征信录入选择界面
 */
public class ZXSHDetails extends AbsBaseLoadActivity {

    private ActivityZxshdetailsBinding mBinding;
    private ZXDetialsBean.ListBean mData;
    private String creditCode;


    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ZXSHDetails.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zxshdetails, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("审核");
        if (getIntent() != null) {
            creditCode = getIntent().getStringExtra(CdRouteHelper.DATA_SIGN);
        }

        getData();
        initListener();
    }

    private void initListener() {
        //1通过 0不通过
        mBinding.myCbConfirm.setOnConfirmListener(view -> {

            submit("1");

        });
        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            submit("0");
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

        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        CreditUserAdapter2 adapter = new CreditUserAdapter2(mData.getCreditUserList(), false);
        mBinding.rv.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter12, view, position) -> {
            ZXDetialsBean.ListBean.CreditUserListBean item = (ZXDetialsBean.ListBean.CreditUserListBean) adapter12.getItem(position);
            ZXLRInputActivity.open(this, item, null, true);
        });
    }

    /**
     * 提交录入
     */
    private void submit(String approveResult) {
        Map<String, Object> map = new HashMap<>();
        map.put("approveNote", mBinding.myELMsg.getText());
        map.put("approveResult", approveResult);
        map.put("code", mData.getCode());
        map.put("creditUserList", mData.getCreditUserList());
        map.put("operator", SPUtilHelper.getUserId());
        Call<BaseResponseModel<SuccessBean>> success = RetrofitUtils.createApi(MyApiServer.class).success("632113", StringUtils.getJsonToString(map));
        showLoadingDialog();
        success.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {
                UITipDialog.showSuccess(ZXSHDetails.this, "成功", dialogInterface -> finish());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
