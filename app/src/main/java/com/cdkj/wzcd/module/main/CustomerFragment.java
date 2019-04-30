package com.cdkj.wzcd.module.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.interfaces.BaseRefreshCallBack;
import com.cdkj.baselibrary.interfaces.RefreshHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.CustomerFragmentAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.FragmentCustomerBinding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;


/**
 * 客户fragment
 */
public class CustomerFragment extends BaseLazyFragment {

    private FragmentCustomerBinding mBinding;
    private RefreshHelper mRefreshHelper;

    public static Fragment getInstance() {
        CustomerFragment homeFragment = new CustomerFragment();
        return homeFragment;
    }

    @Override
    protected void lazyLoad() {


    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_customer, container, false);
        getTypeData();

        return mBinding.getRoot();
    }

    /**
     * 获取数据字典  条目展示的时候用到
     */
    private void getTypeData() {

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRefreshHelper = new RefreshHelper(mActivity, new BaseRefreshCallBack<ZXDetialsBean.ListBean>(mActivity) {

            @Override
            public View getRefreshLayout() {
                return mBinding.refreshLayout;
            }

            @Override
            public RecyclerView getRecyclerView() {
                return mBinding.rv;
            }

            @Override
            public RecyclerView.Adapter getAdapter(List<ZXDetialsBean.ListBean> listData) {
                CustomerFragmentAdapter customerFragmentAdapter = new CustomerFragmentAdapter(listData);
                customerFragmentAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ZXDetialsBean.ListBean item = (ZXDetialsBean.ListBean) adapter.getItem(position);
//                    CustomerDetailsActivity.open(mActivity, item);
                    ZRDDetialsActivity.open(mActivity,item);
                });
                return customerFragmentAdapter;
            }

            @Override
            public void getListDataRequest(int pageIndex, int limit, boolean isShowDialog) {
                getData(pageIndex, limit, isShowDialog);
            }
        });
        mRefreshHelper.init(10);
        mRefreshHelper.onDefaultMRefresh(true);
    }

    private void getData(int pageIndex, int limit, boolean isShowDialog) {

        if (pageIndex == 1) {
            showLoadingDialog();
        }

        Map<String, String> map = new HashMap<>();
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
//        map.put("ywyUser", SPUtilHelper.getUserId());
        Call<BaseResponseModel<ZXDetialsBean>> customerData = RetrofitUtils.createApi(MyApiServer.class).getCustomerData("632515", StringUtils.getJsonToString(map));
        customerData.enqueue(new BaseResponseModelCallBack<ZXDetialsBean>(mActivity) {
            @Override
            protected void onSuccess(ZXDetialsBean data, String SucMessage) {

                mRefreshHelper.setData(data.getList(), "暂无客户数据", 0);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
