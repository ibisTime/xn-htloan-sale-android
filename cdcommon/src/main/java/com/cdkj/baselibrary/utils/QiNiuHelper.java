package com.cdkj.baselibrary.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.cdkj.baselibrary.CdApplication;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.model.QiniuGetTokenModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

import static com.cdkj.baselibrary.utils.BitmapUtils.getImageWidthHeight;


/**
 * 七牛上传图片
 * <p>
 * 实现ImageCompressInterface接口可以修改默认压缩方法
 * 调用clear清除任务
 * <p>
 * Created by cdkj on 2016/12/29.
 */

public class QiNiuHelper {

    private final String ANDROID = "ANDROID";
    private Context context;
    private ImageCompressInterface mImageCompressInterface;

    private int upLoadListIndex; //多张图片上传索引

    private CompositeDisposable mSubscription;

    // 初始化、执行上传 为true时停止上传
    private volatile boolean isCancelled = false;


    // 多文件上传结果集合
    List<String> fileResultList = new ArrayList<>();

    /**
     * @param context
     */
    public QiNiuHelper(Context context) {
        this.context = context;
        upLoadListIndex = 0;
        mImageCompressInterface = new DeflatCompreess();
        mSubscription = new CompositeDisposable();
    }

    public void setmImageCompressInterface(ImageCompressInterface mImageCompressInterface) {
        this.mImageCompressInterface = mImageCompressInterface;
    }

