package com.cdkj.wzcd.module.business.credit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.BankModel;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adpter.CreditPersonAdapter;
import com.cdkj.wzcd.databinding.ActivityZxLaunchBinding;
import com.cdkj.wzcd.model.CreditPersonModel;
import com.cdkj.wzcd.model.MySelectModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 发起征信
 * Created by cdkj on 2018/5/29.
 */

public class CreditInitiateActivity extends AbsBaseLoadActivity {

    private ActivityZxLaunchBinding mBinding;

    // 银行
    private List<MySelectModel> mMapBank;
    // 购车途径
    private List<DataDictionary> mMapWay;
    // 行驶证正面
    private String mDocPositive;
    // 行驶证反面
    private String mDocNegative;

    private CreditPersonAdapter mAdapter;
    private List<CreditPersonModel> mList = new ArrayList<>();

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CreditInitiateActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zx_launch, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        mBaseBinding.titleView.setMidTitle("发起征信");

        init();
        initListener();

//        getBank();
        initCustomView();
        initListAdapter();
    }

    private void init() {
        mMapWay = new ArrayList<>();
        mMapBank = new ArrayList<>();
    }

    private void initListener() {
        mBinding.llAdd.setOnClickListener(view -> {
            CreditPersonAddActivity.open(this);
        });
    }

    /**
     * 获取银行卡渠道
     */
    private void getBank() {
        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("bankCode", "");
        map.put("bankName", "");
        map.put("channelType", "");
        map.put("status", "");
        map.put("paybank", "");

        Call call = RetrofitUtils.getBaseAPiService().getBackModel("802116", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BankModel>(this) {

            @Override
            protected void onSuccess(List<BankModel> data, String SucMessage) {

                if (data == null)
                    return;

                for (BankModel model : data){
                    mMapBank.add(new MySelectModel().setKey(model.getBankName()).setValue(model.getBankCode()));
                }

                initCustomView();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    private void initCustomView() {

//        mBinding.mySlBank.setActivity(mMapBank);

        mMapWay.add(new DataDictionary().setDkey("1").setDvalue("新车"));
        mMapWay.add(new DataDictionary().setDkey("2").setDvalue("二手车"));

        mBinding.mySlWay.setData(mMapWay, (dialog, which) -> {
            // 新车则隐藏证件
            mBinding.myIlDocuments.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
        });

        mBinding.myIlDocuments.setActivity(this,1,2);
    }

    public void initListAdapter() {
        mAdapter = new CreditPersonAdapter(mList);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CreditPersonModel model = mAdapter.getItem(position);
        });

        mBinding.rvZxr.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvZxr.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(CameraHelper.staticPath);
        showLoadingDialog();
        new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {

                if (requestCode == mBinding.myIlDocuments.getRequestCode()){
                    mDocPositive = key;
                    mBinding.myIlDocuments.setFlImgImageView(mDocPositive);
                }else {
                    mDocNegative = key;
                    mBinding.myIlDocuments.setFlImgRightImageView(mDocNegative);
                }

                disMissLoading();
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
            }
        }, path);
    }

    @Subscribe
    public void doAddCreditPerson(CreditPersonModel model){

        mList.add(model);

        mAdapter.notifyDataSetChanged();

    }

}
