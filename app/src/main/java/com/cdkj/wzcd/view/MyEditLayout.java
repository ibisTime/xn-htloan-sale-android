package com.cdkj.wzcd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.LayoutMyInputHorizontalBinding;

/**
 * Created by cdkj on 2018/5/29.
 */

public class MyEditLayout extends LinearLayout {

    private LayoutMyInputHorizontalBinding mBinding;
    private Context context;

    private String txtHint;
    private String txtTitle;
    private String txtTitleRight;
    private String inputType;

    public MyEditLayout(Context context) {
        this(context, null);
    }

    public MyEditLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEditLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditLayout, 0, 0);
        txtHint = typedArray.getString(R.styleable.MyEditLayout_txt_input_hint);
        txtTitle = typedArray.getString(R.styleable.MyEditLayout_txt_input_title);
        txtTitleRight = typedArray.getString(R.styleable.MyEditLayout_txt_input_title_right);
        inputType = typedArray.getString(R.styleable.MyEditLayout_inputType);

        typedArray.recycle();

        init(context);
        setData();
    }

    private void setData() {
        mBinding.tvTitle.setText(txtTitle);
        mBinding.tvTitleRight.setText(txtTitleRight);
        mBinding.tvTitleRight.setVisibility(!TextUtils.isEmpty(txtTitleRight)? VISIBLE : GONE);


        if (!TextUtils.isEmpty(txtHint))
            mBinding.edtInput.setHint(txtHint);

        if (!TextUtils.isEmpty(inputType)){

            switch (inputType){
                case "0":
                    mBinding.edtInput.setInputType(InputType.TYPE_CLASS_PHONE);
                    InputFilter[] filtersPhone = {new InputFilter.LengthFilter(11)};
                    mBinding.edtInput.setFilters(filtersPhone);
                    break;

                case "1":
                    mBinding.edtInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    InputFilter[] filtersId = {new InputFilter.LengthFilter(18)};
                    mBinding.edtInput.setFilters(filtersId);
                    break;

                default:
                    mBinding.edtInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
            }
        }
    }


    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_my_input_horizontal, this, true);

    }

    public String getText(){

        if (TextUtils.isEmpty(mBinding.edtInput.getText().toString().trim())){
            ToastUtil.show(context, mBinding.edtInput.getHint().toString());
            return null;
        }

        return mBinding.edtInput.getText().toString();
    }

    public void setText(String content){
        mBinding.edtInput.setText(content);
    }
}