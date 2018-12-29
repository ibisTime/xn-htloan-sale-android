package com.cdkj.wzcd.module.business.gps_install;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.GpsInstallListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.HeadGpsCheckBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.PickerViewDataBean;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * GPS 安装
 * Created by cdkj on 2018/5/30.
 */

public class GPSInstallListActivity extends AbsRefreshListActivity {

    private List<DataDictionary> mList;
    private HeadGpsCheckBinding headView;
    private int selectFrist;
    private List<PickerViewDataBean> typeDatas;

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GPSInstallListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.gps_install));
        initTitelAndHead();
        initRefreshHelper(10);
    }

    @Override
    public void onResume() {
        super.onResume();

        mRefreshHelper.onDefaultMRefresh(true);
    }


    private void initTitelAndHead() {
        headView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_gps_check, null, false);
        mRefreshBinding.llHead.addView(headView.getRoot());
        typeDatas = new ArrayList<>();
        PickerViewDataBean bean1 = new PickerViewDataBean();
        bean1.setKey("未安装");
        bean1.setValue("0");
        PickerViewDataBean bean2 = new PickerViewDataBean();
        bean2.setKey("已安装");
        bean2.setValue("1");
        typeDatas.add(bean1);
        typeDatas.add(bean2);
        headView.tvType.setText(typeDatas.get(0).getKey());//初始化
        headView.llType.setOnClickListener(v -> {//
            OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(this, (options1, options2, options3, v1) -> {
                selectFrist = options1;
                headView.tvType.setText(typeDatas.get(options1).getKey());
                //刷新数据
                mRefreshHelper.onDefaultMRefresh(true);
            }).build();

            optionsPickerView.setPicker(typeDatas);
            optionsPickerView.setSelectOptions(selectFrist);
            optionsPickerView.show();

        });
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        GpsInstallListAdapter gpsInstallListAdapter = new GpsInstallListAdapter(listData, mList);
        gpsInstallListAdapter.setOnItemClickListener((adapter, view, position) -> {
            GpsDetailActivity.open(GPSInstallListActivity.this, gpsInstallListAdapter.getItem(position).getCode());
        });
        return gpsInstallListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        DataDictionaryHelper.getDataDictionaryRequest(GPSInstallListActivity.this, DataDictionaryHelper.gps_apply_status, "", (List<DataDictionary> list) -> {

            if (list == null || list.size() == 0)
                return;

            mList = list;

            Map<String, Object> map = RetrofitUtils.getNodeListMap();

            List<String> curNodeCodeList = new ArrayList<>();
            curNodeCodeList.add("002_09");
            curNodeCodeList.add("002_10");
            curNodeCodeList.add("002_12");
            curNodeCodeList.add("002_32");

            map.put("advanfCurNodeCodeList", curNodeCodeList);
            map.put("start", pageIndex + "");
            map.put("limit", limit + "");
            map.put("roleCode", SPUtilHelper.getRoleCode());
            map.put("teamCode", SPUtilHelper.getTeamCode());
            map.put("userId", SPUtilHelper.getUserId());
            if (selectFrist == 0) {
                //未安装
                map.put("isGpsAz", "0");
            } else {
                //已安装
                map.put("isGpsAz", "1");
            }

            if (isShowDialog) showLoadingDialog();

            Call call = RetrofitUtils.createApi(MyApiServer.class).getNodeList("632148", StringUtils.getJsonToString(map));
            addCall(call);

            call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<NodeListModel>>(this) {
                @Override
                protected void onSuccess(ResponseInListModel<NodeListModel> data, String SucMessage) {
                    mRefreshHelper.setData(data.getList(), "暂无安装记录", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();
                }
            });

        });
    }
}
