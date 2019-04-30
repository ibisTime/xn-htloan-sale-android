package com.cdkj.wzcd.module.business.credit;

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
import com.cdkj.wzcd.adapter.CreditListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 资信调查   发起征信
 */

public class BssCreditListActivity2 extends AbsRefreshListActivity {
    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BssCreditListActivity2.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("资信调查");
//        if (!UserHelper.isZHRY()){
        mBaseBinding.titleView.setRightTitle("发起征信");
        mBaseBinding.titleView.setRightFraClickListener(view -> {
            CreditInitiateActivity.open(this, null);

        });
//        }
        initRefreshHelper(10);

    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        CreditListAdapter mAdapter = new CreditListAdapter(listData);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CreditModel model = mAdapter.getItem(position);
//            CreditDetailActivity.open(mActivity, model.getCode());
            ZRDDetialsActivity.open(this, model.getCode());
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

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCreditList("632115", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CreditModel>>(this) {
            @Override
            protected void onSuccess(ResponseInListModel<CreditModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无资信", 0);
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
        if (mRefreshHelper != null) {
            mRefreshHelper.onDefaultMRefresh(true);
        }
    }
}
