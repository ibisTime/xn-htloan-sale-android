package com.cdkj.wzcd.module.business.join_approval.step;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentJoinStep5Binding;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/7.
 */

public class JoinStep5Fragment extends BaseLazyFragment {

    private String code;
    private FragmentJoinStep5Binding mBinding;

    public static JoinStep5Fragment getInstance(String code) {
        JoinStep5Fragment fragment = new JoinStep5Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_SIGN, code);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_join_step5, null, false);

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
