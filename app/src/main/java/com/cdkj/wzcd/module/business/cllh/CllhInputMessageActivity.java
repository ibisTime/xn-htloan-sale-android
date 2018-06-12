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

    public void getNode(){
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

            mBinding.myIlReceipt.setFlImgByRequest(data.getCarInvoice());
            mBinding.myIlQualified.setFlImgByRequest(data.getCarHgz());
            mBinding.myIlJqx.setFlImgByRequest(data.getCarJqx());
            mBinding.myIlSyx.setFlImgByRequest(data.getCarSyx());

            mBinding.myCbConfirm.setVisibility(View.GONE);
        }

    }

    private void initCustomView() {
        mBinding.myIlReceipt.setActivity(this,1,0);
        mBinding.myIlQualified.setActivity(this,2,0);
        mBinding.myIlJqx.setActivity(this,3,0);
        mBinding.myIlSyx.setActivity(this,4,0);
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

                if (requestCode == mBinding.myIlReceipt.getRequestCode()){
                    mBinding.myIlReceipt.setFlImg(key);
                }

                if (requestCode == mBinding.myIlQualified.getRequestCode()){
                    mBinding.myIlQualified.setFlImg(key);
                }

                if (requestCode == mBinding.myIlJqx.getRequestCode()){
                    mBinding.myIlJqx.setFlImg(key);
                }

                if (requestCode == mBinding.myIlSyx.getRequestCode()){
                    mBinding.myIlSyx.setFlImg(key);
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
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true,  true, false, false, false);
        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){
                settle();
            }
        });
    }

    public boolean check(){
        // 落户日期
        if (TextUtils.isEmpty(mBinding.myNlDateTime.check())){
            return false;
        }
        // 发票
        if (TextUtils.isEmpty(mBinding.myIlReceipt.check())){
            return false;
        }
        // 合格证
        if (TextUtils.isEmpty(mBinding.myIlQualified.check())){
            return false;
        }
        // 交强险
        if (TextUtils.isEmpty(mBinding.myIlJqx.check())){
            return false;
        }
        // 商业险
        if (TextUtils.isEmpty(mBinding.myIlSyx.check())){
            return false;
        }

        return true;
    }

    public void settle(){
        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        map.put("carSettleDatetime", mBinding.myNlDateTime.getText());
        map.put("carInvoice", mBinding.myIlReceipt.getFlImgUrl());
        map.put("carHgz", mBinding.myIlQualified.getFlImgUrl());
        map.put("carJqx", mBinding.myIlJqx.getFlImgUrl());
        map.put("carSyx", mBinding.myIlSyx.getFlImgUrl());
        map.put("operator", SPUtilHelper.getUserId());

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
