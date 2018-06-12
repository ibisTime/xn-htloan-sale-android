package com.cdkj.wzcd.module.business.join_approval;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityJoinApplyBinding;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep1Fragment;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep2Fragment;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep3Fragment;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep4Fragment;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep5Fragment;
import com.cdkj.wzcd.module.business.join_approval.step.JoinStep6Fragment;

import java.util.ArrayList;
import java.util.List;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/7.
 */

public class JoinApplyActivity extends AbsBaseLoadActivity {

    private ActivityJoinApplyBinding mBinding;

    private String code;

    private int pageIndex = 0;

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, JoinApplyActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_join_apply, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initViewPager();
        initListener();

        if(getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);

        setCurrent(pageIndex);
    }


    private void initViewPager() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(JoinStep1Fragment.getInstance(code));
        fragments.add(JoinStep2Fragment.getInstance(code));
        fragments.add(JoinStep3Fragment.getInstance(code));
        fragments.add(JoinStep4Fragment.getInstance(code));
        fragments.add(JoinStep5Fragment.getInstance(code));
        fragments.add(JoinStep6Fragment.getInstance(code));

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        mBinding.vpStep.setAdapter(mAdapter);
    }

    private void initListener() {
        mBaseBinding.titleView.setLeftFraClickListener(view -> {
            if (pageIndex == 0){
                finish();
            }else {
                pageIndex -=1;
                setCurrent(pageIndex);
            }
        });

        mBaseBinding.titleView.setRightFraClickListener(view -> {
            if (pageIndex != 5){
                pageIndex +=1;
                setCurrent(pageIndex);
            }
        });

        mBinding.vpStep.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.E("onPageSelected="+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setCurrent(int current){
        pageIndex = current;

        mBinding.vpStep.setCurrentItem(current);
        changeTitleView(current);
    }

    private void changeTitleView(int current){

        switch (current){

            case 0:
                mBaseBinding.titleView.setLeftTitle(null);
                mBaseBinding.titleView.setLeftImg(com.cdkj.baselibrary.R.drawable.back_img);
                mBaseBinding.titleView.setMidTitle("拟购车辆信息");
                mBaseBinding.titleView.setRightTitle("下一步");

                break;

            case 1:
                mBaseBinding.titleView.setLeftImg(0);
                mBaseBinding.titleView.setLeftTitle("上一步");
                mBaseBinding.titleView.setMidTitle("申请人信息");
                mBaseBinding.titleView.setRightTitle("下一步");

                break;

            case 2:
                mBaseBinding.titleView.setLeftImg(0);
                mBaseBinding.titleView.setLeftTitle("上一步");
                mBaseBinding.titleView.setMidTitle("配偶信息");
                mBaseBinding.titleView.setRightTitle("下一步");

                break;

            case 3:
                mBaseBinding.titleView.setLeftImg(0);
                mBaseBinding.titleView.setLeftTitle("上一步");
                mBaseBinding.titleView.setMidTitle("担保人信息");
                mBaseBinding.titleView.setRightTitle("下一步");

                break;

            case 4:
                mBaseBinding.titleView.setLeftImg(0);
                mBaseBinding.titleView.setLeftTitle("上一步");
                mBaseBinding.titleView.setMidTitle("流水信息");
                mBaseBinding.titleView.setRightTitle("下一步");

                break;

            case 5:
                mBaseBinding.titleView.setLeftImg(0);
                mBaseBinding.titleView.setLeftTitle("上一步");
                mBaseBinding.titleView.setMidTitle("其他材料");
                mBaseBinding.titleView.setRightImg(0);

                break;

        }

    }

}
