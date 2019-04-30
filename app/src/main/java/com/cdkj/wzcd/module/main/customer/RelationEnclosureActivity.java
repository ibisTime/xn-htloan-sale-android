package com.cdkj.wzcd.module.main.customer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityRelationEnclosureBinding;

public class RelationEnclosureActivity extends AbsBaseLoadActivity {
    private ActivityRelationEnclosureBinding mBinding;

    public static void open(Context context) {
        Intent intent = new Intent(context, RelationEnclosureActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_relation_enclosure, null, false);
        mBaseBinding.titleView.setMidTitle("添加关联");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
