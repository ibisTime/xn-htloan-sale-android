package com.cdkj.wzcd.module.business.credit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.CreditListAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.SearchModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.baselibrary.appmanager.CdRouteHelper.IS_FIRST_REQUEST;

/**
 * Created by cdkj on 2018/5/29.
 */

public class CreditListFragment extends AbsRefreshListFragment {

    private String isPass;
    private boolean isFirstRequest;

    // 业务种类
    private List<DataDictionary> mType = new ArrayList<>();

    private String searchKey = "";
    private String searchValue = "";

    /**
     * @param
     * @return
     */
    public static CreditListFragment getInstance(Boolean isFirstRequest, String isPass) {
        CreditListFragment fragment = new CreditListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, isPass);
        bundle.putBoolean(IS_FIRST_REQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    @Override
    protected void lazyLoad() {
        if (mRefreshBinding != null){
            mRefreshHelper.onDefaultMRefresh(true);
        }

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            isPass = getArguments().getString(DATA_SIGN);
            isFirstRequest = getArguments().getBoolean(IS_FIRST_REQUEST);

            initRefreshHelper(MyCdConfig.LIST_LIMIT);

            if (isFirstRequest){
                mRefreshHelper.onDefaultMRefresh(true);
            }

        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {

        CreditListAdapter mAdapter = new CreditListAdapter(listData, mType, (view, code) -> {
            showDoubleWarnListen("您确定要撤回此条征信调查单吗?", view1 -> {
                creditCancel(code);
            });
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CreditModel model = mAdapter.getItem(position);

            CreditDetailActivity.open(mActivity, model.getCode());
        });

        return mAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        DataDictionaryHelper.getDataDictionaryRequest(mActivity, DataDictionaryHelper.budget_orde_biz_typer, "", data -> {

            if (data == null || data.size() == 0){
                return;
            }

            mType.addAll(data);

            Map<String, Object> map = RetrofitUtils.getRequestMap();

            List<String> curNodeCodeList = new ArrayList<>();
            curNodeCodeList.add("001_01");
            curNodeCodeList.add("001_02");
            curNodeCodeList.add("001_03");
            curNodeCodeList.add("001_04");
            curNodeCodeList.add("001_05");
            curNodeCodeList.add("001_06");
            curNodeCodeList.add("001_07");

            map.put("curNodeCodeList", curNodeCodeList);
            map.put("roleCode", SPUtilHelper.getRoleCode());
            map.put("teamCode", SPUtilHelper.getTeamCode());
            map.put("userId", SPUtilHelper.getUserId());
            map.put("isPass", isPass);
            map.put("start", pageIndex + "");
            map.put("limit", limit + "");
            map.put(searchKey, searchValue);

            if (isShowDialog) showLoadingDialog();

            Call call = RetrofitUtils.createApi(MyApiServer.class).getCreditList("632115", StringUtils.getJsonToString(map));
            addCall(call);

            call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<CreditModel>>(mActivity) {
                @Override
                protected void onSuccess(ResponseInListModel<CreditModel> data, String SucMessage) {
                    mRefreshHelper.setData(data.getList(), "暂无资信", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();
                }
            });

        });

    }

    private void creditCancel(String code){

        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("code", code);
        map.put("operator", SPUtilHelper.getUserId());

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().successRequest("632114", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    UITipDialog.showSuccess(mActivity, "操作成功", dialogInterface -> {
                        mRefreshHelper.onDefaultMRefresh(true);
                    });
                } else {
                    UITipDialog.showFail(mActivity, "操作失败");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    @org.greenrobot.eventbus.Subscribe
    public void doSearch(SearchModel searchModel){

        if (searchModel == null)
            return;

        if (getUserVisibleHint()){
            searchKey = searchModel.getSearchKey();
            searchValue = searchModel.getSearchValue();

            mRefreshHelper.onDefaultMRefresh(true);
        }

    }

}
