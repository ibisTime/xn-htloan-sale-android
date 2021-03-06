package com.cdkj.wzcd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.LayoutMyNormalBinding;

/**
 * Created by cdkj on 2018/5/29.
 */

public class MyNormalLayout extends LinearLayout {

    private Context context;
    private LayoutMyNormalBinding mBinding;

    private String txtTitle;
    private String txtHint;
    private String txtContent;
    private int resourceId;
    private boolean isShowRightImg;

    public MyNormalLayout(Context context) {
        this(context, null);
    }

    public MyNormalLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNormalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyNormalLayout, 0, 0);
        txtTitle = typedArray.getString(R.styleable.MyNormalLayout_txt_normal_title);
        txtHint = typedArray.getString(R.styleable.MyNormalLayout_txt_normal_hint);
        txtContent = typedArray.getString(R.styleable.MyNormalLayout_txt_normal_content);
        isShowRightImg = typedArray.getBoolean(R.styleable.MyNormalLayout_is_show_right_img, false);
        resourceId = typedArray.getResourceId(R.styleable.MyNormalLayout_img_normal_right, 0);

        typedArray.recycle();

        init(context);
        setData();
    }

    public void setTvTitle(String txtTitle) {
        this.txtTitle = txtTitle;
        mBinding.tvTitle.setText(txtTitle);
    }

    private void setData() {
        mBinding.tvTitle.setText(txtTitle);
        mBinding.tvContent.setHint(txtHint);
        mBinding.tvContent.setText(txtContent);
        if (isShowRightImg) {
            mBinding.ivRight.setVisibility(VISIBLE);
        }
        if (resourceId != 0) {
            mBinding.ivRight.setImageResource(resourceId);
            mBinding.ivRight.setVisibility(VISIBLE);
        }
    }


    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_my_normal, this, true);
    }

    public void setText(String content) {
        if (!TextUtils.isEmpty(content))
            mBinding.tvContent.setText(content);
    }

    public String check() {

        if (TextUtils.isEmpty(mBinding.tvContent.getText().toString().trim())) {
            ToastUtil.show(context, mBinding.tvContent.getHint().toString());
            return "";
        }

        return mBinding.tvContent.getText().toString();
    }

    public String getText() {
        return mBinding.tvContent.getText().toString();
    }

    public String getTags() {
        return mBinding.tvContent.getTag().toString();
    }

}
