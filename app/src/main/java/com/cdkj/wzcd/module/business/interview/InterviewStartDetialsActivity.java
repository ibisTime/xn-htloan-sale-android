package com.cdkj.wzcd.module.business.interview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityStartFaceDetalisViewBinding;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 面签详情  审核界面
 * Created by cdkj on 2018/5/30.
 */

public class InterviewStartDetialsActivity extends AbsBaseLoadActivity {

    private ActivityStartFaceDetalisViewBinding mBinding;

    private String code;
    private String currentCode;

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, InterviewStartDetialsActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_start_face_detalis_view, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.face_view));

        initCustomView();
        initListener();
        if (getIntent() == null)
            return;
        code = getIntent().getStringExtra(DATA_SIGN);

        getDetails();
    }

    /**
     * 获取详情,获取之前有没没有保存过数据,有就展示出来
     */
    private void getDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        Call<BaseResponseModel<ZXDetialsBean.ListBean>> faceSign = RetrofitUtils.createApi(MyApiServer.class).getFaceSign2("632146", StringUtils.getJsonToString(map));
        addCall(faceSign);

        showLoadingDialog();
        faceSign.enqueue(new BaseResponseModelCallBack<ZXDetialsBean.ListBean>(this) {

            @Override
            protected void onSuccess(ZXDetialsBean.ListBean data, String SucMessage) {
                currentCode = data.getCode();
                setView(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(ZXDetialsBean.ListBean data) {
//        public static String bank_video = "bank_video";    //银行视频
//        public static String bank_contract = "bank_contract";    //银行合同
//        public static String bank_photo = "bank_photo";//银行面签照片
//        public static String company_video = "company_video";//	公司视频
//        public static String company_contract = "company_contract";//公司合同
//        public static String other_video = "other_video";//其他视频
//        public static String advance_fund_amount_pdf = "advance_fund_amount_pdf";//	资金划转授权书
//        public static String interview_other_pdf = "interview_other_pdf";//	面签其他资料

        List<ZXDetialsBean.ListBean.AttachmentsBean> attachments = data.getAttachments();
        String bank_video = BizTypeHelper.getKayToUrl(BizTypeHelper.bank_video, attachments);
        String company_video = BizTypeHelper.getKayToUrl(BizTypeHelper.company_video, attachments);
        String other_video = BizTypeHelper.getKayToUrl(BizTypeHelper.other_video, attachments);

        String bank_contract = BizTypeHelper.getKayToUrl(BizTypeHelper.bank_contract, attachments);
        String bank_photo = BizTypeHelper.getKayToUrl(BizTypeHelper.bank_photo, attachments);
        String company_contract = BizTypeHelper.getKayToUrl(BizTypeHelper.company_contract, attachments);
        String advance_fund_amount_pdf = BizTypeHelper.getKayToUrl(BizTypeHelper.advance_fund_amount_pdf, attachments);
        String interview_other_pdf = BizTypeHelper.getKayToUrl(BizTypeHelper.interview_other_pdf, attachments);

        mBinding.myMlAdvanceFundAmountPdf.setListDataByRequest(advance_fund_amount_pdf);
        mBinding.myMlBankPhoto.setListDataByRequest(bank_photo);
        mBinding.myMlCompanyContract.setListDataByRequest(company_contract);
        mBinding.myMlBankContract.setListDataByRequest(bank_contract);
        mBinding.myMlInterviewOtherPdf.setListDataByRequest(interview_other_pdf);

        List<String> bankVideoList = StringUtils.splitAsPicList(bank_video);
        mBinding.myVlBankVideo.setListByRequest(listSwitchVideoList(bankVideoList));
        List<String> companyVideoList = StringUtils.splitAsPicList(company_video);
        mBinding.myVlCompanyVideo.setListByRequest(listSwitchVideoList(companyVideoList));
        List<String> otherVideoList = StringUtils.splitAsPicList(other_video);
        mBinding.myVlOtherVideo.setListByRequest(listSwitchVideoList(otherVideoList));

    }


    private List<LocalMedia> listSwitchVideoList(List<String> list) {
        if (list == null || list.size() == 0) {

            return new ArrayList();
        }

        List<LocalMedia> lLocalMediaList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setMimeType(PictureMimeType.ofVideo());

            //image/jpeg
            //video/mp4
            String url = list.get(i);
            if (url.endsWith(".mp4")) {
                localMedia.setPictureType("video/mp4");
                localMedia.setMimeType(PictureMimeType.ofVideo());
                localMedia.setVideoUrl(true);
            } else if (url.endsWith(".jpg")) {
                localMedia.setPictureType("image/jpeg");
                localMedia.setMimeType(PictureMimeType.ofImage());
                localMedia.setVideoUrl(false);
            } else {
                localMedia.setPictureType("video/mp4");
                localMedia.setMimeType(PictureMimeType.ofVideo());
                localMedia.setVideoUrl(true);
            }
            localMedia.setVideoUrl(true);
//            localMedia.setPath(MyCdConfig.QINIU_URL + url + "?vframe/png/offset/0");
            localMedia.setPath(url);
            lLocalMediaList.add(localMedia);
        }
        return lLocalMediaList;
    }

    private void initCustomView() {
        mBinding.myMlAdvanceFundAmountPdf.build(this, 1);
        mBinding.myMlBankPhoto.build(this, 2);
        mBinding.myMlCompanyContract.build(this, 3);
        mBinding.myMlBankContract.build(this, 4);
        mBinding.myMlInterviewOtherPdf.build(this, 5);

        mBinding.myVlBankVideo.build(this, 10, 6);
        mBinding.myVlCompanyVideo.build(this, 10, 7);
        mBinding.myVlOtherVideo.build(this, 20, 8);
    }

    private void initListener() {
        //通过
        mBinding.myCbLoad.setOnConfirmListener(view -> {
            submit("1");
        });

        //不通过
        mBinding.myCbLoad.setOnConfirmRightListener(view -> {
            submit("0");
        });
        mBinding.llPercent.setOnClickListener(view -> {
            // do nothing
        });
    }

    /**
     * @param isYes 1通过  0不通过
     */
    private void submit(String isYes) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", currentCode);
        map.put("approveResult", isYes);
        map.put("approveNote", mBinding.myELMsg.getText());
        map.put("operator", SPUtilHelper.getUserId());
//
        Call<BaseResponseModel<SuccessBean>> success = RetrofitUtils.createApi(MyApiServer.class).success("632137", StringUtils.getJsonToString(map));
        addCall(success);
        success.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {
                UITipDialog.showSuccess(InterviewStartDetialsActivity.this, "成功", dialogInterface -> finish());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
