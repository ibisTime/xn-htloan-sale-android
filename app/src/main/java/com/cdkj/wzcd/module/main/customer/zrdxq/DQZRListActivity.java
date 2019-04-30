package com.cdkj.wzcd.module.main.customer.zrdxq;

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
import com.cdkj.wzcd.adapter.DQZZRAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class DQZRListActivity extends AbsRefreshListActivity {


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, DQZRListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("贷前准入");
        initRefreshHelper(10);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        DQZZRAdapter madapter = new DQZZRAdapter(listData);
        madapter.setOnItemClickListener((adapter, view, position) -> {
//            InterviewStartActivity.open(this, interviewListAdapter.getItem(position).getCode());
//            CreditDetailActivity.open(this,interviewListAdapter.getItem(position).getCode());
            ZXDetialsBean.ListBean item = madapter.getItem(position);
            ZRDDetialsActivity.open(this, item);
        });
        return madapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        Map<String, Object> map = RetrofitUtils.getNodeListMap();
        List<String> curNodeCodeList = new ArrayList<>();
        curNodeCodeList.add("b1");
        curNodeCodeList.add("b1x");
        map.put("intevCurNodeCodeList", curNodeCodeList);
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("userId", SPUtilHelper.getUserId());
        map.put("teamCode", SPUtilHelper.getTeamCode());

        if (isShowDialog) showLoadingDialog();

        Call<BaseResponseModel<ZXDetialsBean>> nodeList = RetrofitUtils.createApi(MyApiServer.class).getCreditList2("632115", StringUtils.getJsonToString(map));
        addCall(nodeList);

        nodeList.enqueue(new BaseResponseModelCallBack<ZXDetialsBean>(this) {
            @Override
            protected void onSuccess(ZXDetialsBean data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无准入单", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRefreshHelper != null)
            mRefreshHelper.onDefaultMRefresh(true);
    }
}
