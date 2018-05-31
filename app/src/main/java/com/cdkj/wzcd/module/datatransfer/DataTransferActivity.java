package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.DataTransferAdapter;
import com.cdkj.wzcd.model.CllhListBean;
import com.cdkj.wzcd.model.DataTransferBean;

import java.util.ArrayList;
import java.util.List;

public class DataTransferActivity extends AbsRefreshListActivity<CllhListBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, DataTransferActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("资料传递");

        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        ArrayList<DataTransferBean> data = new ArrayList<>();
        data.add(new DataTransferBean());
        data.add(new DataTransferBean());
        data.add(new DataTransferBean());
        data.add(new DataTransferBean());
        data.add(new DataTransferBean());
        data.add(new DataTransferBean());
        DataTransferAdapter mAdapter = new DataTransferAdapter(data);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_send:
                    //发件
                    SendActivity.open(DataTransferActivity.this);
                    break;
                case R.id.btn_receive_and_check:
                    //收件并审核
                    SendAndExamineActivity.open(DataTransferActivity.this);
                    break;

            }
        });
        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        initDatas(pageIndex, limit, isShowDialog);
    }

    /**
     * 获取数据
     *
     * @param pageIndex
     * @param limit
     * @param isShowDialog
     */
    private void initDatas(int pageIndex, int limit, boolean isShowDialog) {


    }

}
