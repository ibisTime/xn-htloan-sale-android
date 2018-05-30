package com.cdkj.wzcd.view.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.LayoutMyItemNormalBinding;

/**
 * Created by cdkj on 2018/5/29.
 */

public class MyItemNormalLayout extends LinearLayout {

    private Context context;
    private LayoutMyItemNormalBinding mBinding;

    private String txtTitle;
    private String txtContent;

    public MyItemNormalLayout(Context context) {
        this(context, null);
    }

    public MyItemNormalLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyItemNormalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyItemNormalLayout, 0, 0);
        txtTitle = typedArray.getString(R.styleable.MyItemNormalLayout_txt_item_normal_title);
        txtContent = typedArray.getString(R.styleable.MyItemNormalLayout_txt_item_normal_content);

        typedArray.recycle();

        init(context);
        setData();
    }

    private void setData() {
        mBinding.tvTitle.setText(txtTitle);
        if (!TextUtils.isEmpty(txtContent))
            mBinding.tvContent.setText(txtContent);
    }

    public void setTitle(String title){
        mBinding.tvTitle.setText(title);
    }

    public void setContext(String context){
        mBinding.tvContent.setText(context);
    }

    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_my_item_normal, this, true);

    }
}
