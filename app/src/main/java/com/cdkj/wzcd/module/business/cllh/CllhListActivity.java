package com.cdkj.wzcd.module.business.cllh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.CllhListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.CllhListBean;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 车辆落户
 */
public class CllhListActivity extends AbsRefreshListActivity<CllhListBean> {

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CllhListActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("车辆落户");

        initRefreshHelper(10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        CllhListAdapter mAdapter = new CllhListAdapter(listData);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CllhInputMessageActivity.open(CllhListActivity.this, mAdapter.getItem(position).getCode());
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        Map<String, Object> map = RetrofitUtils.getNodeListMap();

        List<String> curNodeCodeList = new ArrayList<>();
        curNodeCodeList.add("002_11");
        curNodeCodeList.add("002_13");
        curNodeCodeList.add("002_14");
        curNodeCodeList.add("002_15");
        curNodeCodeList.add("002_16");
        curNodeCodeList.add("002_17");

        map.put("curNodeCodeList", curNodeCodeList);
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
        if (!UserHelper.isZHRY()) {
            map.put("saleUserId", SPUtilHelper.getUserId());
            map.put("teamCode", SPUtilHelper.getTeamCode());
        }
        map.put("userId", SPUtilHelper.getUserId());
        if (isShowDialog) showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getNodeList("632148", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<NodeListModel>>(this) {
            @Override
            protected void onSuccess(ResponseInListModel<NodeListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无落户记录", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
