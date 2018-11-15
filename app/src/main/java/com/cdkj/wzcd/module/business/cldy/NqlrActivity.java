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
import com.cdkj.wzcd.databinding.ActivityNqlrBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 内勤录入
 */
public class NqlrActivity extends AbsBaseLoadActivity {
    private ActivityNqlrBinding mBinding;
    private String code;

    /**
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, NqlrActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_nqlr, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("内勤录入");
        if (getIntent() == null)
            return;
        code = getIntent().getStringExtra(DATA_SIGN);
        getNode();
        initListener();
        initViewbuild();
    }

    private void initViewbuild() {
        mBinding.myMlPledgeUserIdCardCopy.build(this, 1);
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
        mBinding.myNlCompanyName.setText(data.getCompanyName());
        mBinding.myNlAmount.setText(RequestUtil.formatAmountDivSign(data.getLoanAmount()));
        mBinding.myNlBank.setText(data.getLoanBankName());//信贷

        mBinding.myElInsideJobName.setText(data.getInsideJobName());//
        mBinding.myMlPledgeUserIdCardCopy.setListData(data.getPledgeUserIdCardCopy());
//        mBinding.myMlIdNoPdf.setListDataByRequest();

    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("operator", SPUtilHelper.getUserId());
        map.put("code", code);
        map.put("approveNote", mBinding.myElOther.getText());//审核说明
        map.put("pledgeUser", mBinding.myElInsideJobName.getText());//代理人
        map.put("pledgeUserIdCardCopy", mBinding.myMlPledgeUserIdCardCopy.getListData());//代理人身份证复印件

//        approveNote
//        code
//        operator
//        pledgeUser
//        pledgeUserIdCardCopy

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).success("632124", StringUtils.getJsonToString(map));
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {
                UITipDialog.showSuccess(NqlrActivity.this, "成功", dialogInterface -> {
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