    /**
     * 图片单张上传
     *
     * @param callBack
     * @param url
     */
    public void uploadSingle(final QiNiuCallBack callBack, final String url, final String token) {

        Configuration config = new Configuration.Builder().build();

        final UploadManager uploadManager = new UploadManager(config);

        final String key = ANDROID + timestamp() + getImageWidthHeight(url) + ".jpg";

        mSubscription.add(Observable.just(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, byte[]>() {
                    @Override
                    public byte[] apply(@NonNull String s) throws Exception {
                        return mImageCompressInterface.onCompress(context, s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) throws Exception {

                        if (bytes == null || bytes.length == 0) {
                            callBack.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                            return;
                        }
                        uploadManager.put(url, key, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(final String key, final ResponseInfo info, final JSONObject res) {

                                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                                        if (info != null && info.isOK()) {
                                            if (callBack != null) {

                                                Observable.just("")
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Consumer<String>() {
                                                            @Override
                                                            public void accept(String s) throws Exception {
                                                                callBack.onSuccess(key);
                                                            }
                                                        });
                                            }

                                        } else {
                                            if (callBack != null) {
                                                Observable.just(CdApplication.getContext().getString(R.string.qiniu_token_error))
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Consumer<String>() {
                                                            @Override
                                                            public void accept(String s) throws Exception {
                                                                callBack.onFal(s);
                                                            }
                                                        });
                                            }
                                            Log.i("QiNiu", "Upload Fail");
                                            Log.i("QiNiu", "key=" + key);
                                            Log.i("QiNiu", "res=" + res);
                                            Log.i("QiNiu", "info=" + info);
                                        }
                                    }
                                }, null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.onFal("图片上传失败");
                    }
                }));


    }


    /**
     * 获取七牛token
     *
     * @return
     */
    public Call<BaseResponseModel<QiniuGetTokenModel>> getQiniuToeknRequest() {
        Map<String, String> object = new HashMap<>();
        object.put("companyCode", MyCdConfig.COMPANY_CODE);
        object.put("systemCode", MyCdConfig.SYSTEM_CODE);
        return RetrofitUtils.getBaseAPiService().getQiniuTOken("630091", StringUtils.getJsonToString(object));
    }

    /**
     * 上传单图片
     *
     * @param callBack
     */
    public void uploadSinglePic(final QiNiuCallBack callBack, final String filePath) {

        getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(context) {
            @Override
            protected void onSuccess(QiniuGetTokenModel mo, String SucMessage) {
                if (mo == null || TextUtils.isEmpty(mo.getUploadToken()) || TextUtils.isEmpty(filePath)) {
                    if (callBack != null) {
                        callBack.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                    }
                    return;
                }
                String token = mo.getUploadToken();
                uploadSingle(callBack, filePath, token);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                if (callBack != null) {
                    callBack.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                }
            }

            @Override
            protected void onFinish() {

            }
        });

    }


    /**
     * 多图片上传
     *
     * @param dataList
     * @param listListener
     */
    public void upLoadListPic(final List<String> dataList, final upLoadListImageListener listListener) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(context) {
            @Override
            protected void onSuccess(QiniuGetTokenModel data, String SucMessage) {
                if (data == null || TextUtils.isEmpty(data.getUploadToken())) {
                    if (listListener != null) {
                        listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                    }
                    return;
                }
                upLoadListPic(0, dataList, data.getUploadToken(), listListener);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                if (listListener != null) {
                    listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                }
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    /**
     * 递归实现多图片上传
     *
     * @param dataList
     * @param token
     * @param listListener
     */
    public void upLoadListPic(int index, final List<String> dataList, final String token, final upLoadListImageListener listListener) {
        this.upLoadListIndex = index;
        if (TextUtils.isEmpty(token)) {
            if (listListener != null) {
                listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
            }
            return;
        }

        mSubscription.add(Observable.just(dataList.get(upLoadListIndex))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(s -> mImageCompressInterface.onCompress(context, s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {
                    if (bytes == null || bytes.length == 0) {
                        if (listListener != null) {
                            listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                        }
                        return;
                    }
                    uploadSingle(new QiNiuCallBack() {
                        @Override
                        public void onSuccess(String key) {
                            if (listListener != null) {
                                listListener.onChange(upLoadListIndex, key);
                            }
                            if (upLoadListIndex < dataList.size() - 1) {
                                upLoadListIndex++;
                                upLoadListPic(upLoadListIndex, dataList, token, listListener);
                            } else {
                                upLoadListIndex = 0;
                                if (listListener != null) {
                                    listListener.onSuccess();
                                }

                            }
                        }

                        @Override
                        public void onFal(String info) {
                            if (listListener != null) {
                                listListener.onFal(info);
                            }
                        }
                    }, bytes, token);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (listListener != null) {
                            listListener.onError(CdApplication.getContext().getString(R.string.error_unknown));
                        }
                    }
                }));

    }

    /**
     * 图片单张上传
     *
     * @param callBack
     * @param
     */
    public void uploadSingle(final QiNiuCallBack callBack, byte[] data, String token) {

        Configuration config = new Configuration.Builder().build();
        UploadManager uploadManager = new UploadManager(config);
        String key = ANDROID + timestamp() + ".jpg";

        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(final String key, final ResponseInfo info, final JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info != null && info.isOK()) {
                            if (callBack != null) {
                                Observable.just("")
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<String>() {
                                            @Override
                                            public void accept(String s) throws Exception {
                                                callBack.onSuccess(key);
                                            }
                                        });
                            }

                        } else {
                            if (callBack != null) {
                                Observable.just(CdApplication.getContext().getString(R.string.qiniu_token_error))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<String>() {
                                            @Override
                                            public void accept(String s) throws Exception {
                                                callBack.onFal(s);
                                            }
                                        });
                            }
                            Log.i("QiNiu", "Upload Fail");
                            Log.i("QiNiu", "key=" + key);
                            Log.i("QiNiu", "res=" + res);
                            Log.i("QiNiu", "info=" + info);
                        }
                    }
                }, null);

    }

    /**
     * 清除
     */
    public void clear() {
        if (mSubscription != null) {
            mSubscription.dispose();
            mSubscription.clear();
        }
        context = null;
    }

    private static String timestamp() {
        String time = System.currentTimeMillis() + "";
        return "_" + time;
    }


    public interface QiNiuCallBack {
        void onSuccess(String key);

        void onFal(String info);
    }

    public interface QiNiuFileCallBack {

        void onSuccess(String key);

        void onFal(String info);

        void progress(String key, double percent);

        void complete();
    }

    /**
     * 用于实现图片压缩 把图片压缩后返回byte[]用于上传
     */
    public interface ImageCompressInterface {
        byte[] onCompress(Context context, String path);
    }

    /**
     * 多图片上传
     */
    public interface upLoadListImageListener {

        void onChange(int index, String url);

        void onSuccess();

        void onFal(String info);

        void onError(String info);
    }

    /**
     * 默认图片压缩实现
     */
    class DeflatCompreess implements ImageCompressInterface {
        @Override
        public byte[] onCompress(Context context, String path) {
            return BitmapUtils.compressLogoImage(path);
        }
    }


    /**
     * 上传单视频
     *
     * @param callBack
     */
    public void uploadSingleVideo(final QiNiuFileCallBack callBack, final String filePath) {

        getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(context) {
            @Override
            protected void onSuccess(QiniuGetTokenModel mo, String SucMessage) {
                if (mo == null || TextUtils.isEmpty(mo.getUploadToken()) || TextUtils.isEmpty(filePath)) {
                    if (callBack != null) {
                        callBack.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                    }
                    return;
                }
                String token = mo.getUploadToken();

                isCancelled = false;

                uploadVideo(callBack, filePath, token);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                if (callBack != null) {
                    callBack.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                }
            }

            @Override
            protected void onFinish() {

            }
        });

    }

    /**
     * 单个视频上传
     *
     * @param callBack
     * @param url
     */
    public void uploadVideo(final QiNiuFileCallBack callBack, final String url, final String token) {

        Configuration config = new Configuration.Builder().build();

        final UploadManager uploadManager = new UploadManager(config);

        String key = ANDROID + timestamp() + "_video" + ".mp4";
        //这个就是上传七牛的文件的名称  名字
        File file = new File(url);
        key = file.getName();
        key = key.replace(" ", "_");//如果包含空格就沒办法正常播放了

        uploadManager.put(url, key, token, (key12, info, response) -> {

            //res包含hash、key等信息，具体字段取决于上传策略的设置
            if (info != null && info.isOK()) {
                if (callBack != null) {

                    Observable.just("")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> callBack.onSuccess(key12));
                }

            } else {
                if (callBack != null) {
                    Observable.just(CdApplication.getContext().getString(R.string.upload_video_fail))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> callBack.onFal(s));
                }


            }


        }, new UploadOptions(null, null, false, (key13, percent) -> {
            Log.e("qiniu key13 = ", key13 + ": " + percent);
            if (callBack != null) {
                callBack.progress(key13, percent);
            }


        }, () -> isCancelled));

    }


    /**
     * 上传单个文件  任何类型的文件
     *
     * @param callBack
     */
    public void uploadSingleFile(final QiNiuFileCallBack callBack, final String filePath) {

        getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(context) {
            @Override
            protected void onSuccess(QiniuGetTokenModel mo, String SucMessage) {
                if (mo == null || TextUtils.isEmpty(mo.getUploadToken()) || TextUtils.isEmpty(filePath)) {
                    if (callBack != null) {
                        callBack.onFal(CdApplication.getContext().getString(R.string.upload_file_fail));
                    }
                    return;
                }
                String token = mo.getUploadToken();

                isCancelled = false;

                uploadFile(callBack, filePath, token);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                if (callBack != null) {
                    callBack.onFal(CdApplication.getContext().getString(R.string.upload_file_fail));
                }
            }

            @Override
            protected void onFinish() {

            }
        });

    }

    /**
     * 上传文件  任何类型的文件
     *
     * @param callBack
     * @param url
     */
    public void uploadFile(final QiNiuFileCallBack callBack, final String url, final String token) {

        Configuration config = new Configuration.Builder().build();

        final UploadManager uploadManager = new UploadManager(config);

        String key = ANDROID + timestamp() + "_video" + ".mp4";
        //这个就是上传七牛的文件的名称  名字
        File file = new File(url);
        key = file.getName();
        key = key.replace(" ", "_");//如果包含空格就沒办法正常播放了

        uploadManager.put(url, key, token, (key12, info, response) -> {

            //res包含hash、key等信息，具体字段取决于上传策略的设置
            if (info != null && info.isOK()) {
                if (callBack != null) {

                    Observable.just("")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> callBack.onSuccess(key12));
                }

            } else {
                if (callBack != null) {
                    Observable.just(CdApplication.getContext().getString(R.string.upload_video_fail))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> callBack.onFal(s));
                }


            }


        }, new UploadOptions(null, null, false, (key13, percent) -> {
            if (callBack != null) {
                callBack.progress(key13, percent);
            }

        }, () -> isCancelled));

    }

    // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
    public void cancelLoadVideo() {
        isCancelled = true;
    }

    /**
     * 多图片上传
     *
     * @param dataList
     * @param listListener
     */
    public void upLoadListVideo(final List<String> dataList, final UpLoadListFileListener listListener) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(context) {
            @Override
            protected void onSuccess(QiniuGetTokenModel data, String SucMessage) {
                if (data == null || TextUtils.isEmpty(data.getUploadToken())) {
                    if (listListener != null) {
                        listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                    }
                    return;
                }

                listListener.start();
                fileResultList.clear();

                upLoadListVideo(0, dataList, data.getUploadToken(), listListener);
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                if (listListener != null) {
                    listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
                }
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    /**
     * 递归实现多图片上传
     *
     * @param dataList
     * @param token
     * @param listListener
     */
    public void upLoadListVideo(int index, final List<String> dataList, final String token, final UpLoadListFileListener listListener) {
        this.upLoadListIndex = index;
        if (TextUtils.isEmpty(token)) {
            if (listListener != null) {
                listListener.onFal(CdApplication.getContext().getString(R.string.upload_pic_fail));
            }
            return;
        }

        /**
         * 用rxjava    just从0开始发送成功以后+1继续循环
         */
        mSubscription.add(Observable.just(dataList.get(upLoadListIndex))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(str -> QiNiuHelper.this.uploadVideo(new QiNiuFileCallBack() {
                    @Override
                    public void onSuccess(String key) {
                        complete();
                        // 添加结果集合
                        fileResultList.add(key);

                        if (listListener != null) {
                            listListener.onChange(upLoadListIndex, key);
                        }
                        if (upLoadListIndex < dataList.size() - 1) {
                            upLoadListIndex++;
                            upLoadListVideo(upLoadListIndex, dataList, token, listListener);
                        } else {
                            upLoadListIndex = 0;
                            if (listListener != null) {
                                listListener.onSuccess(fileResultList);
                            }

                        }

                    }

                    @Override
                    public void onFal(String info) {
                        if (listListener != null) {
                            listListener.onFal(info);
                        }
                        complete();
                    }

                    @Override
                    public void progress(String key, double percent) {
                        listListener.progress(key, percent);
                    }

                    @Override
                    public void complete() {

                    }
                }, str, token), (Consumer<Throwable>) throwable -> {
                    if (listListener != null) {
                        listListener.onError(CdApplication.getContext().getString(R.string.error_unknown));
                    }
                }));

    }

    /**
     * 多文件上传
     */
    public interface UpLoadListFileListener {

        void start();

        void onChange(int index, String url);

        void progress(String key, double percent);

        void onSuccess(List<String> result);

        void onFal(String info);

        void onError(String info);
    }


}
