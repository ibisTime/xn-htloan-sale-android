package com.cdkj.wzcd.module.main.message;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.MessageSystemAdapter;
import com.cdkj.wzcd.adapter.MessageTobeAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.common.WebViewMessageActivity;
import com.cdkj.wzcd.model.MessageSystemBean;
import com.cdkj.wzcd.model.MessageTobeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 消息Fragment
 */
public class MessageItemFragment extends AbsRefreshListFragment {

    private String type;

    //1 提醒 2 通知 3 公告  老的
    //1是 全部  2是  待办事项  3是 系统公告
    public static Fragment getInstance(String type) {
        MessageItemFragment messageItemFragment = new MessageItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        messageItemFragment.setArguments(bundle);
        return messageItemFragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString("type");
        }
        initRefreshHelper(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        BaseQuickAdapter adapter = null;
        switch (type) {
            case "1":
                adapter = getAllAdapter(listData);
                break;
            case "2":
                adapter = getTobeAdapter(listData);
                break;
            case "3":
                adapter = getSystemAdapter(listData);
                break;
        }
        return adapter;
    }


    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        getData(pageIndex, limit, isShowDialog);
    }

    /**
     * 全部的 adapter
     *
     * @param listData
     * @return
     */
    private BaseQuickAdapter getAllAdapter(List listData) {
        MessageSystemAdapter messageSystemAdapter = new MessageSystemAdapter(listData);
        messageSystemAdapter.setOnItemClickListener((adapter, view, position) -> {
            MessageSystemBean item = (MessageSystemBean) adapter.getItem(position);
//            WebViewMessageActivity.openContent(mActivity, "标题", "", "系统公告", "内容阿萨德and啊d");
            ZXPDActivity.open(mActivity);
        });
        return messageSystemAdapter;
    }

    /**
     * 系统公告adapter
     *
     * @param listData
     * @return
     */
    private BaseQuickAdapter getSystemAdapter(List listData) {
        MessageSystemAdapter messageSystemAdapter = new MessageSystemAdapter(listData);
        messageSystemAdapter.setOnItemClickListener((adapter, view, position) -> {
            MessageSystemBean.ListBean item = (MessageSystemBean.ListBean) adapter.getItem(position);
//            WebViewMessageActivity.openContent(mActivity, "标题", "", "系统公告", "内容阿萨德and啊d");
            WebViewMessageActivity.openContent(mActivity, item.getContent(), item.getCreateDatetime(), "系统公告", item.getContent());
//630053
//            ZXPDActivity.open(mActivity);
        });
        return messageSystemAdapter;
    }

    /**
     * 待办事项adapter
     *
     * @param listData
     * @return
     */
    private BaseQuickAdapter getTobeAdapter(List listData) {
        MessageTobeAdapter tobeAdapter = new MessageTobeAdapter(listData);
        tobeAdapter.setOnItemClickListener((adapter, view, position) -> {
            MessageTobeBean.ListBean item = (MessageTobeBean.ListBean) adapter.getItem(position);
            WebViewMessageActivity.openContent(mActivity, item.getContent(), item.getCreateDatetime(), "待办事项", item.getContent());
//            ZXPDActivity.open(mActivity);
        });
        return tobeAdapter;
    }


    private void getData(int pageIndex, int limit, boolean isShowDialog) {

        switch (type) {
            case "1":
                mRefreshHelper.setData(null, "暂无消息", 0);
                break;
            case "2":
                getTobeThing(pageIndex, limit);
                break;
            case "3":
                getSystemNotice(pageIndex, limit);
                break;
        }

    }

    /**
     * 获取待办数据
     *
     * @param pageIndex
     * @param limit
     */
    private void getTobeThing(int pageIndex, int limit) {
        Map<String, String> map = new HashMap<>();
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
        map.put("status", "0");//0 未完成 1已完成
        map.put("operator", SPUtilHelper.getUserId());
        Call<BaseResponseModel<MessageTobeBean>> tobeThing = RetrofitUtils.createApi(MyApiServer.class).getTobeThing("632525", StringUtils.getJsonToString(map));
        addCall(tobeThing);
        tobeThing.enqueue(new BaseResponseModelCallBack<MessageTobeBean>(mActivity) {
            @Override
            protected void onSuccess(MessageTobeBean data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无待办消息", 0);
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    /**
     * 获取系统公告
     *
     * @param pageIndex
     * @param limit
     */
    private void getSystemNotice(int pageIndex, int limit) {
        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("status", "1");//0 草稿 1 发送 2 撤回
        map.put("type", type);

        Call<BaseResponseModel<MessageSystemBean>> message = RetrofitUtils.createApi(MyApiServer.class).getMessage("805305", StringUtils.getJsonToString(map));
        addCall(message);
        message.enqueue(new BaseResponseModelCallBack<MessageSystemBean>(mActivity) {
            @Override
            protected void onSuccess(MessageSystemBean data, String SucMessage) {

                mRefreshHelper.setData(data.getList(), "暂无公告消息", 0);
            }

            @Override
            protected void onFinish() {

            }
        });
    }


}
