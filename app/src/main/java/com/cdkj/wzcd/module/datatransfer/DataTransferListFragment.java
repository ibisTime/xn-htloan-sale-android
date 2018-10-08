package com.cdkj.wzcd.module.datatransfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsRefreshListFragment;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.adapter.DataTransferAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.CllhListBean;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.baselibrary.appmanager.CdRouteHelper.IS_FIRST_REQUEST;

public class DataTransferListFragment extends AbsRefreshListFragment<CllhListBean> {

    public static final String DATA_GPS = "gps";
    public static final String DATA_SEND = "send";
    public static final String DATA_OTHER = "other";

    private String dataType;
    private boolean isFirstRequest;

    private List<DataDictionary> mCompany = new ArrayList<>();

    /**
     * @param
     * @return
     */
    public static DataTransferListFragment getInstance(Boolean isFirstRequest, String dataType) {
        DataTransferListFragment fragment = new DataTransferListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, dataType);
        bundle.putBoolean(IS_FIRST_REQUEST, isFirstRequest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            mRefreshHelper.onDefaultMRefresh(true);
        }
    }

    @Override
    protected void lazyLoad() {
        if (mRefreshBinding != null) {
            mRefreshHelper.onDefaultMRefresh(true);
        }

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void afterCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            dataType = getArguments().getString(DATA_SIGN);
            isFirstRequest = getArguments().getBoolean(IS_FIRST_REQUEST);

            initRefreshHelper(MyCdConfig.LIST_LIMIT);

            if (isFirstRequest) {
                mRefreshHelper.onDefaultMRefresh(true);
            }

        }
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        DataTransferAdapter mAdapter;
        if (DATA_GPS.equals(dataType)) {
            mAdapter = new DataTransferAdapter(listData, mCompany, true);
        } else {
            mAdapter = new DataTransferAdapter(listData, mCompany);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DataTransferModel item = (DataTransferModel) adapter.getItem(position);
            if (DATA_GPS.equals(dataType)) {
                DataDetialsActivity.open(mActivity, item.getCode(), mCompany, true);
            } else {
                DataDetialsActivity.open(mActivity, item.getCode(), mCompany, false);
            }

        });
        return mAdapter;
    }


    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {
        DataDictionaryHelper.getDataDictionaryRequest(mActivity, DataDictionaryHelper.kd_company, "", data -> {

            if (data == null || data.size() == 0)
                return;

            mCompany.addAll(data);

            Map<String, Object> map = new HashMap<>();

            List<String> statusList = new ArrayList<>();

            if (TextUtils.equals(dataType, DATA_SEND)) {
//                map.put("userId", SPUtilHelper.getUserId());  //这个地方有问题  传userid   就看不到其他的数据了  不传就能看到所有的数据 了
//                statusList.add("0");//资料发件显示所有状态
                ArrayList<String> typeList = new ArrayList<>();
                typeList.add("1");
                typeList.add("3");
                map.put("typeList", typeList);

            } else if (TextUtils.equals(dataType, DATA_OTHER)) {
//                map.put("receiver", "0");
                statusList.add("1");
                statusList.add("2");
                statusList.add("3");

            } else {
                map.put("receiver", SPUtilHelper.getUserId());
                statusList.add("1");
                statusList.add("2");
                statusList.add("3");
            }

            if (!TextUtils.equals(dataType, DATA_SEND)) {
                map.put("statusList", statusList);

            }
            map.put("start", pageIndex + "");
            map.put("limit", limit + "");

            if (isShowDialog) showLoadingDialog();

            Call call = RetrofitUtils.createApi(MyApiServer.class).getDataTransfer("632155", StringUtils.getJsonToString(map));
            addCall(call);

            call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<DataTransferModel>>(mActivity) {
                @Override
                protected void onSuccess(ResponseInListModel<DataTransferModel> data, String SucMessage) {
                    mRefreshHelper.setData(data.getList(), "暂无资料", 0);
                }

                @Override
                protected void onFinish() {
                    disMissLoading();
                }
            });
        });
    }


}
