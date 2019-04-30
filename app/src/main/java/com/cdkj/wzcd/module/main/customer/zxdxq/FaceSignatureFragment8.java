package com.cdkj.wzcd.module.main.customer.zxdxq;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.FragmentFaceSignature8Binding;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.module.main.customer.ZRDDetialsActivity;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 面签
 */
public class FaceSignatureFragment8 extends BaseLazyFragment {

    private FragmentFaceSignature8Binding mBinding;
    private ZXDetialsBean.ListBean data;


    public static Fragment getInstance() {
        FaceSignatureFragment8 fragment = new FaceSignatureFragment8();
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_face_signature8, container, false);
        initView();
        setView();
        return mBinding.getRoot();
    }

    public void initView() {
        ZRDDetialsActivity activity = (ZRDDetialsActivity) mActivity;
        this.data = activity.currentItem;
        mBinding.myVlBankVideo.build(mActivity, 10, 1);
        mBinding.myVlCompanyVideo.build(mActivity, 10, 2);
        mBinding.myVlOtherVideo.build(mActivity, 10, 3);
        mBinding.mymlGsht.build(mActivity, 4);
        mBinding.mymlQtzl.build(mActivity, 5);
        mBinding.mymlYhht.build(mActivity, 6);
        mBinding.mymlYhmqtp.build(mActivity, 7);
        mBinding.mymlZjhzsqs.build(mActivity, 8);

    }



    private void setView() {
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

        mBinding.mymlYhmqtp.setListDataByRequest(bank_photo);
        mBinding.mymlYhht.setListDataByRequest(bank_contract);
        mBinding.mymlGsht.setListDataByRequest(company_contract);
        mBinding.mymlZjhzsqs.setListDataByRequest(advance_fund_amount_pdf);
        mBinding.mymlQtzl.setListDataByRequest(interview_other_pdf);

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
}
