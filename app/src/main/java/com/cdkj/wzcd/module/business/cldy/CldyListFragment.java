package com.cdkj.wzcd.module.business.cldy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CldyListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.HeadGpsCheckBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.PickerViewDataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.baselibrary.appmanager.CdRouteHelper.IS_FIRST_REQUEST;

/**
 * 车辆抵押  嵌套的Fragment
 */
public class CldyListFragment extends AbsRefreshListFragment {
    private HeadGpsCheckBinding headView;
    private ArrayList<PickerViewDataBean> typeDatas;
    private int selectFrist;

    /**
     * @param
     * @return
     */
    public static CldyListFragment getInstance(Boolean isFirstRequest, String status) {
        CldyListFragment fragment = new CldyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, status);
        bundle.putBoolean(IS_FIRST_REQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initRefreshHelper(10);
//        initTitelAndHead();
    }

    /**
     * 增加筛选
     */
    private void initTitelAndHead() {
        headView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_gps_check, null, false);
        mRefreshBinding.llHead.addView(headView.getRoot());
        typeDatas = new ArrayList<>();
        PickerViewDataBean bean1 = new PickerViewDataBean();
        bean1.setKey("未抵押");
        bean1.setValue("0");
        PickerViewDataBean bean2 = new PickerViewDataBean();
        bean2.setKey("已抵押");
        bean2.setValue("1");
        typeDatas.add(bean1);
        typeDatas.add(bean2);
        headView.tvType.setText(typeDatas.get(0).getKey());//初始化
        headView.llType.setOnClickListener(v -> {//
            OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(mActivity, (options1, options2, options3, v1) -> {
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
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {

            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        CldyListAdapter mAdapter = new CldyListAdapter(listData);

//        mAdapter.setOnItemClickListener((adapter, view, position) -> CldyInputMessageActivity.open(mActivity, mAdapter.getItem(position).getCode()));
        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        Map<String, Object> map = RetrofitUtils.getNodeListMap();

        List<String> curNodeCodeList = new ArrayList<>();
        curNodeCodeList.add("002_20");
        curNodeCodeList.add("002_21");
        curNodeCodeList.add("002_33");
        curNodeCodeList.add("002_34");

        map.put("curNodeCodeList", curNodeCodeList);
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
//        map.put("isMortgage", typeDatas.get(selectFrist).getValue());
        map.put("roleCode", SPUtilHelper.getRoleCode());
        map.put("teamCode", SPUtilHelper.getTeamCode());
        map.put("userId", SPUtilHelper.getUserId());


        if (isShowDialog) showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getNodeList("632148", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<NodeListModel>>(mActivity) {
            @Override
            protected void onSuccess(ResponseInListModel<NodeListModel> data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无抵押记录", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}