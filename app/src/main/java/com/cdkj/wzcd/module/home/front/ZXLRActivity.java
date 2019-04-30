package com.cdkj.wzcd.module.home.front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.ZXDetialsAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 征信录入
 */
public class ZXLRActivity extends AbsRefreshListActivity {

    public static void open(Context context) {
        Intent intent = new Intent(context, ZXLRActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("征信录入");
        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        ZXDetialsAdapter mAdapter = new ZXDetialsAdapter(listData);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ZXDetialsBean.ListBean item = mAdapter.getItem(position);
//            CreditDetailActivity.open(this, item.getCode());
            ZRDDetialsActivity.open(this, item);
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        Map<String, Object> map = RetrofitUtils.getRequestMap();
        List<String> curNodeCodeList = new ArrayList<>();
        curNodeCodeList.add("a1");
        curNodeCodeList.add("a2");
        curNodeCodeList.add("a3");
        curNodeCodeList.add("ax1");

        map.put("curNodeCodeList", curNodeCodeList);
        map.put("roleCode", SPUtilHelper.getRoleCode());
        map.put("teamCode", SPUtilHelper.getTeamCode());
        map.put("userId", SPUtilHelper.getUserId());
        map.put("token", SPUtilHelper.getUserToken());
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");

        if (isShowDialog) showLoadingDialog();

        Call<BaseResponseModel<ZXDetialsBean>> call = RetrofitUtils.createApi(MyApiServer.class).getCreditList2("632115", StringUtils.getJsonToString(map));
        addCall(call);

        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<ZXDetialsBean>(this) {
            @Override
            protected void onSuccess(ZXDetialsBean data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无资信", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
