package com.cdkj.wzcd.module.business.interview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityStartFaceViewBinding;
import com.cdkj.wzcd.model.ChekRoomIdBean;
import com.cdkj.wzcd.model.ILiveVideoBean;
import com.cdkj.wzcd.model.RecVideoBean;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.tencent.TencentLoginHelper;
import com.cdkj.wzcd.tencent.logininterface.TencentLoginInterface;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * 发起面签
 * Created by cdkj on 2018/5/30.
 */

public class InterviewStartActivity extends AbsBaseLoadActivity implements TencentLoginInterface {

    private ActivityStartFaceViewBinding mBinding;
    private TencentLoginHelper mHelper;

    private String code;
    private QiNiuHelper mQiNiuHelper;

    // 初始化、执行上传 为true时停止上传
    private volatile boolean isCancelled = false;
    private boolean isBoos = true;//是不是 房主,(是不是 创建的房间,是的话再下个界面可以录制视频,否则不可以录制视频)

    private List<String> mList = new ArrayList<>();

    private String bankVideo = "";
    private String otherVideo = "";
    private String companyVideo = "";

    String isSend;//是保存还是发送   1发送  0保存

    private int vlYhCode = 1000;
    private int vlGsCode = 999;
    private int vlOtherCode = 998;

    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, InterviewStartActivity.class);
        intent.putExtra(DATA_SIGN, code);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_start_face_view, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle(getString(R.string.face_view));

        mBaseBinding.titleView.setRightTitle(getString(R.string.start_face_view));
        mBaseBinding.titleView.setRightFraClickListener(view -> {
            if (mBinding.llPercent.getVisibility() == View.VISIBLE)
                return;
            //这里  这里
//            mHelper = new TencentLoginHelper(InterviewStartActivity.this, InterviewStartActivity.this);
//            mHelper.login(code);
            getCheckRoomLoading();

        });

        mQiNiuHelper = new QiNiuHelper(this);

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

        mBinding.myMlAdvanceFundAmountPdf.setListData(advance_fund_amount_pdf);
        mBinding.myMlBankPhoto.setListData(bank_photo);
        mBinding.myMlCompanyContract.setListData(company_contract);
        mBinding.myMlBankContract.setListData(bank_contract);
        mBinding.myMlInterviewOtherPdf.setListData(interview_other_pdf);

        List<String> bankVideoList = StringUtils.splitAsPicList(bank_video);
        mBinding.myVlBankVideo.setList(listSwitchVideoList(bankVideoList));
        List<String> companyVideoList = StringUtils.splitAsPicList(company_video);
        mBinding.myVlCompanyVideo.setList(listSwitchVideoList(companyVideoList));
        List<String> otherVideoList = StringUtils.splitAsPicList(other_video);
        mBinding.myVlOtherVideo.setList(listSwitchVideoList(otherVideoList));

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

    @Override
    protected boolean canFinish() {
        return false;
    }

    @Override
    public void topTitleViewLeftClick() {
        super.topTitleViewLeftClick();

        cancel(true, "您确定要取消上传并退出吗?");

    }

    private void initCustomView() {
        mBinding.myMlAdvanceFundAmountPdf.build(this, 1);
        mBinding.myMlBankPhoto.build(this, 2);
        mBinding.myMlCompanyContract.build(this, 3);
        mBinding.myMlBankContract.build(this, 4);
        mBinding.myMlInterviewOtherPdf.build(this, 5);

        mBinding.myVlBankVideo.build(this, 10, vlYhCode);
        mBinding.myVlCompanyVideo.build(this, 10, vlGsCode);
        mBinding.myVlOtherVideo.build(this, 20, vlOtherCode);
    }

    private void initListener() {
        //提交
        mBinding.myCbLoad.setOnConfirmListener(view -> {
            isSend = "1";
            if (check()) {
                upLoad(mBinding.myVlBankVideo.getList(), vlYhCode);
            }
        });
        //保存
        mBinding.myCbLoad.setOnConfirmRightListener(view -> {
            isSend = "0";
            upLoad(mBinding.myVlBankVideo.getList(), vlYhCode);
        });
        mBinding.llPercent.setOnClickListener(view -> {
            // do nothing
        });
    }

    private void upLoad(List<LocalMedia> list, int which) {
        //点击保存时的逻辑
        if (list == null || list.size() == 0) {
            if (which == vlYhCode) {
                upLoad(mBinding.myVlCompanyVideo.getList(), vlGsCode);
            } else if (which == vlGsCode) {
                upLoad(mBinding.myVlOtherVideo.getList(), vlOtherCode);
            } else if (which == vlOtherCode) {
                interview();
            }
            return;
        }

        //点击上传时的逻辑
        List<String> urlList = new ArrayList<>();
        List<String> loadVideoUrl = new ArrayList<>();
        for (LocalMedia localMedia : list) {
            if (localMedia.isVideoUrl()) {
                loadVideoUrl.add(localMedia.getPath());
            } else {
                urlList.add(localMedia.getPath());
            }
        }
        String title;
        if (which == vlYhCode) {
            title = "银行视频";
            bankVideo = StringUtils.listToString(loadVideoUrl, "||");
        } else if (which == vlGsCode) {
            title = "公司视频";
            companyVideo = StringUtils.listToString(loadVideoUrl, "||");
        } else {
            title = "其它视频";
            otherVideo = StringUtils.listToString(loadVideoUrl, "||");
        }

        //判断有没有可上传的视频
        if (urlList == null || urlList.size() == 0) {
            if (which == vlYhCode) {
                upLoad(mBinding.myVlCompanyVideo.getList(), vlGsCode);
            } else if (which == vlGsCode) {
                upLoad(mBinding.myVlOtherVideo.getList(), vlOtherCode);
            } else if (which == vlOtherCode) {
                interview();
            }
            return;
        }


        mQiNiuHelper.upLoadListVideo(urlList, new QiNiuHelper.UpLoadListFileListener() {

            @Override
            public void start() {
                // 初始化上传UI
                mBinding.llPercent.setVisibility(View.VISIBLE);
                mBaseBinding.titleView.setMidTitle(title + "1/" + urlList.size());
            }

            @Override
            public void onChange(int index, String url) {

                mBaseBinding.titleView.setMidTitle(title + (index + 2) + "/" + urlList.size());
            }

            @Override
            public void progress(String key, double percent) {
                if (!isCancelled) {
                    Double p = percent * 100;

                    mBinding.llPercent.setVisibility(View.VISIBLE);
                    mBinding.cpPercent.setProgress(p.intValue());

                    mBaseBinding.titleView.setRightTitle("");
                }
            }

            @Override
            public void onSuccess(List<String> result) {
                mBinding.cpPercent.setProgress(0);
                mBinding.llPercent.setVisibility(View.GONE);

                mBaseBinding.titleView.setMidTitle(getString(R.string.face_view));

                if (which == vlYhCode) {

                    if (TextUtils.isEmpty(bankVideo)) {
                        bankVideo = StringUtils.listToString(result, "||");
                    } else {
                        bankVideo += ("||" + StringUtils.listToString(result, "||"));
                    }

                    upLoad(mBinding.myVlCompanyVideo.getList(), vlGsCode);

                } else if (which == vlGsCode) {
                    if (TextUtils.isEmpty(companyVideo)) {
                        companyVideo = StringUtils.listToString(result, "||");
                    } else {
                        companyVideo += ("||" + StringUtils.listToString(result, "||"));
                    }
                    upLoad(mBinding.myVlOtherVideo.getList(), vlOtherCode);

                } else {
                    if (TextUtils.isEmpty(otherVideo)) {
                        otherVideo = StringUtils.listToString(result, "||");
                    } else {
                        otherVideo += ("||" + StringUtils.listToString(result, "||"));
                    }
                    interview();
                }

            }

            @Override
            public void onFal(String info) {
                UITipDialog.showFail(InterviewStartActivity.this, info);

            }

            @Override
            public void onError(String info) {
                UITipDialog.showFail(InterviewStartActivity.this, info);
            }
        });
    }

    /**
     * 检查这个面签是否在进行中  再进行中就加入房间,否则就开启房间
     * <p>
     * 返回对象为空的话说明没有在进行中,
     */
    public void getCheckRoomLoading() {
        Map<String, String> map = new HashMap<>();
        map.put("budgetCode", code);
        map.put("homeOwnerId", SPUtilHelper.getUserId());

        Call<BaseResponseModel<ChekRoomIdBean>> roomId = RetrofitUtils.createApi(MyApiServer.class).getChekRoomId("632954", StringUtils.getJsonToString(map));
        showLoadingDialog();
        roomId.enqueue(new BaseResponseModelCallBack<ChekRoomIdBean>(this) {
            @Override
            protected void onSuccess(ChekRoomIdBean data, String SucMessage) {
                if (data == null) {
                    //没有在进行中就去  获取房间号正常开房
                    getRoomId();
                } else {
                    //有房间号并且房間是可用的就去检查房间号 人数不满就加入房间  否则就重新获取房间号
                    if (TextUtils.equals("0", data.getStatus()) && !TextUtils.isEmpty(data.getCode())) {
                        //房间可用
                        checkRoomId(data.getCode(), true);

                    } else {
                        getRoomId();
                    }
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 获取房间号
     */
    public void getRoomId() {
        Map<String, String> map = new HashMap<>();
        map.put("budgetCode", code);
        map.put("homeOwnerId", SPUtilHelper.getUserId());
        Call<BaseResponseModel<String>> roomId = RetrofitUtils.createApi(MyApiServer.class).getRoomId("632950", StringUtils.getJsonToString(map));
        showLoadingDialog();
        roomId.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                checkRoomId(data, false);
//                getSendRoomIdSms(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 检查房间人数,超过三人  就不让再进入房间了
     *
     * @param roomid
     * @param isCheck 是否是  632054返回的房间id
     */
    public void checkRoomId(String roomid, boolean isCheck) {
        HashMap<String, String> map = new HashMap<>();
        map.put("roomId", roomid);
        map.put("userId", SPUtilHelper.getUserId());
        Call<BaseResponseModel<Integer>> roomId = RetrofitUtils.createApi(MyApiServer.class).checkRoomId("632953", StringUtils.getJsonToString(map));
        showLoadingDialog();
        roomId.enqueue(new BaseResponseModelCallBack<Integer>(this) {
            @Override
            protected void onSuccess(Integer data, String SucMessage) {
                //房间人数大于三人就不让进入了
                if (data >= 3) {
                    UITipDialog.showInfo(InterviewStartActivity.this, "房间人数已满");
                    isBoos = true;
                } else {
                    if (isCheck) {
                        if ((data < 3 && data > 0)) {
                            //加入房间  只有这一种情况  可以 也只有这一种情况不能进行录制
                            getSendRoomIdSms(roomid);
                            isBoos = false;
                        } else {
                            isBoos = true;
                            getRoomId();
                        }
                    } else {
                        isBoos = true;
                        getSendRoomIdSms(roomid);
                    }
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
//     632953

    /**
     * 给会员端发送 房间号
     */
    public void getSendRoomIdSms(String roomid) {

        mHelper = new TencentLoginHelper(InterviewStartActivity.this, InterviewStartActivity.this);
        mHelper.login(roomid);

        Map<String, String> map = RetrofitUtils.getNodeListMap();

        map.put("code", code);
        map.put("roomId", roomid);

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().successRequest("632136", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data == null)
                    return;

                if (data.isSuccess()) {
                    mHelper = new TencentLoginHelper(InterviewStartActivity.this, InterviewStartActivity.this);
                    mHelper.login(roomid);
//                    mHelper.getRoomId();
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    public void updateLoginState(boolean state) {

    }

    @Override
    public void onLoginSDKSuccess(String roomId) {
        if (checkPermission()) {
//            Integer.parseInt(roomId)
            RoomActivity.open(this, Integer.parseInt(roomId), isBoos);
//            RoomActivity.open(this, 546547, isBoos);

        }
    }

    @Override
    public void onLoginSDKFailed(String module, int errCode, String errMsg) {
        LogUtil.E("登录失败__" + module + ":" + errCode + ":" + errMsg);
//        ToastUtil.show(this, "登录失败" + ":::" + errCode + "=" + errMsg);
        ToastUtil.show(this, "登录失败请重试");
    }

    protected boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // 最小的视频回调请求码
            if (requestCode < vlOtherCode) {
                String path = data.getStringExtra(CameraHelper.staticPath);
                showLoadingDialog();
                new QiNiuHelper(this).uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
                    @Override
                    public void onSuccess(String key) {

                        if (requestCode == mBinding.myMlAdvanceFundAmountPdf.getRequestCode()) {
                            mBinding.myMlAdvanceFundAmountPdf.addList(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myMlBankPhoto.getRequestCode()) {
                            mBinding.myMlBankPhoto.addList(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myMlCompanyContract.getRequestCode()) {
                            mBinding.myMlCompanyContract.addList(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myMlBankContract.getRequestCode()) {
                            mBinding.myMlBankContract.addList(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myMlInterviewOtherPdf.getRequestCode()) {
                            mBinding.myMlInterviewOtherPdf.addList(key);
                            disMissLoading();
                        }

                    }

                    @Override
                    public void onFal(String info) {
                        disMissLoading();
                    }
                }, path);

            } else {

                if (requestCode == mBinding.myVlBankVideo.getRequestCode()) {
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    mBinding.myVlBankVideo.setList(selectList);
                }

                if (requestCode == mBinding.myVlCompanyVideo.getRequestCode()) {
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    mBinding.myVlCompanyVideo.setList(selectList);
                }

                if (requestCode == mBinding.myVlOtherVideo.getRequestCode()) {
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    mBinding.myVlOtherVideo.setList(selectList);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        cancel(false, "您确定要取消上传吗?");
    }

    private void cancel(boolean isFinish, String str) {
        if (mBinding.llPercent.getVisibility() == View.GONE) {
            finish();
        }

        showDoubleWarnListen(str, view -> {
            isCancelled = true;
            mQiNiuHelper.cancelLoadVideo();
            mBinding.cpPercent.setProgress(0);
            mBinding.llPercent.setVisibility(View.GONE);

            if (isFinish)
                finish();
        });

    }

    private boolean check() {
        if (mBinding.myVlBankVideo.check()) {
            return false;
        }

        if (mBinding.myVlCompanyVideo.check()) {
            return false;
        }

        // 资金划账授权书
        if (mBinding.myMlAdvanceFundAmountPdf.check()) {
            return false;
        }

        // 面签照片
        if (mBinding.myMlBankPhoto.check()) {
            return false;
        }

        return true;
    }


    private void interview() {
        Map<String, Object> map = new HashMap<>();

        if (TextUtils.isEmpty(code)) {
            return;
        }

        map.put("code", code);

        map.put("isSend", isSend);
        map.put("operator", SPUtilHelper.getUserId());
        map.put("bankVideo", bankVideo);
        map.put("otherVideo", otherVideo);
        map.put("companyVideo", companyVideo);

        map.put("advanceFundAmountPdf", mBinding.myMlAdvanceFundAmountPdf.getListData());
        map.put("bankPhoto", mBinding.myMlBankPhoto.getListData());
        map.put("companyContract", mBinding.myMlCompanyContract.getListData());
        map.put("bankContract", mBinding.myMlBankContract.getListData());
        map.put("interviewOtherPdf", mBinding.myMlInterviewOtherPdf.getListData());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("632123", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                UITipDialog.showSuccess(InterviewStartActivity.this, "上传成功", dialogInterface -> {
                    finish();
                });
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showFail(InterviewStartActivity.this, errorMessage);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 获取上个是界面发送的数据,判断去不去请求视频
     *
     * @param bean
     */
    @Subscribe
    public void iLiveVideo(ILiveVideoBean bean) {
        //是房主
        if (bean != null && bean.isBoos()) {
            //并且getStreamId不为空 说明 已经点击了 录制  那么这个房间就已经作废了
            if (!TextUtils.isEmpty(bean.getStreamId())) {
                showViodeDialog(bean, "发现视频是否添加到银行面签");
                return;
            } else {
                //否则就  销毁这个房间并且提示
                getCleanRoom(bean.getRoomId());
            }
        }
    }

    private void showViodeDialog(ILiveVideoBean bean, String title) {
//        "发现视频是否添加到银行面签"
        CommonDialog commonDialog = new CommonDialog(this).builder();
        commonDialog.setTitle(title);
        commonDialog.setPositiveBtn("确定", view -> {
            //延时两秒去获取视频  防止后台混流不成功
            showLoadingDialog();
            Observable.timer(2000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            getLoveViode(bean);
                        }
                    });


        }).setNegativeBtn("取消", null).show();
    }

    private void getLoveViode(ILiveVideoBean bean) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("streamId", bean.getStreamId());
        Call<BaseResponseModel<String>> iLiveVoide = RetrofitUtils.createApi(MyApiServer.class).getILiveVoide("632952", StringUtils.getJsonToString(map));
        showLoadingDialog();
        iLiveVoide.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                if (TextUtils.isEmpty(data)) {
                    UITipDialog.showSuccess(InterviewStartActivity.this, "获取失败", v -> {
                        showViodeDialog(bean, "是否重新获取");
                    });
                    return;
                }
                RecVideoBean recVideoBean = JSON.parseObject(data, new TypeReference<RecVideoBean>() {
                });
                RecVideoBean.OutputBean output = recVideoBean.getOutput();
                if (output == null) {
                    UITipDialog.showSuccess(InterviewStartActivity.this, "获取失败", v -> {
                        showViodeDialog(bean, "是否重新获取");
                    });
                    return;
                }
                List<RecVideoBean.OutputBean.FileListBean> file_list = output.getFile_list();
                if (file_list == null || file_list.size() == 0) {
                    UITipDialog.showSuccess(InterviewStartActivity.this, "获取失败", v -> {
                        showViodeDialog(bean, "是否重新获取");
                    });
                    return;
                }
                RecVideoBean.OutputBean.FileListBean fileListBean = file_list.get(0);

                String viodeUrl = fileListBean.getRecord_file_url();

                ArrayList<String> listEvent = new ArrayList<>();
                listEvent.add(viodeUrl);
                List<LocalMedia> localMedia = listSwitchVideoList(listEvent);

                List<LocalMedia> list = mBinding.myVlBankVideo.getList();
                list.addAll(localMedia);
                mBinding.myVlBankVideo.setList(list);

            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showSuccess(InterviewStartActivity.this, "获取失败", v -> {
                    showViodeDialog(bean, "是否重新获取");
                });
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void getCleanRoom(String roomId) {
        Map<String, String> map = new HashMap<>();
        map.put("code", roomId);
        Call<BaseResponseModel<SuccessBean>> baseResponseModelCall = RetrofitUtils.createApi(MyApiServer.class).cleanRoom("632955", StringUtils.getJsonToString(map));
        baseResponseModelCall.enqueue(new BaseResponseModelCallBack<SuccessBean>(this) {
            @Override
            protected void onSuccess(SuccessBean data, String SucMessage) {

                if (data.isIsSuccess()) {
                    UITipDialog.showSuccess(InterviewStartActivity.this, "房主退出,之前房间已作废");
                } else {
                    UITipDialog.showSuccess(InterviewStartActivity.this, "销毁房间失败");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
