package com.cdkj.wzcd.module.business.audit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.AuditListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.RepaymentModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * GPS 安装
 * Created by cdkj on 2018/5/30.
 */

public class AuditListActivity extends AbsRefreshListActivity {

    private List<DataDictionary> mList;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AuditListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("结清审核");
        initRefreshHelper(10);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRefreshHelper.onDefaultMRefresh(true);
    }


    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        AuditListAdapter auditListAdapter = new AuditListAdapter(listData, mList);
        auditListAdapter.setOnItemClickListener((adapter, view, position) -> {
        });
        return auditListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        DataDictionaryHelper.getDataDictionaryRequest(AuditListActivity.this, DataDictionaryHelper.gps_apply_status, "", (List<DataDictionary> list) -> {

            if (list == null || list.size()==0)
                return;

            mList = list;

            Map<String, Object> map = RetrofitUtils.getRequestMap();

            List<String> curNodeCodeList = new ArrayList<>();
            curNodeCodeList.add("003_02");
            curNodeCodeList.add("003_03");
            curNodeCodeList.add("003_04");
            curNodeCodeList.add("003_05");

            map.put("limit", limit + "");
            map.put("start", pageIndex + "");
            map.put("refType", "0");
            map.put("curNodeCodeList", curNodeCodeList);

            if (isShowDialog) {
                showLoadingDialog();
            }
            Call call = RetrofitUtils.createApi(MyApiServer.class).getRepaymentList("630520", StringUtils.getJsonToString(map));
            addCall(call);
            call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<RepaymentModel>>(this) {

                @Override
                protected void onSuccess(ResponseInListModel<RepaymentModel> data, String SucMessage) {
                    mRefreshHelper.setData(data.getList(), "暂无审核记录", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();

                }
            });

        });
    }
}
