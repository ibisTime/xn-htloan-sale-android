package com.cdkj.wzcd.module.cartool.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.HistoryUserAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.HistoryBean;
import com.cdkj.wzcd.model.RepaymentModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class HistoryUserActivity extends AbsRefreshListActivity<HistoryBean> {

    private List<DataDictionary> mList;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, HistoryUserActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("历史客户");
        initRefreshHelper(10);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        mList = new ArrayList<>();
        HistoryUserAdapter mAdapter = new HistoryUserAdapter(listData, mList);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            HistoryUserDetailsActivity.open(HistoryUserActivity.this, mAdapter.getItem(position).getCode());
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        DataDictionaryHelper.getDataDictionaryRequest(this, DataDictionaryHelper.status, "", (List<DataDictionary> list) -> {

            if (list == null || list.size() == 0)
                return;

            mList.addAll(list);

            Map<String, Object> map = RetrofitUtils.getRequestMap();

            List<String> curNodeCodeList = new ArrayList<>();
            curNodeCodeList.add("003_14");
            curNodeCodeList.add("003_15");
            curNodeCodeList.add("003_16");
            curNodeCodeList.add("003_07");
            curNodeCodeList.add("007_04");

            map.put("curNodeCodeList", curNodeCodeList);
            map.put("limit", limit + "");
            map.put("start", pageIndex + "");
            map.put("teamCode", SPUtilHelper.getTeamCode());
//            map.put("userId", SPUtilHelper.getUserId());

            if (isShowDialog) {
                showLoadingDialog();
            }
            Call call = RetrofitUtils.createApi(MyApiServer.class).getRepaymentList("630520", StringUtils.getJsonToString(map));
            addCall(call);
            call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<RepaymentModel>>(this) {

                @Override
                protected void onSuccess(ResponseInListModel<RepaymentModel> data, String SucMessage) {
                    mRefreshHelper.setData(data.getList(), "暂无历史用户", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();

                }
            });
        });
    }

}
