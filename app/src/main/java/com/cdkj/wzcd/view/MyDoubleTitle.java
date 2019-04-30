package com.cdkj.wzcd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.LayoutMyDoubleTitleBinding;

/**
 * @updateDts 2019/4/18
 */
public class MyDoubleTitle extends LinearLayout {

    private String minTitle;
    private String title;
    private boolean isTitleSingle;
    private LayoutMyDoubleTitleBinding mBinding;
    private Context context;

    public MyDoubleTitle(Context context) {
        this(context, null);
    }

    public MyDoubleTitle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDoubleTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyDoubleTitle, 0, 0);
        minTitle = typedArray.getString(R.styleable.MyDoubleTitle_txt_mintop_title);
        title = typedArray.getString(R.styleable.MyDoubleTitle_txt_title);
        isTitleSingle = typedArray.getBoolean(R.styleable.MyDoubleTitle_is_title_single, false);
        typedArray.recycle();//释放资源
        init(context);
        setData();
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mBinding.tvTitle.setText("");
        } else {
            mBinding.tvTitle.setText(title);
        }
    }

    public void setMinTitle(String minTitle) {
        if (TextUtils.isEmpty(minTitle)) {
            mBinding.tvMinTitle.setText("");
        } else {
            mBinding.tvMinTitle.setText(minTitle);
        }
    }




    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_my_double_title, this, true);
    }

    private void setData() {
        if (isTitleSingle) {
            mBinding.tvTitle.setSingleLine(true);
        }
        mBinding.tvTitle.setText(title);
        mBinding.tvMinTitle.setText(minTitle);
    }
}
