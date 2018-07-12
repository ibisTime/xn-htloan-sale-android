package com.cdkj.wzcd.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.GridImageAdapter;
import com.cdkj.wzcd.databinding.LayoutMyVideoBinding;
import com.cdkj.wzcd.module.business.interview.FullyGridLayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdkj on 2018/6/22.
 */

public class MyVideoLayout extends LinearLayout {

    private Context context;

    private Activity mActivity;
    private LayoutMyVideoBinding mBinding;

    private String tvTitle;

    private int themeId;
    private int mRequestCode;

    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;

    public MyVideoLayout(Context context) {
        this(context, null);
    }

    public MyVideoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyVideoLayout, 0, 0);
        tvTitle = typedArray.getString(R.styleable.MyVideoLayout_title);

        init(context);
        setData();
    }

    private void init(Context context) {
        this.context = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_my_video, this, true);

        initListener();
    }

    private void initListener() {

    }

    private void setData() {
        mBinding.tvTitle.setText(tvTitle);

    }

    public void setTitle(String title) {
        mBinding.tvTitle.setText(title);

    }

    public void build(Activity activity, int maxSelectNum, int requestCode){
        mActivity = activity;
        mRequestCode = requestCode;

        themeId = R.style.picture_default_style;

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        mBinding.rvVideo.setLayoutManager(manager);
        mBinding.rvVideo.setNestedScrollingEnabled(false);

        adapter = new GridImageAdapter(mActivity, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mBinding.rvVideo.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(mActivity).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(mActivity).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(mActivity).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
    }

    public void setList(List<LocalMedia> list){
        selectList = list;
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
    }

    public List<LocalMedia> getList(){
       return selectList;
    }

    public boolean check(){
        if(selectList == null || selectList.size() == 0){
            ToastUtil.show(context, "请选择"+mBinding.tvTitle.getText().toString());
            return true;
        }

        return false;
    }

    public int getRequestCode(){
        return mRequestCode;
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(mActivity)
                    .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(10)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewVideo(true)// 是否可预览视频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(mRequestCode);//结果回调onActivityResult code

        }

    };

}
