package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.ZRDDetialsAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityZrddetialsBinding;
import com.cdkj.wzcd.model.ZRDDetialsBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.zxdxq.ApplicantFragment4;
import com.cdkj.wzcd.module.main.customer.zxdxq.BankLendingFragment10;
import com.cdkj.wzcd.module.main.customer.zxdxq.EnclosureFragment16;
import com.cdkj.wzcd.module.main.customer.zxdxq.FBHFragment12;
import com.cdkj.wzcd.module.main.customer.zxdxq.FaceSignatureFragment8;
import com.cdkj.wzcd.module.main.customer.zxdxq.FinanceFragment9;
import com.cdkj.wzcd.module.main.customer.zxdxq.GPSInstallFragment13;
import com.cdkj.wzcd.module.main.customer.zxdxq.LoanVehicleFragment3;
import com.cdkj.wzcd.module.main.customer.zxdxq.LogFragment14;
import com.cdkj.wzcd.module.main.customer.zxdxq.OtherBasicDetialsFragment6;
import com.cdkj.wzcd.module.main.customer.zxdxq.RepaymentListFragment15;
import com.cdkj.wzcd.module.main.customer.zxdxq.SpouseDetialsFragment7;
import com.cdkj.wzcd.module.main.customer.zxdxq.VehicleMortgageFragment11;
import com.cdkj.wzcd.module.main.customer.zxdxq.WorkDetialsFragment5;
import com.cdkj.wzcd.module.main.customer.zxdxq.ZXDFragment1;
import com.cdkj.wzcd.module.main.customer.zxdxq.ZXLBFragment2;
import com.cdkj.wzcd.util.BizTypeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 准入单详情
 */
public class ZRDDetialsActivity extends AbsBaseLoadActivity {

    private ActivityZrddetialsBinding mBinding;
    private ArrayList<ZRDDetialsBean> titleData;
    private ArrayList<Fragment> fragments;
    public ZXDetialsBean.ListBean currentItem;
    public ArrayList<DataDictionary> dataDictionaryList;
    private String currentCode;
    //
//    public static void open(Context context) {
//        Intent intent = new Intent(context, ZRDDetialsActivity.class);
//        context.startActivity(intent);
//    }

    public static void open(Context context, ZXDetialsBean.ListBean currentItem) {
        Intent intent = new Intent(context, ZRDDetialsActivity.class);
        intent.putExtra("item", currentItem);
        context.startActivity(intent);
    }

    public static void open(Context context, String code) {
        Intent intent = new Intent(context, ZRDDetialsActivity.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_zrddetials, null, false);
        mBaseBinding.titleView.setMidTitle("征信单详情");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            currentItem = (ZXDetialsBean.ListBean) getIntent().getSerializableExtra("item");
            currentCode = getIntent().getStringExtra("code");
            if (currentItem != null) {
                initViewPager();
                initRecyclerView();
            } else {
                getData();
            }
        }
        dataDictionaryList = BizTypeHelper.getParentList(BizTypeHelper.cdbiz_status);

    }

    /**
     * 通过code 获取的数据
     */
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SPUtilHelper.getUserToken());
        map.put("code", currentCode);

        Call<BaseResponseModel<ZXDetialsBean.ListBean>> call = RetrofitUtils.createApi(MyApiServer.class).getCredit3("632117", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<ZXDetialsBean.ListBean>(this) {
            @Override
            protected void onSuccess(ZXDetialsBean.ListBean data, String SucMessage) {
                currentItem = data;
                initViewPager();
                initRecyclerView();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 初始化  左侧的 标题
     */
    private void initRecyclerView() {
        titleData = new ArrayList<>();
        titleData.add(new ZRDDetialsBean("基本信息", true));
        titleData.add(new ZRDDetialsBean("征信列表"));
        titleData.add(new ZRDDetialsBean("贷款车辆信息"));
        titleData.add(new ZRDDetialsBean("申请人基本信息"));
        titleData.add(new ZRDDetialsBean("工作情况"));
        titleData.add(new ZRDDetialsBean("其他基本资料"));
        titleData.add(new ZRDDetialsBean("配偶信息"));
        titleData.add(new ZRDDetialsBean("面签"));
        titleData.add(new ZRDDetialsBean("财务垫资"));
        titleData.add(new ZRDDetialsBean("银行放款"));
        titleData.add(new ZRDDetialsBean("车辆抵押"));
        titleData.add(new ZRDDetialsBean("发保合"));
        titleData.add(new ZRDDetialsBean("GPS安装列表"));
        titleData.add(new ZRDDetialsBean("流转日志"));
        titleData.add(new ZRDDetialsBean("还款计划"));
        titleData.add(new ZRDDetialsBean("附件池"));

        mBinding.tvTitle.setText(titleData.get(0).getTitle());//设置默认的标题

        mBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ZRDDetialsAdapter zrdDetialsAdapter = new ZRDDetialsAdapter(titleData);
        mBinding.rv.setAdapter(zrdDetialsAdapter);
        zrdDetialsAdapter.setOnItemClickListener((adapter, view, position) -> {
            //切换右侧的 fragment
            if (position <= fragments.size() - 1) {
                mBinding.viewpager.setCurrentItem(position, false);//切换界面  去掉默认的切换动画的
            }
            for (int i = 0; i < titleData.size(); i++) {
                ZRDDetialsBean zrdDetialsBean = titleData.get(i);
                if (position == i) {
                    mBinding.tvTitle.setText(zrdDetialsBean.getTitle());//设置标题
                    zrdDetialsBean.setSelect(true);
                    adapter.notifyItemChanged(i);
                } else {
                    //做了优化不是每次都刷新全部的列表刷新  上一个选中的  和本次选中的条目
                    if (zrdDetialsBean.isSelect()) {
                        zrdDetialsBean.setSelect(false);
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(ZXDFragment1.getInstance());//基本信息
        fragments.add(ZXLBFragment2.getInstance());//征信列表
        fragments.add(LoanVehicleFragment3.getInstance());//贷款车辆信息
        fragments.add(ApplicantFragment4.getInstance());//申请人基本信息
        fragments.add(WorkDetialsFragment5.getInstance());//工作情况
        fragments.add(OtherBasicDetialsFragment6.getInstance());//其他基本信息
        fragments.add(SpouseDetialsFragment7.getInstance());//配偶信息
        fragments.add(FaceSignatureFragment8.getInstance());//面签
        fragments.add(FinanceFragment9.getInstance());//财务垫资
        fragments.add(BankLendingFragment10.getInstance());//银行放款
        fragments.add(VehicleMortgageFragment11.getInstance());//车辆抵押
        fragments.add(FBHFragment12.getInstance());//发保合
        fragments.add(GPSInstallFragment13.getInstance());//gps安装列表
        fragments.add(LogFragment14.getInstance());//流转日志
        fragments.add(RepaymentListFragment15.getInstance());//还款计划
        fragments.add(EnclosureFragment16.getInstance());//附件池

        mBinding.viewpager.setPagingEnabled(false);
        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpager.setCurrentItem(0, false);
    }

}
