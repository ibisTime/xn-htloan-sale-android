package com.cdkj.wzcd.module.business.interview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.InterviewListAdapter2;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.HeadGpsCheckBinding;
import com.cdkj.wzcd.model.PickerViewDataBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

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
        if (getUserVisibleHint()) {

            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    private void initTitelAndHead() {
        headView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_gps_check, null, false);
        mRefreshBinding.llHead.addView(headView.getRoot());
        typeDatas = new ArrayList<>();
        PickerViewDataBean bean1 = new PickerViewDataBean();
        bean1.setKey("未录入");
        bean1.setValue("0");
        PickerViewDataBean bean2 = new PickerViewDataBean();
        bean2.setKey("已录入");
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
        InterviewListAdapter2 interviewListAdapter = new InterviewListAdapter2(listData);
        interviewListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            InterviewStartActivity.open(mActivity, interviewListAdapter.getItem(position).getCode());
//            CreditDetailActivity.open(mActivity,interviewListAdapter.getItem(position).getCode());
            ZXDetialsBean.ListBean item = interviewListAdapter.getItem(position);
            ZRDDetialsActivity.open(mActivity, item);
        });
        return interviewListAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        Map<String, Object> map = RetrofitUtils.getNodeListMap();
        List<String> curNodeCodeList = new ArrayList<>();
//        b01","b02","b03","b01x"
        curNodeCodeList.add("b01");
//        curNodeCodeList.add("b02");
//        curNodeCodeList.add("b03");
        curNodeCodeList.add("b01x");
        map.put("intevCurNodeCodeList", curNodeCodeList);
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("userId", SPUtilHelper.getUserId());
        map.put("teamCode", SPUtilHelper.getTeamCode());

        if (isShowDialog) showLoadingDialog();

        Call<BaseResponseModel<ZXDetialsBean>> nodeList = RetrofitUtils.createApi(MyApiServer.class).getCreditList2("632148", StringUtils.getJsonToString(map));
        addCall(nodeList);

        nodeList.enqueue(new BaseResponseModelCallBack<ZXDetialsBean>(mActivity) {
            @Override
            protected void onSuccess(ZXDetialsBean data, String SucMessage) {
                mRefreshHelper.setData(data.getList(), "暂无面签记录", 0);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
