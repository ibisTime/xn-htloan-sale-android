package com.cdkj.wzcd.module.business.join_approval.step;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.FragmentJoinStep1Binding;
import com.cdkj.wzcd.model.LoanProductModel;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.util.AdvanceFundHelper;
import com.cdkj.wzcd.view.MySelectLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;
import static com.cdkj.wzcd.util.DataDictionaryHelper.budget_orde_biz_typer;
import static com.cdkj.wzcd.util.DataDictionaryHelper.loan_period;

/**
 * Created by cdkj on 2018/6/7.
 */

public class JoinStep1Fragment extends BaseLazyFragment {

    private String code;
    private FragmentJoinStep1Binding mBinding;

    // 贷款产品
    private List<DataDictionary> mLoanProduct;

    public static JoinStep1Fragment getInstance(String code) {
        JoinStep1Fragment fragment = new JoinStep1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, code);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_join_step1, null, false);

        if (getArguments() != null) {
            code = getArguments().getString(DATA_SIGN);
        }

        getNode();

        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    public void getNode(){
        Map<String, String> map = RetrofitUtils.getRequestMap();

        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getNode("632146", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<NodeListModel>(mActivity) {
            @Override
            protected void onSuccess(NodeListModel data, String SucMessage) {

                setView(data);

                getLoanProduct();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(NodeListModel data) {
        mBinding.myNlName.setText(data.getApplyUserName());
        mBinding.myNlCode.setText(data.getCode());
//        mBinding.myNlBank.setText(data.getLoanBankName());

    }

    /**
     * 获取银行卡渠道
     */
    private void getLoanProduct() {
        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("status", "2");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getLoanProduct("632177", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<LoanProductModel>(mActivity) {

            @Override
            protected void onSuccess(List<LoanProductModel> data, String SucMessage) {

                if (data == null)
                    return;

                for (LoanProductModel model : data){
                    mLoanProduct.add(new DataDictionary().setDkey(model.getCode()).setDvalue(model.getName()));
                }

                mBinding.mySlLoanProduct.setData(mLoanProduct, null);

                initCustomView();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 需要初始化的布局：
     * 下拉框布局SelectLayout
     */
    private void initCustomView() {

        mBinding.mySlAdvanceFund.setData(new AdvanceFundHelper().getAdvanceMapFoundList(), null);
        mBinding.mySlBizType.setData(mActivity, MySelectLayout.DATA_DICTIONARY, budget_orde_biz_typer,null);
        mBinding.mySlLoanPeriod.setData(mActivity, MySelectLayout.DATA_DICTIONARY, loan_period,null);

    }
}
