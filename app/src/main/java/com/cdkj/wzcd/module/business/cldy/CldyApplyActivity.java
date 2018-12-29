package com.cdkj.wzcd.module.business.cldy;

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
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityCldyApplyBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.DatePickerHelper;
import com.cdkj.wzcd.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;


/**
 * 车辆抵押跳转的界面
 */
public class CldyApplyActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivityCldyApplyBinding mBinding;

    public static void open(Context context, String code) {
        if (context != null) {
            Intent intent = new Intent(context, CldyApplyActivity.class);
            intent.putExtra(DATA_SIGN, code);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_cldy_apply, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("提交");


        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

        initListener();
        initCustomView();
        initView();
    }

    private void initView() {
        mBinding.myMlPledgeUserIdCardCopy.build(this,1);

        mBinding.myMlCarBigSmj.build(this, 2);
        mBinding.myMlCarPd.build(this, 3);
        mBinding.myMlCarKey.build(this, 4);
        mBinding.myMlCarRegcerti.build(this, 5);
        mBinding.myMlCarXszSmj.build(this, 6);
        mBinding.myMlDutyPaidProveSmj.build(this, 7);
    }

    private void initListener() {
        mBinding.myNlDateTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true, true, false, false, false);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                inputRequest();
            }
        });
    }

    public void getNode() {
        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getNode("632146", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<NodeListModel>(this) {
            @Override
            protected void onSuccess(NodeListModel data, String SucMessage) {

                setView(data);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(NodeListModel data) {
//        mBinding.myNlName.setText(data.getApplyUserName());
//        mBinding.myNlCode.setText(data.getCode());
        mBinding.myNlName.setText(data.getApplyUserName());
        mBinding.myNlCode.setText(data.getCode());
        mBinding.myNlAmount.setText(RequestUtil.formatAmountDivSign(data.getLoanAmount()));
        mBinding.myNlTeamName.setText(data.getTeamName());
        mBinding.myNlLoanBankName.setText(data.getLoanBankName() + data.getRepaySubbranch());
        mBinding.myNlSaleUserName.setText(data.getSaleUserName());//信贷专员
        mBinding.myNlInsideJobName.setText(data.getInsideJobName());//内勤专员
        mBinding.myMlPledgeUserIdCardCopy.setListDataByRequest(data.getPledgeUserIdCardCopy());

        mBinding.myMlCarBigSmj.setListDataByRequest(data.getCarBigSmj());
        mBinding.myMlCarPd.setListDataByRequest(data.getCarPd());
        mBinding.myMlCarKey.setListDataByRequest(data.getCarKey());
        mBinding.myMlCarRegcerti.setListDataByRequest(data.getCarRegcerti());
        mBinding.myMlCarXszSmj.setListDataByRequest(data.getCarXszSmj());
        mBinding.myMlDutyPaidProveSmj.setListDataByRequest(data.getDutyPaidProveSmj());
        mBinding.myNlPledgeAddress.setText(data.getPledgeAddress());
        mBinding.myNlSupplementNote.setText(data.getSupplementNote());
        mBinding.myNlCarNumber.setText(data.getCarNumber());
        mBinding.myNlAreaName.setText(data.getAreaName());

    }

    private void initCustomView() {
        mBinding.myNlDateTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true, true, false, false, false);
        });


    }


    private boolean check() {
        // 抵押日期
        if (TextUtils.isEmpty(mBinding.myNlDateTime.check())) {
            return false;
        }
        return true;
    }

    private void inputRequest() {
        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("pledgeBankCommitDatetime", mBinding.myNlDateTime.getText());
        map.put("pledgeBankCommitNot", mBinding.myElRemark.getText());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632132", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(CldyApplyActivity.this, "提交成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(CldyApplyActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
