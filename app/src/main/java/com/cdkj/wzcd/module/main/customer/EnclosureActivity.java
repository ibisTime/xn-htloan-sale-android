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
import com.cdkj.wzcd.adapter.EnclosureAdapter;
import com.cdkj.wzcd.databinding.ActivityEnclosureBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;

public class EnclosureActivity extends AbsBaseLoadActivity {

    private ActivityEnclosureBinding mBinding;
//    private RefreshHelper mRefreshHelper;
    ArrayList<ZXDetialsBean.ListBean.AttachmentsBean> attachmentsList;

    public static void open(Context context, ArrayList<ZXDetialsBean.ListBean.AttachmentsBean> attachmentsList) {
        Intent intent = new Intent(context, EnclosureActivity.class);
        intent.putExtra(CdRouteHelper.DATA_SIGN, attachmentsList);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_enclosure, null, false);
        mBaseBinding.titleView.setMidTitle("附件池");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            attachmentsList = (ArrayList<ZXDetialsBean.ListBean.AttachmentsBean>) getIntent().getSerializableExtra(CdRouteHelper.DATA_SIGN);
        }
        initListener();
        initRecyclerView();
    }

    private void initListener() {
        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            RelationEnclosureActivity.open(this);
        });
        mBinding.myCbConfirm.setOnConfirmRightListener(view -> {
            LocalUpEnclosureActivity.open(this);
        });
    }

    private void initRecyclerView() {
        EnclosureAdapter adapter = new EnclosureAdapter(attachmentsList);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mBinding.rv.setAdapter(adapter);
    }


    //数据是从上个界面全部传递过来的 所以不用刷新控件了
//    private void initRecyclerView() {
//        mRefreshHelper = new RefreshHelper(this, new BaseRefreshCallBack<EnclosureBean>(this) {
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
//            public RecyclerView.Adapter getAdapter(List<EnclosureBean> listData) {
//                EnclosureAdapter adapter = new EnclosureAdapter(listData);
//                return adapter;
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
//        ArrayList<EnclosureBean> data = new ArrayList<>();
//        data.add(new EnclosureBean());
//        data.add(new EnclosureBean());
//        mRefreshHelper.setData(data, "暂无附件", 0);
//    }
}
