package com.cdkj.wzcd.module.business.cllh;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CllhListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.HeadGpsCheckBinding;
import com.cdkj.wzcd.model.CllhListBean;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.PickerViewDataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 车辆落户
 */
public class CllhListActivity extends AbsRefreshListActivity<CllhListBean> {
    private HeadGpsCheckBinding headView;
    private List<PickerViewDataBean> typeDatas;
    private int selectFrist;

    public static void open(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CllhListActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("发保合");

//        initTitelAndHead();
        initRefreshHelper(10);
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
    protected void onResume() {
        super.onResume();

        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        CllhListAdapter mAdapter = new CllhListAdapter(listData);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CllhInputMessageActivity.open(CllhListActivity.this, mAdapter.getItem(position).getCode(),true);
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        Map<String, Object> map = RetrofitUtils.getNodeListMap();

        List<String> curNodeCodeList = new ArrayList<>();
//        curNodeCodeList.add("002_11");
//        curNodeCodeList.add("002_13");
//        curNodeCodeList.add("002_14");
//        curNodeCodeList.add("002_15");
//        curNodeCodeList.add("002_16");
//        curNodeCodeList.add("002_17");
        curNodeCodeList.add("002_18");


//        map.put("curNodeCodeList", curNodeCodeList);
        map.put("advanfCurNodeCodeList", curNodeCodeList);
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");

        map.put("teamCode", SPUtilHelper.getTeamCode());
        map.put("userId", SPUtilHelper.getUserId());

//        if (selectFrist == 0) {
//            //未录入
//            map.put("isEntryMortgage", "0");
//        } else {
//            //已录入
//            map.put("isEntryMortgage", "1");
//        }
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
