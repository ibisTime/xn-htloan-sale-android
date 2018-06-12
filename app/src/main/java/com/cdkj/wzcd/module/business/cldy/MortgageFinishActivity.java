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
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityMortgageFinishBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/10.
 */

public class MortgageFinishActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivityMortgageFinishBinding mBinding;

    public static void open(Context context, String code) {
        if (context != null) {
            Intent intent = new Intent(context, MortgageFinishActivity.class);
            intent.putExtra(DATA_SIGN, code);
            context.startActivity(intent);
        }
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_mortgage_finish, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("抵押完成");

        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

        initListener();
        initCustomView();
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()){
                settle();
            }
        });
    }

    public boolean check(){
        // 车牌号
        if (TextUtils.isEmpty(mBinding.myElCarNumber.check())){
            return false;
        }
        // 机动车登记证书
        if (TextUtils.isEmpty(mBinding.myIlCarRegcerti.check())){
            return false;
        }
        // 批单
        if (TextUtils.isEmpty(mBinding.myIlCarPd.check())){
            return false;
        }
        // 车钥匙
        if (TextUtils.isEmpty(mBinding.myIlCarKey.check())){
            return false;
        }
        // 绿大本扫描件
        if (TextUtils.isEmpty(mBinding.myIlCarBigSmj.check())){
            return false;
        }

        return true;
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

    }

    private void initCustomView() {
        mBinding.myIlCarRegcerti.setActivity(this,1,0);
        mBinding.myIlCarPd.setActivity(this,2,0);
        mBinding.myIlCarKey.setActivity(this,3,0);
        mBinding.myIlCarBigSmj.setActivity(this,4,0);
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

                if (requestCode == mBinding.myIlCarRegcerti.getRequestCode()){
                    mBinding.myIlCarRegcerti.setFlImg(key);
                }

                if (requestCode == mBinding.myIlCarPd.getRequestCode()){
                    mBinding.myIlCarPd.setFlImg(key);
                }

                if (requestCode == mBinding.myIlCarKey.getRequestCode()){
                    mBinding.myIlCarKey.setFlImg(key);
                }

                if (requestCode == mBinding.myIlCarBigSmj.getRequestCode()){
                    mBinding.myIlCarBigSmj.setFlImg(key);
                }

                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }

    public void settle(){
        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        map.put("carNumber", mBinding.myElCarNumber.getText());
        map.put("carRegcerti", mBinding.myIlCarRegcerti.getFlImgUrl());
        map.put("carPd", mBinding.myIlCarPd.getFlImgUrl());
        map.put("carKey", mBinding.myIlCarKey.getFlImgUrl());
        map.put("carBigSmj", mBinding.myIlCarBigSmj.getFlImgUrl());
        map.put("operator", SPUtilHelper.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632133", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(MortgageFinishActivity.this, "录入成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(MortgageFinishActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
