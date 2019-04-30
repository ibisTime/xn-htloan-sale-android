package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.appmanager.CdRouteHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.TobeActivityAdapter;
import com.cdkj.wzcd.databinding.ActivityToBeBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;

public class ToBeActivity extends AbsBaseLoadActivity {

    private ActivityToBeBinding mBinding;
    ArrayList<ZXDetialsBean.ListBean.BizTasksBean> bizTasksBeansList;

    public static void open(Context context, ArrayList<ZXDetialsBean.ListBean.BizTasksBean> bizTasksBeansList) {
        Intent intent = new Intent(context, ToBeActivity.class);
        intent.putExtra(CdRouteHelper.DATA_SIGN, bizTasksBeansList);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_to_be, null, false);
        mBaseBinding.titleView.setMidTitle("待办事项");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            bizTasksBeansList = (ArrayList<ZXDetialsBean.ListBean.BizTasksBean>) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
        }
        initRcyclerView();
    }

    private void initRcyclerView() {
        TobeActivityAdapter tobeActivityAdapter = new TobeActivityAdapter(bizTasksBeansList);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.rv.setAdapter(tobeActivityAdapter);
    }
}
