package com.cdkj.wzcd.module.business.cldy;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityDysqBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.SuccessBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

public class DysqActivity extends AbsBaseLoadActivity {
    private ActivityDysqBinding mBinding;
    private String code;

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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_dysq, null, false);
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
        mBinding.myNlAge.setText(data.getAge() + "");
        mBinding.myNlNation.setText(data.getNation());
        mBinding.myNlIdNo.setText(data.getIdNo());
        mBinding.myNlMobile.setText(data.getMobile());
        mBinding.myNlSaleUserName.setText(data.getSaleUserName());
        mBinding.myNlInsideJobName.setText(data.getInsideJobName());


    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("operator", SPUtilHelper.getUserId());
        map.put("supplementNote", mBinding.myElRemark.getText());
        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).success("632144", StringUtils.getJsonToString(map));
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {

                UITipDialog.showSuccess(DysqActivity.this, "成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
