package com.cdkj.wzcd.module.business.join_approval.step;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentJoinStep2Binding;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/7.
 */

public class JoinStep2Fragment extends BaseLazyFragment {

    private String code;
    private FragmentJoinStep2Binding mBinding;

    public static JoinStep2Fragment getInstance(String code) {
        JoinStep2Fragment fragment = new JoinStep2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, code);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_join_step2, null, false);

        if (getArguments() != null) {
            code = getArguments().getString(DATA_SIGN);
        }

        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }
}
