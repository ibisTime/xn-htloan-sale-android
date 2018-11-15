package com.cdkj.wzcd.module.business.cldy;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityLrdyBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.SuccessBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

public class LrdyActivity extends AbsBaseLoadActivity {
//activity_lrdy

    private ActivityLrdyBinding mBinding;
    private String code;
    private final int CarBigSmjCode = 1;
    private final int CarRegcerti = 2;
    private final int CarXszSmj = 3;
    private final int DutyPaidProveSmj = 4;

    /**
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, DysqActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_lrdy, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("抵押申请");
        if (getIntent() == null)
            return;
        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

        initListener();
        initViewBudil();
    }

    private void initViewBudil() {

        mBinding.myMlCarBigSmj.build(this, CarBigSmjCode);
        mBinding.myMlCarRegcerti.build(this, CarRegcerti);
        mBinding.myMlCarXszSmj.build(this, CarXszSmj);
        mBinding.myMlDutyPaidProveSmj.build(this, DutyPaidProveSmj);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {

            submit();
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
        mBinding.myNlName.setText(data.getApplyUserName());
        mBinding.myNlCode.setText(data.getCode());
        mBinding.myNlTeamName.setText(data.getTeamName());
//        mBinding.myNlCjh.setText(data.getTeamName());
        mBinding.myNlSaleUserName.setText(data.getSaleUserName());
        data.getCarNumber();
    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("operator", SPUtilHelper.getUserId());
        map.put("code", code);
        map.put("carBigSmj", code);
        map.put("carKey", code);
        map.put("carNumber", code);
        map.put("carPd", code);
        map.put("carRegcerti", code);
        map.put("carXszSmj", code);
        map.put("dutyPaidProveSmj", code);

//        carBigSmj	必填，大本扫描件	string
//        carKey	必填，车钥匙	string
//        carNumber	必填，车牌号	string
//        carPd	必填，车辆批单	string
//        carRegcerti	必填，登记证书	string
//        carXszSmj	必填，车辆行驶证扫描件	string
//        code	必填，预算单编号	string
//        dutyPaidProveSmj	必填，完税证明扫描件	string
//        operator

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).success("632133", StringUtils.getJsonToString(map));
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {

                showSureDialog("成功", view -> {
                    finish();
                });

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();
        new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {

                if (requestCode == mBinding.myMlDutyPaidProveSmj.getRequestCode()) {
                    mBinding.myMlDutyPaidProveSmj.addList(key);
                }
                if (requestCode == mBinding.myMlCarXszSmj.getRequestCode()) {
                    mBinding.myMlCarXszSmj.addList(key);
                }
                if (requestCode == mBinding.myMlCarRegcerti.getRequestCode()) {
                    mBinding.myMlCarRegcerti.addList(key);
                }
                if (requestCode == mBinding.myMlCarBigSmj.getRequestCode()) {
                    mBinding.myMlCarBigSmj.addList(key);
                }

                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);

    }
}
