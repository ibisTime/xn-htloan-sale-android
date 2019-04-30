package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CreditCardDetailsAdapter;
import com.cdkj.wzcd.databinding.ActivityCreditCardBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.List;

/**
 * 征信单详情
 */
public class CreditCardDetailsActivity extends AbsBaseLoadActivity {
//    private RefreshHelper mRefreshHelper;
    private ActivityCreditCardBinding mBinding;
    ZXDetialsBean.ListBean currentItem;

    public static void open(Context context, ZXDetialsBean.ListBean currentItem) {
        Intent intent = new Intent(context, CreditCardDetailsActivity.class);
        intent.putExtra(CdRouteHelper.DATA_SIGN, currentItem);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_credit_card, null, false);
        mBaseBinding.titleView.setMidTitle("征信单详情");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            currentItem = (ZXDetialsBean.ListBean) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
        }

        setView();
        initRecyclerView();
        initListener();
        initRecyclerView();
    }

    private void setView() {
        if (currentItem == null)
            return;

        mBinding.tvCode.setText(currentItem.getCode());
        ZXDetialsBean.ListBean.CreditUserBean credit = currentItem.getCreditUser();
        if (credit != null) {
            mBinding.tvBank.setText(currentItem.getLoanBankName());
            mBinding.tvName.setText(credit.getUserName());
            if (TextUtils.equals(credit.getBizCode(), "0")) {
                mBinding.tvType.setText("新车");
            } else {
                mBinding.tvType.setText("二手车");
            }
            mBinding.tvMoney.setText(StringUtils.formatNum(currentItem.getLoanAmount()));
        }
        String valueOnTheKey = DataDictionaryHelper.getValueOnTheKey(currentItem.getStatus(), CustomerDetailsActivity.dataDictionaryList);
        mBinding.tvJiedian.setText(valueOnTheKey);

    }

    private void initListener() {
        mBinding.llCode.setOnClickListener(v -> {
            CreditPeopleDetailsActivity.open(this,currentItem.getCreditUser());
        });
    }

    private void initRecyclerView() {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (currentItem.getCreditUserList() == null) {
            mBinding.rv.setAdapter(null);
        } else {
            List<ZXDetialsBean.ListBean.CreditUserListBean> creditUserList = currentItem.getCreditUserList();
            CreditCardDetailsAdapter creditCardDetailsAdapter = new CreditCardDetailsAdapter(creditUserList);

            creditCardDetailsAdapter.setOnItemClickListener((adapter, view, position) -> {
                ZXDetialsBean.ListBean.CreditUserListBean item= (ZXDetialsBean.ListBean.CreditUserListBean) adapter.getItem(position);
                CreditPeopleDetailsActivity.open(CreditCardDetailsActivity.this,item);
            });
            mBinding.rv.setAdapter(creditCardDetailsAdapter);
        }

    }


//    private void initRecyclerView() {
//        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack<CreditCardDetailsBean>(this) {
//
//            @Override
//            public View getRefreshLayout() {
//                return mBinding.refreshLayout;
//            }
//
//            @Override
//            public RecyclerView getRecyclerView() {
//                return mBinding.rv;
//            }
//
//            @Override
//            public RecyclerView.Adapter getAdapter(List<CreditCardDetailsBean> listData) {
//                return new CreditCardDetailsAdapter(listData);
//            }
//
//            @Override
//            public void getListDataRequest(int pageIndex, int limit, boolean isShowDialog) {
//                getData(pageIndex, limit, isShowDialog);
//            }
//        });
//        mRefreshHelper.init(10);
//        mRefreshHelper.onDefaultMRefresh(true);
//    }
//
//    private void getData(int pageIndex, int limit, boolean isShowDialog) {
//        ArrayList<CreditCardDetailsBean> data = new ArrayList<>();
//        data.add(new CreditCardDetailsBean());
//        data.add(new CreditCardDetailsBean());
//        mRefreshHelper.setData(data, "暂无征信人", 0);
//    }

}
