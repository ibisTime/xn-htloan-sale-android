package com.cdkj.wzcd.module.datatransfer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.adapter.DataFileAdapter;
import com.cdkj.wzcd.adapter.DataFileChoiceAdapter;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivitySendBinding;
import com.cdkj.wzcd.model.CLQDBean;
import com.cdkj.wzcd.model.DataFileModel;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.util.DataDictionaryHelper;
import com.cdkj.wzcd.util.DatePickerHelper;
import com.cdkj.wzcd.view.MySelectLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

public class SendActivity extends AbsBaseLoadActivity {

    private String code;
    private ActivitySendBinding mBinding;

    private List<DataFileModel> refFileList = new ArrayList<>();
    private DataFileAdapter refFileAdapter;

    private List<DataFileModel> sendFileList = new ArrayList<>();
    private DataFileChoiceAdapter sendFileAdapter;
    private String[] clqds;
    ArrayList<String> clqdList = new ArrayList<>();
    private Boolean isGps;

    public static void open(Context context, String code) {
        if (context != null) {
            Intent intent = new Intent(context, SendActivity.class);
            intent.putExtra(DATA_SIGN, code);
            context.startActivity(intent);
        }

    }

    public static void open(Context context, String code, boolean isGps) {
        if (context != null) {
            Intent intent = new Intent(context, SendActivity.class);
            intent.putExtra(DATA_SIGN, code);
            intent.putExtra("isGps", isGps);
            context.startActivity(intent);
        }

    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_send, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("发件");

        if (getIntent() == null)
            return;

        code = getIntent().getStringExtra(DATA_SIGN);
        isGps = getIntent().getBooleanExtra("isGps", false);

        getData();
        initAdapter();

        getCLQD();

        initListener();
    }

