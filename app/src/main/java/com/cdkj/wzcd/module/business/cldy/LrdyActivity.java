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
import com.cdkj.wzcd.util.RequestUtil;

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
    private int pdCode = 5;
    private int keyCode = 6;

    /**
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LrdyActivity.class);
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
        mBaseBinding.titleView.setMidTitle("录入抵押信息");
        if (getIntent() == null)
            return;
        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();

        initListener();
        initViewBudil();
    }

    private void initViewBudil() {

        mBinding.myMlCarBigSmj.build(this, CarBigSmjCode);
        mBinding.myMlCarPd.build(this, pdCode);
        mBinding.myMlCarKey.build(this, keyCode);
        mBinding.myMlCarRegcerti.build(this, CarRegcerti);
        mBinding.myMlCarXszSmj.build(this, CarXszSmj);
        mBinding.myMlDutyPaidProveSmj.build(this, DutyPaidProveSmj);
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                submit();
            }
        });
    }


    public boolean check() {
        if (TextUtils.isEmpty(mBinding.myElCarNumber.check())) {
            return false;
        }

        if (mBinding.myMlCarRegcerti.check()) {
            return false;
        }
        if (mBinding.myMlCarBigSmj.check()) {

            return false;
        }
        if (mBinding.myMlDutyPaidProveSmj.check()) {
            return false;
        }
        if (mBinding.myMlCarXszSmj.check()) {
            return false;
        }
        if (mBinding.myMlCarPd.check()) {
            return false;
        }
        if (mBinding.myMlCarKey.check()) {
            return false;
        }

        return true;

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
//        区域经理,贷款金额,车辆型号,车架号,车牌号,机动车登记证
//        areaName，loanAmount，carModel，carFrameNo，carNumber
        mBinding.myNlAreaName.setText(data.getAreaName());
        mBinding.myNlSaleUserName.setText(data.getSaleUserName());
        mBinding.myNlLoanAmount.setText(RequestUtil.formatAmountDiv(data.getLoanAmount()));
        mBinding.myNlCarModel.setText(data.getCarModel());
        mBinding.myNlCarFrameNo.setText(data.getCarFrameNo());
        mBinding.myElCarNumber.setText(data.getCarNumber());
        mBinding.myMlCarRegcerti.setListData(data.getCarRegcerti());
        mBinding.myMlCarBigSmj.setListData(data.getCarBigSmj());
        mBinding.myMlCarXszSmj.setListData(data.getCarXszSmj());
        mBinding.myMlDutyPaidProveSmj.setListData(data.getDutyPaidProveSmj());
        mBinding.myMlCarPd.setListData(data.getCarPd());
        mBinding.myMlCarKey.setListData(data.getCarKey());
    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("operator", SPUtilHelper.getUserId());
        map.put("code", code);
        map.put("carBigSmj", mBinding.myMlCarBigSmj.getListData());
        map.put("carKey", mBinding.myMlCarKey.getListData());//
        map.put("carNumber", mBinding.myElCarNumber.getText());
        map.put("carPd", mBinding.myMlCarPd.getListData());
        map.put("carRegcerti", mBinding.myMlCarRegcerti.getListData());
        map.put("carXszSmj", mBinding.myMlCarXszSmj.getListData());
        map.put("dutyPaidProveSmj", mBinding.myMlDutyPaidProveSmj.getListData());



        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).success("632133", StringUtils.getJsonToString(map));
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {
                UITipDialog.showSuccess(LrdyActivity.this, "成功", dialogInterface -> {
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
                if (requestCode == mBinding.myMlCarPd.getRequestCode()) {
                    mBinding.myMlCarPd.addList(key);
                }
                if (requestCode == mBinding.myMlCarKey.getRequestCode()) {
                    mBinding.myMlCarKey.addList(key);
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
