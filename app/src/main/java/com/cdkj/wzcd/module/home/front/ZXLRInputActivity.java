package com.cdkj.wzcd.module.home.front;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityZxlrinputBinding;
import com.cdkj.wzcd.model.CreditUserUpModel;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 征信录入界面
 */
public class ZXLRInputActivity extends AbsBaseLoadActivity {
    private ActivityZxlrinputBinding mBinding;
    private ZXDetialsBean.ListBean.CreditUserListBean currentData;
    private CreditUserUpModel upitem;
    private boolean isRequest;//是否是复显数据

    public static void open(Context context, ZXDetialsBean.ListBean.CreditUserListBean item, CreditUserUpModel upitem, boolean isRequest) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ZXLRInputActivity.class);
        intent.putExtra(DATA_SIGN, item);
        intent.putExtra("upitem", upitem);
        intent.putExtra("isRequest", isRequest);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zxlrinput, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("录入信息");
        if (getIntent() != null) {
            currentData = (ZXDetialsBean.ListBean.CreditUserListBean) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
            upitem = (CreditUserUpModel) getIntent().getSerializableExtra("upitem");
            isRequest = getIntent().getBooleanExtra("isRequest", false);
            if (upitem == null) {
                upitem = new CreditUserUpModel();
            }
        }

        initView();
        setViewData();
        initListener();
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (!isRequest) {
                upitem.setCreditUserCode(currentData.getCode());
                upitem.setDataCreditReport(mBinding.myMLDsj.getListData());
                upitem.setBankCreditReport(mBinding.myIlZxbgImg.getFlImgUrl());
                upitem.setCreditNote(mBinding.myElRemarks.getText());
                upitem.setBankResult(mBinding.mySlIsbank.getDataKey());

                EventBus.getDefault().post(upitem);
                finish();
            }
        });
    }

    private void initView() {
        if (isRequest) {
            mBinding.myCbConfirm.setVisibility(View.GONE);
        }

        mBinding.myIlZxbgImg.setActivity(this, 1, 0);
        mBinding.myMLDsj.build(this, 2);
        DataDictionary bean1 = new DataDictionary();
        bean1.setDkey("0");
        bean1.setDvalue("通过");
        DataDictionary bean2 = new DataDictionary();
        bean2.setDkey("1");
        bean2.setDvalue("不通过");
        ArrayList<DataDictionary> selectData = new ArrayList<>();
        selectData.add(bean1);
        selectData.add(bean2);
        mBinding.mySlIsbank.setData(selectData, null);

    }

    private void setViewData() {
        mBinding.myNLName.setText(currentData.getUserName());
        mBinding.myNLRelation.setText(BizTypeHelper.getNameOnTheKey(BizTypeHelper.credit_user_relation, currentData.getRelation()));
        mBinding.myNLRole.setText(BizTypeHelper.getNameOnTheKey(BizTypeHelper.credit_user_loan_role, currentData.getLoanRole()));
        mBinding.myNLPhone.setText(currentData.getMobile());
        mBinding.myNLIdno.setText(currentData.getIdNo());
        if (isRequest) {
            mBinding.myElRemarks.setTextByRequest(currentData.getBankCreditResultRemark());
            mBinding.myMLDsj.setListDataByRequest(currentData.getDataCreditReport());
            mBinding.myIlZxbgImg.setFlImgByRequest(currentData.getBankCreditReport());
            mBinding.mySlIsbank.setTextByRequest(TextUtils.equals(currentData.getBankCreditResult(), "0") ? "通过" : "不通过");
        } else {
            ///下面是设置需要录入的信息复显
            mBinding.myElRemarks.setText(upitem.getCreditNote());
            mBinding.myMLDsj.setListData(upitem.getDataCreditReport());
            mBinding.myIlZxbgImg.setFlImg(upitem.getBankCreditReport());
            mBinding.mySlIsbank.setContentByKey(upitem.getBankResult());
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }//myIl_idCard
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();
        new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {

                LogUtil.E("requestCode=" + requestCode);

                if (requestCode == mBinding.myMLDsj.getRequestCode()) {
                    mBinding.myMLDsj.addList(key);
                }
                if (requestCode == mBinding.myIlZxbgImg.getRequestCode()) {
                    mBinding.myIlZxbgImg.setFlImg(key);
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
