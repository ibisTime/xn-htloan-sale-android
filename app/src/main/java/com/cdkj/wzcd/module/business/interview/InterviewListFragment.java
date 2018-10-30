package com.cdkj.wzcd.module.business.interview;

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
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.InterviewListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.HeadGpsCheckBinding;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.PickerViewDataBean;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.UserHelper;

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
    private HeadGpsCheckBinding headView;
    private ArrayList<PickerViewDataBean> typeDatas;
    private int selectFrist;

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
//        initTitelAndHead();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){

            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    private void initTitelAndHead() {
        headView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_gps_check, null, false);
        mRefreshBinding.llHead.addView(headView.getRoot());
        typeDatas = new ArrayList<>();
        PickerViewDataBean bean1 = new PickerViewDataBean();
        bean1.setKey("未面签");
        bean1.setValue("0");
        PickerViewDataBean bean2 = new PickerViewDataBean();
        bean2.setKey("已面签");
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
    public RecyclerView.Adapter getListAdapter(List listData) {
        InterviewListAdapter interviewListAdapter = new InterviewListAdapter(listData, mType);
        interviewListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            InterviewStartActivity.open(mActivity, interviewListAdapter.getItem(position).getCode());
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

            Map<String, Object> map = RetrofitUtils.getNodeListMap();

            List<String> curNodeCodeList = new ArrayList<>();
            curNodeCodeList.add("002_05");
            curNodeCodeList.add("002_06");
            curNodeCodeList.add("002_08");

            map.put("curNodeCodeList", curNodeCodeList);
            map.put("limit", limit + "");
            map.put("start", pageIndex + "");
            map.put("userId", SPUtilHelper.getUserId());

            if (!UserHelper.isZHRY()) {
//                map.put("saleUserId", SPUtilHelper.getUserId());
                map.put("teamCode", SPUtilHelper.getTeamCode());
            }

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