    /**
     * 获取材料清单
     */
    private void getCLQD() {

        Call<BaseResponseListModel<CLQDBean>> clqd = RetrofitUtils.createApi(MyApiServer.class).getCLQD("632217", "{}");
        addCall(clqd);
        showLoadingDialog();
        clqd.enqueue(new BaseResponseListCallBack<CLQDBean>(this) {
            @Override
            protected void onSuccess(List<CLQDBean> data, String SucMessage) {
                if (data == null || data.size() == 0) {
                    return;
                }
//                clqds = new String[5];
//                clqds[0]="条目1";
//                clqds[1]="条目2";
//                clqds[2]="条目3";
//                clqds[3]="条目4";
//                clqds[4]="条目5";
                clqds = new String[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    clqds[i] = data.get(i).getName();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void initListener() {
        mBinding.myNlDateTime.setOnClickListener(view -> {
            new DatePickerHelper().build(this).getDate(mBinding.myNlDateTime, true, true, true, true, true, true);
        });

        mBinding.tvClqd.setOnClickListener(view -> {
            showMutilAlertDialog();

        });

        mBinding.myCbConfirm.setOnConfirmListener(view -> {
            if (check()) {
                sendRequest();
            }
        });
    }

    public void showMutilAlertDialog() {
        clqdList.clear();
//        final String[] items = {"多选1", "多选2", "多选3", "多选4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("材料清单");        /**         *第一个参数:弹出框的消息集合，一般为字符串集合         * 第二个参数：默认被选中的，布尔类数组         * 第三个参数：勾选事件监听         */
        alertBuilder.setMultiChoiceItems(clqds, null, (dialogInterface, i, isChecked) -> {
            if (isChecked) {
                clqdList.add(clqds[i]);
                // Toast.makeText(SendActivity.this, "选择" + clqds[i], Toast.LENGTH_SHORT).show();
            } else {
                clqdList.remove(clqds[i]);
//                    Toast.makeText(SendActivity.this, "取消选择" + clqds[i], Toast.LENGTH_SHORT).show();
            }
        });
        alertBuilder.setPositiveButton("确定", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            String clqd = "";
            for (int j = 0; j < clqdList.size(); j++) {
                clqd += clqdList.get(j);
                if (j != clqdList.size() - 1) {
                    clqd += ",";
                }
            }
            mBinding.tvClqd.setText(clqd);
        });
        alertBuilder.setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss());
        alertBuilder.show();
    }


    private void initAdapter() {
        refFileAdapter = new DataFileAdapter(refFileList);
        mBinding.rvRefFile.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvRefFile.setAdapter(refFileAdapter);

        sendFileAdapter = new DataFileChoiceAdapter(sendFileList);
        sendFileAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            DataFileModel model = sendFileAdapter.getItem(position);

            model.setChoice(!model.isChoice());
            sendFileAdapter.notifyItemChanged(position);

        });
        mBinding.rvSendFile.setLayoutManager(getLinearLayoutManager(false));
        mBinding.rvSendFile.setAdapter(sendFileAdapter);
    }

    public void getData() {
        Map<String, String> map = new HashMap<>();

        map.put("code", code);

        showLoadingDialog();

        Call call = RetrofitUtils.createApi(MyApiServer.class).getData("632156", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<DataTransferModel>(this) {
            @Override
            protected void onSuccess(DataTransferModel data, String SucMessage) {

                setView(data);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(DataTransferModel data) {
        if (isGps) {
            mBinding.llGps.setVisibility(View.VISIBLE);
            mBinding.myNlTeamName.setText(data.getTeamName());
            mBinding.myNlUserName.setText(data.getUserName());
            mBinding.myNlUserRole.setText(data.getUserRole());
            mBinding.myNlCarFrameNo.setText(data.getGpsApply() == null ? "" : data.getGpsApply().getCarFrameNo());
        }


        mBinding.myNlName.setText(data.getUserName());
        mBinding.myNlCode.setText(data.getBizCode());

        if (!TextUtils.isEmpty(data.getRefFileList())) {
            mBinding.llRefFile.setVisibility(View.VISIBLE);

            setReFileListData(data.getRefFileList());
        } else {
            mBinding.llRefFile.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(data.getSendFileList())) {
            mBinding.llSendFile.setVisibility(View.VISIBLE);

            setSendFileListData(data.getSendFileList());
        } else {
            mBinding.llSendFile.setVisibility(View.GONE);
        }

        mBinding.mySlWay.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.send_type, (dialog, which) -> {
            // 如果寄送方式为快递则显示快递
            mBinding.llLogistics.setVisibility(TextUtils.equals(mBinding.mySlWay.getDataValue(), "快递") ? View.VISIBLE : View.GONE);
        });

        mBinding.mySlCompany.setData(this, MySelectLayout.DATA_DICTIONARY, DataDictionaryHelper.kd_company, null);

    }

    private void setReFileListData(String reFile) {

        String[] reFileStr = reFile.split(",");

        List<DataFileModel> list = new ArrayList<>();

        for (String file : reFileStr) {
            DataFileModel model = new DataFileModel();
            model.setFile(file);
            model.setChoice(false);
            list.add(model);
        }
        refFileList.clear();
        refFileList.addAll(list);
        refFileAdapter.notifyDataSetChanged();
    }

    private void setSendFileListData(String sendFile) {

        String[] sendFileStr = sendFile.split(",");

        sendFileList.clear();
        for (String file : sendFileStr) {
            DataFileModel model = new DataFileModel();
            model.setFile(file);
            model.setChoice(false);
            sendFileList.add(model);
        }
        sendFileAdapter.notifyDataSetChanged();
    }


    private List<DataFileModel> getConfirmSendFileList() {

        List<DataFileModel> list = new ArrayList<>();

        for (DataFileModel model : sendFileList) {

            if (model.isChoice()) {
                list.add(model);
            }

        }

        return list;
    }

    private String getConfirmSendFile() {

        String sendFile = "";

        for (DataFileModel model : sendFileList) {

            if (model.isChoice()) {
                sendFile = sendFile + model.getFile() + ",";
            }

        }

        return sendFile;
    }

    private boolean check() {
        if (mBinding.llSendFile.getVisibility() == View.VISIBLE) {
            int i = 0;

            for (DataFileModel model : sendFileList) {

                if (model.isChoice()) {
                    i++;
                }

            }

            if (i == 0) {
                ToastUtil.show(this, "请勾选寄送材料");
                return false;
            }
        }

        if (TextUtils.isEmpty(mBinding.tvClqd.getText())) {
            ToastUtil.show(this, "请选择材料清单");
            return false;
        }

        // 寄送方式
        if (mBinding.mySlWay.check()) {
            return false;
        }

        if (mBinding.llLogistics.getVisibility() == View.VISIBLE) {

            // 快递公司
            if (mBinding.mySlCompany.check()) {
                return false;
            }

            // 快递单号
            if (TextUtils.isEmpty(mBinding.myElNumber.check())) {
                return false;
            }
        }

        // 发货时间
        if (TextUtils.isEmpty(mBinding.myNlDateTime.check())) {
            return false;
        }


        return true;
    }

    private void sendRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("sendNote", mBinding.myElNote.getText());
        map.put("sendType", mBinding.mySlWay.getDataKey());
        map.put("operator", SPUtilHelper.getUserId());
        map.put("sendDatetime", mBinding.myNlDateTime.getTag());
        map.put("filelist", mBinding.tvClqd.getText());
        map.put("senderName", SPUtilHelper.getUserId());//发件人
        if (mBinding.llSendFile.getVisibility() == View.VISIBLE) {
            map.put("sendFileList", getConfirmSendFile().substring(0, getConfirmSendFile().length() - 1));
        } else {
//            map.put("sendFileList", "合同,材料"); // 要去掉!!!要去掉!!!要去掉!!!
            map.put("sendFileList", "");
        }

        if (mBinding.llLogistics.getVisibility() == View.VISIBLE) {
            map.put("logisticsCode", mBinding.myElNumber.getText());
            map.put("logisticsCompany", mBinding.mySlCompany.getDataKey());
        }


        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632150", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(SendActivity.this, "发件成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(SendActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
