package com.cdkj.wzcd.view.popup;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.SearchAdapter;
import com.cdkj.wzcd.databinding.PopupSearchBinding;
import com.cdkj.wzcd.model.SearchModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdkj on 2018/7/5.
 */

public class MySearchPopup extends PopupWindow {

    public static final String SEARCH_EDIT = "text";
    public static final String SEARCH_INPUT = "input";
    public static final String SEARCH_SELECT= "select";
    public static final String SEARCH_EDIT_SELECT= "edit_select";
    public static final String SEARCH_DATE_TIME = "time";


    private PopupSearchBinding mBinding;

    private Context mContext;

    // 选择的搜索数据下标
    private int mPosition = 0;

    // 搜索类型
    private List<SearchModel> mSearchTypeList;
    private SearchAdapter mSearchTypeAdapter;

    // 搜索内容
    private List<SearchModel> mList = new ArrayList<>();
    private SearchAdapter mAdapter;

    private String mSearchValue;//搜索关键字

    public MySearchPopup(Context context, List<SearchModel> searchTypeList){
        super(context);

        mSearchTypeList = searchTypeList;
        mContext = context;

        this.init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.popup_search, null, false);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mBinding.getRoot());
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.common_popup_bg));
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        initListener();
        initSearch();
        initEditKeyBoard();
    }

    /**
     * 显示popupWindow
     * @param parent
     */
    public void show(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private void initListener() {

        mBinding.tvOperate.setOnClickListener(view -> {
            if (TextUtils.equals(mBinding.tvOperate.getText(), "取消")){
                this.dismiss();
            }else {



            }
        });

        mBinding.llSearchType.setOnClickListener(view -> {

            LogUtil.E("rvList "+(mBinding.rvSearchType.getVisibility() == View.VISIBLE));
            mBinding.rvSearchType.setVisibility(View.VISIBLE);

        });

    }


    private void initSearch(){
        // 初始化搜索内容
        mAdapter = new SearchAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            SearchModel searchModel = mAdapter.getItem(position);
            EventBus.getDefault().post(searchModel);

            //清空搜索内容
            mList.clear();
            mAdapter.notifyDataSetChanged();

        });
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBinding.rvList.setAdapter(mAdapter);


        // 初始化搜索类型
        mSearchTypeAdapter = new SearchAdapter(mSearchTypeList);
        mSearchTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            mPosition = position;
            SearchModel searchModel = mSearchTypeAdapter.getItem(mPosition);

            mBinding.rvSearchType.setVisibility(View.GONE);
            mBinding.tvSearchType.setText(searchModel.getKeyTypeText());
            initSearchType(searchModel);
        });

        mBinding.rvSearchType.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBinding.rvSearchType.setAdapter(mSearchTypeAdapter);

        if (mSearchTypeList != null && mSearchTypeList.size() != 0){
            SearchModel searchModel = mSearchTypeList.get(mPosition);

            mBinding.tvSearchType.setText(searchModel.getKeyTypeText());
            initSearchType(searchModel);
        }

    }

    private void initSearchType(SearchModel searchModel) {

        mBinding.tvSearchType.setText(searchModel.getKeyTypeText());

        switch (searchModel.getSearchType()){

            case SEARCH_EDIT:
                mBinding.editSearchView.setFocusable(true);
                mBinding.ivIcon.setVisibility(View.VISIBLE);
                mBinding.ivMoreDown.setVisibility(View.GONE);
                mBinding.ivMoreRight.setVisibility(View.GONE);

                mBinding.editSearchView.addTextChangedListener(getDefaultTextWatcher());
                break;

            case SEARCH_DATE_TIME:
                mBinding.editSearchView.setFocusable(false);
                mBinding.ivIcon.setVisibility(View.GONE);
                mBinding.ivMoreDown.setVisibility(View.GONE);
                mBinding.ivMoreRight.setVisibility(View.VISIBLE);

                mBinding.editSearchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (TextUtils.isEmpty(editable)){
                            mBinding.tvOperate.setText("取消");
                        }else {
                            mBinding.tvOperate.setText("搜索");
                        }
                    }
                });

                break;

            case SEARCH_INPUT:
                mBinding.editSearchView.setFocusable(true);
                mBinding.ivIcon.setVisibility(View.VISIBLE);
                mBinding.ivMoreDown.setVisibility(View.GONE);
                mBinding.ivMoreRight.setVisibility(View.GONE);

                mBinding.editSearchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!TextUtils.isEmpty(editable)){

                        }
                    }
                });

                break;

            case SEARCH_SELECT:
                mBinding.editSearchView.setFocusable(false);
                mBinding.ivIcon.setVisibility(View.GONE);
                mBinding.ivMoreDown.setVisibility(View.VISIBLE);
                mBinding.ivMoreRight.setVisibility(View.GONE);

                mBinding.editSearchView.addTextChangedListener(getDefaultTextWatcher());
                break;

            case SEARCH_EDIT_SELECT:
                mBinding.editSearchView.setFocusable(true);
                mBinding.ivIcon.setVisibility(View.VISIBLE);
                mBinding.ivMoreDown.setVisibility(View.GONE);
                mBinding.ivMoreRight.setVisibility(View.GONE);

                mBinding.editSearchView.addTextChangedListener(getDefaultTextWatcher());
                break;



        }

    }

    private TextWatcher getDefaultTextWatcher(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return textWatcher;
    }

    /**
     * 设置输入键盘
     */
    private void initEditKeyBoard() {

        mBinding.editSearchView.setOnEditorActionListener((v, actionId, event) -> {
            mSearchValue = v.getText().toString();

//            if (startSearchByKey())
//                return false;

            // 通知搜索
            SearchModel searchModel = mSearchTypeList.get(mPosition);


            switch (searchModel.getSearchType()){

                case SEARCH_EDIT:

                    searchModel.setSearchValue(mSearchValue);
                    EventBus.getDefault().post(searchModel);

                    break;

                case SEARCH_EDIT_SELECT:

                    SearchHelper.getUserRequest(mContext, mSearchValue, new SearchHelper.searchUserInterface() {
                        @Override
                        public void onSuccess(List<UserModel> list) {
                            mList.clear();

                            for (UserModel userModel : list){
                                SearchModel sm = new SearchModel();
                                sm.setKeyTypeText(userModel.getCompanyName()+"-"+userModel.getRealName());
                                sm.setSearchKey("realName");
                                sm.setSearchValue(userModel.getUserId());
                                mList.add(sm);
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onReqFailure(String errorCode, String errorMessage) {

                        }
                    });

                    break;

            }




            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);//隐藏键盘
                return true;
            }
            return false;
        });
    }

    /**
     * 开始搜索
     *
     * @return
     */
    private boolean startSearchByKey() {
        if (TextUtils.isEmpty(mSearchValue)) {
            UITipDialog.showInfo(mContext, "请输入搜索内容");
            return true;
        }

        return false;
    }
}
