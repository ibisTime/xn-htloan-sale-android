package com.cdkj.wzcd.module.business.cllh;

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
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityCllhInputMessageBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.DatePickerHelper;
import com.cdkj.wzcd.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 车辆落户跳转的界面
 */
public class CllhInputMessageActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivityCllhInputMessageBinding mBinding;

    public static void open(Context context, String code) {
        if (context != null) {
            Intent intent = new Intent(context, CllhInputMessageActivity.class);
            intent.putExtra(DATA_SIGN, code);
            context.startActivity(intent);
        }
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_cllh_input_message, null, false);
        mBinding.myMlQualified.setVisibility(View.GONE);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("录入");

        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

        initListener();
        initCustomView();
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
        mBinding.myNlName.setText(data.getApplyUserName());
        mBinding.myNlCode.setText(data.getCode());
        mBinding.myNlBank.setText(data.getLoanBankName());
        mBinding.myNlLoanAmount.setText(RequestUtil.formatAmountDivSign(data.getLoanAmount()));

        if (!TextUtils.equals(data.getCurNodeCode(), "002_11")) { // 业务团队车辆落户

            mBinding.myNlDateTime.setText(DateUtil.formatStringData(data.getCarSettleDatetime(), DateUtil.DEFAULT_DATE_FMT));
            mBinding.myNlPolicyExpireTime.setText(DateUtil.formatStringData(data.getPolicyDueDate(), DateUtil.DEFAULT_DATE_FMT));
            mBinding.myNlPolicyTime.setText(DateUtil.formatStringData(data.getPolicyDatetime(), DateUtil.DEFAULT_DATE_FMT));
            mBinding.myMlReceipt.setListDataByRequest(data.getCarInvoice());
            mBinding.myMlQualified.setListDataByRequest(data.getCarHgz());
            mBinding.myMlJqx.setListDataByRequest(data.getCarJqx());
            mBinding.myMlSyx.setListDataByRequest(data.getCarSyx());
            mBinding.myMlOther.setListDataByRequest(data.getCarSettleOtherPdf());

            mBinding.myCbConfirm.setVisibility(View.GONE);
        }

    }

    private void initCustomView() {
        mBinding.myMlReceipt.build(this, 1);
        mBinding.myMlQualified.build(this, 2);
        mBinding.myMlJqx.build(this, 3);
        mBinding.myMlSyx.build(this, 4);
        mBinding.myMlOther.build(this, 5);
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

                if (requestCode == mBinding.myMlReceipt.getRequestCode()) {
                    mBinding.myMlReceipt.addList(key);
                }

                if (requestCode == mBinding.myMlQualified.getRequestCode()) {
                    mBinding.myMlQualified.addList(key);
                }

                if (requestCode == mBinding.myMlJqx.getRequestCode()) {
                    mBinding.myMlJqx.addList(key);
                }

                if (requestCode == mBinding.myMlSyx.getRequestCode()) {
                    mBinding.myMlSyx.addList(key);
                }

                if (requestCode == mBinding.myMlOther.getRequestCode()) {
                    mBinding.myMlOther.addList(key);
                }

                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }

    private void initListener() {
        mBinding.myNlDateTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true, true, false, false, false);
        });
        mBinding.myNlPolicyTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlPolicyTime, true, true, true, false, false, false);
        });
        mBinding.myNlPolicyExpireTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlPolicyExpireTime, true, true, true, false, false, false);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                settle();
            }
        });
    }

    public boolean check() {
        // 落户日期
        if (TextUtils.isEmpty(mBinding.myNlDateTime.check())) {
            return false;
        }
        //保单到期日
        if (TextUtils.isEmpty(mBinding.myNlPolicyExpireTime.check())) {
            return false;
        }
        //保单日期
        if (TextUtils.isEmpty(mBinding.myNlPolicyTime.check())) {
            return false;
        }
        // 发票
        if (mBinding.myMlReceipt.check()) {
            return false;
        }
//        // 合格证
//        if (mBinding.myMlQualified.check()){
//            return false;
//        }
        // 交强险
        if (mBinding.myMlJqx.check()) {
            return false;
        }
        // 商业险
        if (mBinding.myMlSyx.check()) {
            return false;
        }

        return true;
    }

    public void settle() {
        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        map.put("carSettleDatetime", mBinding.myNlDateTime.getTag());
        map.put("carInvoice", mBinding.myMlReceipt.getListData());
        map.put("carHgz", mBinding.myMlQualified.getListData());
        map.put("carJqx", mBinding.myMlJqx.getListData());
        map.put("carSyx", mBinding.myMlSyx.getListData());
        map.put("carSettleOtherPdf", mBinding.myMlOther.getListData());
        map.put("operator", SPUtilHelper.getUserId());

        map.put("policyDueDate", mBinding.myNlPolicyExpireTime.getTag());
        map.put("policyDatetime", mBinding.myNlPolicyTime.getTag());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632128", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(CllhInputMessageActivity.this, "录入成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(CllhInputMessageActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
