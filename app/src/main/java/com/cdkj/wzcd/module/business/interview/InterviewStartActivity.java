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

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.CodeModel;
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
import com.cdkj.wzcd.model.FaceSignBean;
import com.cdkj.wzcd.tencent.TencentLoginHelper;
import com.cdkj.wzcd.tencent.logininterface.TencentLoginInterface;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            getSendRoomIdSms();
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
        Call<BaseResponseModel<FaceSignBean>> faceSign = RetrofitUtils.createApi(MyApiServer.class).getFaceSign("632146", StringUtils.getJsonToString(map));
        addCall(faceSign);

        showLoadingDialog();
        faceSign.enqueue(new BaseResponseModelCallBack<FaceSignBean>(this) {
            @Override
            protected void onSuccess(FaceSignBean data, String SucMessage) {
                setView(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setView(FaceSignBean data) {
//        List<String> strings = StringUtils.splitAsPicList(data.getAdvanceFundAmountPdf());
        mBinding.myIlAdvanceFundAmountPdf.setFlImg(data.getAdvanceFundAmountPdf());
        mBinding.myIlBankPhoto.setFlImg(data.getBankPhoto());
        mBinding.myIlCompanyContract.setFlImg(data.getCompanyContract());
        mBinding.myIlBankContract.setFlImg(data.getBankContract());
        mBinding.myIlInterviewOtherPdf.setFlImg(data.getInterviewOtherPdf());

        List<String> bankVideoList = StringUtils.splitAsPicList(data.getBankVideo());
        mBinding.myVlBankVideo.setList(listSwitchVideoList(bankVideoList));
        List<String> companyVideoList = StringUtils.splitAsPicList(data.getCompanyVideo());
        mBinding.myVlCompanyVideo.setList(listSwitchVideoList(companyVideoList));
        List<String> otherVideoList = StringUtils.splitAsPicList(data.getOtherVideo());
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
        mBinding.myIlAdvanceFundAmountPdf.setActivity(this, 1, -1);
        mBinding.myIlBankPhoto.setActivity(this, 2, -1);
        mBinding.myIlCompanyContract.setActivity(this, 3, -1);
        mBinding.myIlBankContract.setActivity(this, 4, -1);
        mBinding.myIlInterviewOtherPdf.setActivity(this, 5, -1);

        mBinding.myVlBankVideo.build(this, 10, vlYhCode);
        mBinding.myVlCompanyVideo.build(this, 10, vlGsCode);
        mBinding.myVlOtherVideo.build(this, 20, vlOtherCode);
    }

    private void initListener() {

        mBinding.myCbLoad.setOnConfirmListener(view -> {
            isSend = "1";
            if (check()) {
                upLoad(mBinding.myVlBankVideo.getList(), vlYhCode);
            }
        });
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

    public void getSendRoomIdSms() {

        mHelper = new TencentLoginHelper(InterviewStartActivity.this, InterviewStartActivity.this);
        mHelper.login();

//        Map<String, String> map = RetrofitUtils.getNodeListMap();
//
//        map.put("code", code);
//
//        showLoadingDialog();
//
//        Call call = RetrofitUtils.getBaseAPiService().successRequest("632136", StringUtils.getJsonToString(map));
//        addCall(call);
//
//        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
//            @Override
//            protected void onSuccess(IsSuccessModes data, String SucMessage) {
//                if (data == null)
//                    return;
//
//                if (data.isSuccess()){
//                    mHelper = new TencentLoginHelper(InterviewStartActivity.this, InterviewStartActivity.this);
//                    mHelper.login();
//                }
//
//            }
//
//            @Override
//            protected void onFinish() {
//                disMissLoading();
//            }
//        });

    }

    @Override
    public void updateLoginState(boolean state) {

    }

    @Override
    public void onLoginSDKSuccess() {
        if (checkPermission()) {
            String roomId = code.substring(code.length() - 7, code.length());

            try {
                RoomActivity.open(this, Integer.parseInt(roomId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoginSDKFailed(String module, int errCode, String errMsg) {
        LogUtil.E("登录失败__" + module + ":" + errCode + ":" + errMsg);
        ToastUtil.show(this, "登录失败" + ":::" + errCode + "=" + errMsg);
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

                        if (requestCode == mBinding.myIlAdvanceFundAmountPdf.getRequestCode()) {
                            mBinding.myIlAdvanceFundAmountPdf.setFlImg(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myIlBankPhoto.getRequestCode()) {
                            mBinding.myIlBankPhoto.setFlImg(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myIlCompanyContract.getRequestCode()) {
                            mBinding.myIlCompanyContract.setFlImg(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myIlBankContract.getRequestCode()) {
                            mBinding.myIlBankContract.setFlImg(key);
                            disMissLoading();
                        }

                        if (requestCode == mBinding.myIlInterviewOtherPdf.getRequestCode()) {
                            mBinding.myIlInterviewOtherPdf.setFlImg(key);
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
//                    for (LocalMedia irem : selectList) {
//                        LogUtil.E(irem.toString());
//                    }
                    mBinding.myVlBankVideo.setList(selectList);
                    // TODO: 2018/10/9
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
        if (TextUtils.isEmpty(mBinding.myIlAdvanceFundAmountPdf.check())) {
            return false;
        }

        // 面签照片
        if (TextUtils.isEmpty(mBinding.myIlBankPhoto.check())) {
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

        map.put("advanceFundAmountPdf", mBinding.myIlAdvanceFundAmountPdf.getFlImgUrl());
        map.put("bankPhoto", mBinding.myIlBankPhoto.getFlImgUrl());
        map.put("companyContract", mBinding.myIlCompanyContract.getFlImgUrl());
        map.put("bankContract", mBinding.myIlBankContract.getFlImgUrl());
        map.put("interviewOtherPdf", mBinding.myIlInterviewOtherPdf.getFlImgUrl());

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
}
