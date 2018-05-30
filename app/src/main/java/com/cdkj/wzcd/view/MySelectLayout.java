package com.cdkj.wzcd.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.dialog.LoadingDialog;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.LayoutMySelectHorizontalBinding;
import com.cdkj.wzcd.view.interfaces.MySelectInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/29.
 */

public class MySelectLayout extends LinearLayout {

    public static int DATA_DICTIONARY = 0;
    public static int SYSTEM_PARAMETER = 1;

    private Context context;
    private LayoutMySelectHorizontalBinding mBinding;

    private Activity mActivity;
    private boolean mIsRequest;
    private int mRequestType;
    private String mRequestKey;

    private List<DataDictionary> mData;
    private MySelectInterface mySelectInterface;

    private String txtTitle;
    private String txtContent;

    // 
    private String[] mKeyList;

    private String[] mValueList;

    // 初始默认不选中
    private int selectIndex = -1;

    protected LoadingDialog loadingDialog;

    private Call call;

    public MySelectLayout(Context context) {
        this(context, null);
    }

    public MySelectLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySelectLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySelectLayout, 0, 0);
        txtTitle = typedArray.getString(R.styleable.MySelectLayout_txt_select_title);
        txtContent = typedArray.getString(R.styleable.MySelectLayout_txt_select_content);

        typedArray.recycle();

        init(context);
        setData();
    }

    private void setData() {
        mBinding.tvTitle.setText(txtTitle);
        if (!TextUtils.isEmpty(txtContent))
            mBinding.tvContent.setText(txtContent);
    }


    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_my_select_horizontal, this, true);

        initListener();
    }

    private void initListener() {
        mBinding.llRoot.setOnClickListener(view -> {

            if (mIsRequest) {
                if (mActivity == null)
                    return;

                getRequest();
            }else {
                if (mData == null)
                return;

                showSelect();
            }

        });
    }


    public void setData(Activity activity, int requestType, String requestKey, MySelectInterface selectInterface){
        mActivity = activity;
        mRequestType = requestType;
        mRequestKey = requestKey;

        mySelectInterface = selectInterface;

        mIsRequest = true;
    }
    public void setData(List<DataDictionary> data, MySelectInterface selectInterface){
        mData = data;
        mySelectInterface = selectInterface;

        mIsRequest = false;
    }

    public String getDataKey(){

        if (selectIndex == -1){
            ToastUtil.show(context, "请选择"+mBinding.tvTitle.getText());
            return null;
        }

        return mKeyList[selectIndex];
    }

    public void getRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("dkey", "");
        map.put("orderColumn", "");
        map.put("orderDir", "");
        map.put("parentKey", mRequestKey);
        map.put("type", "");
        map.put("updater", "");

        call = RetrofitUtils.createApi(MyApiServer.class).getDataDictionary("630036", StringUtils.getJsonToString(map));

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<DataDictionary>(mActivity) {

            @Override
            protected void onSuccess(List<DataDictionary> data, String SucMessage) {
                if (data== null)
                    return;

                mData = data;
                showSelect();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void showSelect() {
        mKeyList = new String[mData.size()];
        mValueList = new String[mData.size()];


        int index = 0;
        for (DataDictionary model : mData) {
            mKeyList[index] = model.getDkey();
            mValueList[index] = model.getDvalue();
            index++;
        }

        if (index == 0)
            return;

        new AlertDialog.Builder(context).setTitle("请选择").setSingleChoiceItems(
                mValueList, selectIndex, (dialog, which) -> {

                    selectIndex = which;
                    mBinding.tvContent.setText(mValueList[which]);

                    if (mySelectInterface != null)
                        mySelectInterface.onClick(dialog,which);

                    dialog.dismiss();
                }).setNegativeButton("取消", null).show();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 隐藏Dialog
     */
    public void disMissLoading() {
        if (null != loadingDialog) {
            loadingDialog.closeDialog();
        }
    }

    /**
     * 显示dialog
     */
    public void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mActivity);
        }
        if (null != loadingDialog) {
            loadingDialog.showDialog();
        }
    }

}
