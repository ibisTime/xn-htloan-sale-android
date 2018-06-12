package com.cdkj.wzcd.module.business.join_approval;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.MyApplyListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.NodeListModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 贷前准入 我的申请
 * Created by cdkj on 2018/5/30.
 */
public class MyApplyListFragment extends AbsRefreshListFragment {

    public static MyApplyListFragment getInstance() {
        MyApplyListFragment fragment = new MyApplyListFragment();
        Bundle bundle = new Bundle();
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
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint())
            mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        MyApplyListAdapter myApplyListAdapter = new MyApplyListAdapter(listData);
//        myApplyListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            JoinStep1Activity.open(mActivity);
//        });
        return myApplyListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        Map<String, String> map = RetrofitUtils.getNodeListMap();

        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
        map.put("saleUserId", SPUtilHelper.getUserId());

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
