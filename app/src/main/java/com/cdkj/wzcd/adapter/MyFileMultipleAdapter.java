package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemMyFileMultipleBinding;
import com.cdkj.wzcd.model.MultipleModel;
import com.cdkj.wzcd.view.MyMultipleLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/6/26.
 */

public class MyFileMultipleAdapter extends BaseQuickAdapter<MultipleModel, BaseViewHolder> {

    private ItemMyFileMultipleBinding mBinding;

    /**
     * 点击添加图片跳转
     */
    private OnAddPicClickListener mOnAddPicClickListener;

    public interface OnAddPicClickListener {
        void onAddPicClick();
    }


    public MyFileMultipleAdapter(@Nullable List<MultipleModel> data, OnAddPicClickListener mOnAddPicClickListener) {
        super(R.layout.item_my_file_multiple, data);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleModel item) {
        if (TextUtils.isEmpty(item.getUrl()))
            return;

        mBinding = DataBindingUtil.bind(helper.itemView);


        if (TextUtils.equals(item.getUrl(), MyMultipleLayout.ADD)) {

            mBinding.llDel.setVisibility(View.GONE);
            mBinding.flImg.setOnClickListener(view -> {
                mOnAddPicClickListener.onAddPicClick();
            });

        } else {
            mBinding.tvName.setText(item.getUrl());

            if (item.isCanEdit()) {
                mBinding.ivHint.setImageResource(R.mipmap.modifi);
                mBinding.tvHint.setText("点击修改");
                mBinding.tvHint.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                mBinding.llDel.setOnClickListener(view -> {
                    int index = helper.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致
                    if (index != RecyclerView.NO_POSITION) {
                        getData().remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, getData().size());
                    }
                });

                helper.addOnClickListener(mBinding.llHint.getId());

            } else {
                mBinding.llDel.setVisibility(View.GONE);
                mBinding.llHint.setVisibility(View.GONE);
            }

        }
    }
}
