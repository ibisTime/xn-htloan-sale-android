package com.cdkj.wzcd.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ItemDataTransferBinding;
import com.cdkj.wzcd.model.DataTransferBean;
import com.cdkj.wzcd.view.MyNormalLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author qi
 * @updateDts 2018/5/30
 */

public class DataTransferAdapter extends BaseQuickAdapter<DataTransferBean, BaseViewHolder> {
    private ItemDataTransferBinding mBinding;

    public DataTransferAdapter(@Nullable List<DataTransferBean> data) {
        super(R.layout.item_data_transfer, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataTransferBean item) {

        mBinding  = DataBindingUtil.bind(helper.itemView);

        helper.setText(R.id.tv_mesg, "银行放款:业务团队车辆落户--业务袋后审核材料");//节点信息

        Button btn_input = helper.getView(R.id.btn_send);//发件
        Button btn_receive_and_check = helper.getView(R.id.btn_receive_and_check);//收件并审核
        MyNormalLayout mnl_code = helper.getView(R.id.mnl_code);//业务编号
        MyNormalLayout mnl_name = helper.getView(R.id.mnl_name);//客户姓名
        MyNormalLayout mnl_number = helper.getView(R.id.mnl_number);//快递单号

        helper.addOnClickListener(R.id.btn_send);
        helper.addOnClickListener(R.id.btn_receive_and_check);

    }
}
