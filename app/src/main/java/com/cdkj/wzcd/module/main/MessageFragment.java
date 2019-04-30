package com.cdkj.wzcd.module.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentMessageBinding;
import com.cdkj.wzcd.module.main.message.MessageItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息Fragment
 */
public class MessageFragment extends BaseLazyFragment {


    private FragmentMessageBinding mBinding;

    public static Fragment getInstance() {
        MessageFragment homeFragment = new MessageFragment();
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_message, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        List<String> titls = new ArrayList<>();
        titls.add("全部");
        titls.add("待办事项");
        titls.add("系统公告");
        mBinding.viewindicator.setTabItemTitles(titls);
        mBinding.viewindicator.setViewPager(mBinding.viewpager, 0);
        mBinding.viewindicator.setmLinWidth(25);
        mBinding.viewindicator.setVisibleTabCount(titls.size());

        ArrayList fragments = new ArrayList<>();

        fragments.add(MessageItemFragment.getInstance("1"));
        fragments.add(MessageItemFragment.getInstance("2"));
        fragments.add(MessageItemFragment.getInstance("3"));

        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));
        mBinding.viewpager.setOffscreenPageLimit(fragments.size());
        mBinding.viewpager.setPagingEnabled(true);
    }


}
