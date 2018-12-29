package com.cdkj.wzcd.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.activitys.FileSelectActivity;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.MyFileMultipleAdapter;
import com.cdkj.wzcd.databinding.LayoutMyMultipleBinding;
import com.cdkj.wzcd.model.MultipleModel;
import com.cdkj.wzcd.view.manager.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static com.cdkj.baselibrary.utils.StringUtils.splitAsPicList;

/**
 * Created by cdkj on 2018/6/26.
 */

public class MyFileMultipleLayout extends LinearLayout {

    private LayoutMyMultipleBinding mBinding;

    private Context context;

    private Activity mActivity;

    private int mRequestCode;
    private String tvTitle;

    public static String ADD = "add";

    private MyFileMultipleAdapter adapter;
    private List<MultipleModel> mList = new ArrayList<>();
    private int position = -1;

    public MyFileMultipleLayout(Context context) {
        this(context, null);
    }

    public MyFileMultipleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFileMultipleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyFileMultipleLayout, 0, 0);
//        tvTitle = typedArray.getString(R.styleable.title);
        tvTitle = typedArray.getString(R.styleable.MyFileMultipleLayout_title);
        init(context);
        setData();
    }

    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_my_multiple, this, true);
    }


    private void setData() {
        mBinding.tvTitle.setText(tvTitle);
    }

    public void setTitle(String title) {
        mBinding.tvTitle.setText(title);
    }

    public void build(Activity activity, int requestCode) {
        mActivity = activity;
        mRequestCode = requestCode;
        mList.clear();
        mList.add(new MultipleModel().setUrl(ADD));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        mBinding.rvMultiple.setLayoutManager(manager);
        mBinding.rvMultiple.setNestedScrollingEnabled(false);
        adapter = new MyFileMultipleAdapter(mList, listener);
        mBinding.rvMultiple.setAdapter(adapter);

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            this.position = position;
            FileSelectActivity.launch(mActivity, mRequestCode, false);
        });
    }

    public void addList(String url) {
        if (position > 0) {
            mList.set(position, new MultipleModel().setUrl(url));
        } else {
            mList.add(new MultipleModel().setUrl(url));
        }
        adapter.notifyDataSetChanged();
    }

    public void setListData(String url) {
        mList.clear();
        mList.add(new MultipleModel().setUrl(ADD));
        for (String str : splitAsPicList(url)) {
            mList.add(new MultipleModel().setUrl(str));
        }
        adapter.notifyDataSetChanged();
    }

    public void setListDataByRequest(String url) {
        mList.clear();
        for (String str : splitAsPicList(url)) {
            mList.add(new MultipleModel().setUrl(str).setCanEdit(false));
        }
        adapter.notifyDataSetChanged();
    }

    public String getListData() {
        List<String> urlList = new ArrayList<>();
        if (mList.size() > 0) {
            for (MultipleModel model : mList) {

                if (!TextUtils.equals(model.getUrl(), ADD)) {

                    urlList.add(model.getUrl());
                }
            }
        }

        LogUtil.E("getListData = " + urlList.size());
        LogUtil.E("String = " + StringUtils.listToString(urlList, "||"));
        return StringUtils.listToString(urlList, "||");
    }

    public boolean check() {
        List<String> urlList = new ArrayList<>();
        if (mList.size() == 0) {
            return true;
        } else {
            for (MultipleModel model : mList) {
                if (!TextUtils.equals(model.getUrl(), ADD)) {
                    urlList.add(model.getUrl());
                }
            }
            if (urlList.size() == 0) {
                ToastUtil.show(context, "请上传" + mBinding.tvTitle.getText().toString());
                return true;
            }
        }

        return false;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    private MyFileMultipleAdapter.OnAddPicClickListener listener = new MyFileMultipleAdapter.OnAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            FileSelectActivity.launch(mActivity, mRequestCode, false);
        }
    };

}
