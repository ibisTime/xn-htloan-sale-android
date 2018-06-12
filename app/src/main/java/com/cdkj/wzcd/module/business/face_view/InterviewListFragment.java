package com.cdkj.wzcd.module.business.face_view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.InterviewListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 面签
 * Created by cdkj on 2018/5/30.
 */
public class InterviewListFragment extends AbsRefreshListFragment {

    // 业务种类
    private List<DataDictionary> mType = new ArrayList<>();

    public static InterviewListFragment getInstance() {
        InterviewListFragment fragment = new InterviewListFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){

            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        InterviewListAdapter interviewListAdapter = new InterviewListAdapter(listData, mType);
        interviewListAdapter.setOnItemClickListener((adapter, view, position) -> {
            InterviewStartActivity.open(mActivity);
        });
        return interviewListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        DataDictionaryHelper.getDataDictionaryRequest(mActivity, DataDictionaryHelper.budget_orde_biz_typer, "", data -> {

            if (data == null || data.size() == 0){
                return;
            }

            mType.addAll(data);


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
                    mRefreshHelper.setData(data.getList(), "暂无面签记录", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();
                }
            });
        });

    }
}
