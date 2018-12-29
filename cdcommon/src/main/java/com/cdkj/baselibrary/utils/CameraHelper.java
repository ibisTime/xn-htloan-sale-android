package com.cdkj.baselibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cdkj.baselibrary.CdApplication;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.interfaces.CameraPhotoListener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 拍照、相册辅助类
 * 使用注意 需要在启动页面中调用onActivityResult处理拍照回调
 * 调用onRequestPermissionsResult处理权限回调
 * 调用clear方法防止内存泄漏
 * Created by cdkj on 2017/11/7.
 */
//由CameraHelper2 修改而来 通过反射打开Activity从而兼容Fragment和Activity
//TODO 裁剪接口抽取
public class CameraHelper {

    public final static int CAPTURE_PHOTO_CODE = 3;//相机
    public final static int CAPTURE_WALBUM_CODE = 4;//相册
    public final static int CAPTURE_ZOOM_CODE = 5;//裁剪
    public final static int CAPTURE_FILE_CODE = 6;//裁剪

    private int mRequestCode = -1;//用于记录是相机还是相册裁剪

    private Object mContext;

    private Uri imageUrl;
    protected CompositeDisposable mSubscription;
    private PermissionHelper mPreHelper;//权限请求

    public final static String staticPath = "imgSelect";
    public final static String cropPath = "cropPath";
    private String photoPath;//拍照图片路径
    private boolean isSplit = true;//执行相机或拍照后是否需要裁剪 默认裁剪

    private CameraPhotoListener mCameraPhotoListener;

    private CamerahelperCropInterface mCamerahelperCropInterface;//裁剪接口

    //需要的权限
    private String[] needLocationPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    public void setSplit(boolean split) {
        isSplit = split;
    }

    /**
     * 设置裁剪接口
     *
     * @param mCamerahelperCropInterface
     */
    public void setmCamerahelperCropInterface(CamerahelperCropInterface mCamerahelperCropInterface) {
        this.mCamerahelperCropInterface = mCamerahelperCropInterface;
    }

    /**
     * 默认启动裁剪
     *
     * @param cameraPhotoListener 获取图片监听
     */
    public CameraHelper(@NonNull Object object, @NonNull CameraPhotoListener cameraPhotoListener) {
        this.mContext = object;
        checkCallingObjectSuitability(object);
        this.isSplit = true;
        this.mCameraPhotoListener = cameraPhotoListener;
        mSubscription = new CompositeDisposable();
        mPreHelper = new PermissionHelper(object);
        mCamerahelperCropInterface = new defaultCropInterface();
    }

    /**
     * 判断权限并启动相册
     *
     * @return
     */
    public void startAlbum() {
        if (isNeedRequestPremission()) {
            requestPermissions(CAPTURE_WALBUM_CODE);
            return;
        }
        startImageFromAlbum();
    }

    /**
     * 判断权限并启动相机
     *
     * @return
     */
    public void startCamera() {
        if (isNeedRequestPremission()) {
            requestPermissions(CAPTURE_PHOTO_CODE);
            return;
        }
        startImageFromCamera();
    }

    /**
     * 判断权限并启动文件
     *
     * @return
     */
    public void startFile() {
        if (isNeedRequestPremission()) {
            requestPermissions(CAPTURE_FILE_CODE);
            return;
        }
        startFileSelect();
    }


    /**
     * 请求权限
     *
     * @param type 判断是相册还是相机
     */
    private void requestPermissions(final int type) {
        mRequestCode = type;
        mPreHelper.requestPermissions(new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                switch (type) {
                    case CAPTURE_PHOTO_CODE:
                        startImageFromCamera();
                        break;
                    case CAPTURE_WALBUM_CODE:
                        startImageFromAlbum();
                        break;
                    case CAPTURE_FILE_CODE:
                        startFileSelect();
                        break;
                }
            }

            @Override
            public void doAfterDenied(String... permission) {
                mCameraPhotoListener.noPermissions(type);
            }
        }, needLocationPermissions);
    }


    /**
     * 判断是否存在可用相机
     *
     * @return
     */
    public boolean hasCamera() {
        if (getContextActivity(mContext) == null) {
            return false;
        }
        PackageManager packageManager = getContextActivity(mContext).getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    // 调相机拍照
    private void startImageFromCamera() {

        mRequestCode = CAPTURE_PHOTO_CODE;

        if (!hasCamera())  //判断有没有可用相机
        {
            mCameraPhotoListener.onPhotoFailure(CAPTURE_PHOTO_CODE, CdApplication.getContext().getString(R.string.no_camera));
            return;
        }


        if (FileUtils.isExternalStorageWritable()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = FileUtils.saveAlbumPic("cream");
            imageUrl = FileProviderHelper.getUriForFile(getContextActivity(mContext), file);
            photoPath = file.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivity(intent, CAPTURE_PHOTO_CODE);
        } else {
            mCameraPhotoListener.onPhotoFailure(CAPTURE_PHOTO_CODE, CdApplication.getContext().getString(R.string.no_sd_card));
        }
    }


    public Uri getImageUrl() {
        return imageUrl;
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            mCameraPhotoListener.onPhotoFailure(requestCode, "");
            return;
        }
        switch (requestCode) {
            case CAPTURE_WALBUM_CODE:// 相册
                mRequestCode = CAPTURE_WALBUM_CODE;
                abumNext(data);
                break;
            case CAPTURE_PHOTO_CODE:// 拍照
                mRequestCode = CAPTURE_PHOTO_CODE;
                cameraNext();
                break;
            case CAPTURE_ZOOM_CODE:  //图片裁剪
                zoomNext(data, mRequestCode);
                break;
            case CAPTURE_FILE_CODE:  //文件选择
                fileNext(data);
                break;
            default:
                break;
        }
    }

    /**
     * 裁剪
     *
     * @param data
     */
    private void zoomNext(Intent data, final int requestCode) {

        if (data == null || TextUtils.isEmpty(data.getStringExtra(cropPath))) {
            mCameraPhotoListener.onPhotoFailure(requestCode, CdApplication.getContext().getString(R.string.get_pic_fail));
            return;
        }
        mCameraPhotoListener.onPhotoSuccessful(requestCode, data.getStringExtra(cropPath));

/*        Bundle extras = data.getExtras();
        Bitmap photo = extras.getParcelable("data");

        mSubscription.add(Observable.just(photo)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<Bitmap, String>() {
                    @Override
                    public String apply(@NonNull Bitmap bitmap) throws Exception {
                        String path = BitmapUtils.saveBitmapFile(bitmap, "split");  //图片名称
                        return path;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String path) throws Exception {
                        mCameraPhotoListener.onPhotoSuccessful(requestCode, path);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCameraPhotoListener.onPhotoFailure(requestCode, "图片获取失败");
                    }
                }));*/
    }

    /**
     * 相机
     */
    private void cameraNext() {
        if (isSplit) {
            if (!isNeedUriAdapte()) {
                startCrop(imageUrl.getPath());
            } else {
                startCrop(photoPath);
            }
        } else {
            try {
                if (!CameraHelper.isNeedUriAdapte()) {
                    File file = new File(imageUrl.getPath());
                    mCameraPhotoListener.onPhotoSuccessful(CAPTURE_PHOTO_CODE, imageUrl.getPath());
                    //通知相册更新
                    CdApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(file)));

                } else {
                    File file = new File(photoPath);
                    mCameraPhotoListener.onPhotoSuccessful(CAPTURE_PHOTO_CODE, photoPath);
                    //通知相册更新
                    CdApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(file)));

                }

            } catch (Exception e) {
                mCameraPhotoListener.onPhotoFailure(CAPTURE_PHOTO_CODE, "文件获取失败");
            }
        }
    }

    /**
     * 相册
     *
     * @param data
     */
    private void abumNext(Intent data) {
        if (data == null) return;
        Uri imageUri = data.getData();

        if ("Xiaomi".equals(Build.MANUFACTURER) || SystemUtils.isMIUI())   //小米相册兼容代码
        {
            String imgP = setPhotoForMiuiSystem(data);

            if (imageUri == null) {
                mCameraPhotoListener.onPhotoFailure(CAPTURE_WALBUM_CODE, CdApplication.getContext().getString(R.string.get_pic_fail));
                return;
            }
            if (isSplit) {
//                startPhotoZoom(new File(imgP));
                startCrop(imgP);
                return;
            }

            if (!TextUtils.isEmpty(imgP)) {
                mCameraPhotoListener.onPhotoSuccessful(CAPTURE_WALBUM_CODE, imgP);
            }
            return;
        }
        if (imageUri == null) {
            mCameraPhotoListener.onPhotoFailure(CAPTURE_WALBUM_CODE, CdApplication.getContext().getString(R.string.get_pic_fail));
            return;
        }

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContextActivity(mContext).getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        if (isSplit) {
            startCrop(picturePath);
            return;
//            startPhotoZoom(new File(imageUri.getPath()));
        }

        mCameraPhotoListener.onPhotoSuccessful(CAPTURE_WALBUM_CODE, picturePath);


    }


    /**
     * 相册
     *
     * @param data
     */
    private void fileNext(Intent data) {
        if (data == null) return;
        Uri imageUri = data.getData();

        if ("Xiaomi".equals(Build.MANUFACTURER) || SystemUtils.isMIUI())   //小米相册兼容代码
        {
            String imgP = setPhotoForMiuiSystem(data);

            if (imageUri == null) {
                mCameraPhotoListener.onPhotoFailure(CAPTURE_FILE_CODE, CdApplication.getContext().getString(R.string.get_pic_fail));
                return;
            }
            if (isSplit) {
//                startPhotoZoom(new File(imgP));
                startCrop(imgP);
                return;
            }

            if (!TextUtils.isEmpty(imgP)) {
                mCameraPhotoListener.onPhotoSuccessful(CAPTURE_FILE_CODE, imgP);
            }
            return;
        }
        if (imageUri == null) {
            mCameraPhotoListener.onPhotoFailure(CAPTURE_FILE_CODE, CdApplication.getContext().getString(R.string.get_pic_fail));
            return;
        }

        Uri uri = data.getData();
        //==================
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContextActivity(mContext).getContentResolver().query(uri,
                filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            final int column_index = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(column_index);
            cursor.close();
            mCameraPhotoListener.onPhotoSuccessful(CAPTURE_FILE_CODE, picturePath);
            return;
        }
        //==============================

        String path=null;
        if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
            path = uri.getPath();
//            tv.setText(path);
//            Toast.make
            mCameraPhotoListener.onPhotoSuccessful(CAPTURE_FILE_CODE, path);
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            path = getPath(getContextActivity(mContext), uri);
//            tv.setText(path);
//            Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
        } else {//4.4以下下系统调用方法
            path = getRealPathFromURI(uri);
//            tv.setText(path);
//            Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
        }
        mCameraPhotoListener.onPhotoSuccessful(CAPTURE_FILE_CODE, path);

    }



    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContextActivity(mContext).getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {


        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {


                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }


                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {


        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};


        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * MIUI系统的相册选择
     *
     * @param data
     */
    private String setPhotoForMiuiSystem(Intent data) {
        Uri localUri = data.getData();
        String scheme = localUri.getScheme();
        String imagePath = "";
        if ("content".equals(scheme)) {
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContextActivity(mContext).getContentResolver().query(localUri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            c.close();
            return imagePath;

        } else if ("file".equals(scheme)) {//小米4选择云相册中的图片是根据此方法获得路径
            imagePath = localUri.getPath();
        }
        return imagePath;
    }


    /**
     * 启动裁剪页面
     *
     * @param path
     */
    public void startCrop(String path) {
        mCamerahelperCropInterface.startCrop(mContext, mRequestCode, path);
    }

    /**
     * 系统裁剪图片方法实现 华为裁剪为圆形兼容处理
     *
     * @param uri
     */
    public void startPhotoZoom(File uri) {

        /*
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(uri), "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");

        if (Build.MODEL.contains("HUAWEI")) {//华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);

        startActivity(intent, CAPTURE_ZOOM_CODE);

    }


    /**
     * 7.0适配
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContextActivity(mContext).getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContextActivity(mContext).getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    // 调相册图片
    private void startImageFromAlbum() {
        /**
         * 下面这句话，与其它方式写是一样的效果，如果：
         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         * intent.setType(""image/*");设置数据类型
         * 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
         *
         */
        mRequestCode = CAPTURE_WALBUM_CODE;
        Intent intent = new Intent(Intent.ACTION_PICK, null);

        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivity(intent, CAPTURE_WALBUM_CODE);
    }

    // 调文件,可以选择所有文件
    private void startFileSelect() {
        /**
         * 下面这句话，与其它方式写是一样的效果，如果：
         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         * intent.setType(""image/*");设置数据类型
         * 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
         *
         */
        mRequestCode = CAPTURE_FILE_CODE;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivity(intent, CAPTURE_FILE_CODE);
    }

    /**
     * 获取权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (mPreHelper == null) return;
        mPreHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 清除
     */
    public void clear() {
        if (mSubscription != null) {
            mSubscription.dispose();
            mSubscription.clear();
        }
        this.mContext = null;
    }

    /**
     * 裁剪接口
     */
    public interface CamerahelperCropInterface {
        void startCrop(Object context, int requestCode, String path);//从Activity页面启动
    }


    /**
     * 默认裁剪实现
     */
    class defaultCropInterface implements CamerahelperCropInterface {
        @Override
        public void startCrop(Object context, int requestCode, String path) {

//            startActivity(intent, CAPTURE_ZOOM_CODE);

//            final Uri selectedUri = intent.getData();
//            UCrop uCrop = UCrop.of(selectedUri, Uri.fromFile(new File(getCacheDir(), "SampleCropImage")));
//
//            uCrop.withAspectRatio(16, 9)
//                    .start(this);

        }
    }


    /**
     * 检查传递Context是否合法
     *
     * @param object
     */
    private void checkCallingObjectSuitability(@Nullable Object object) {
        if (object == null) {
            throw new IllegalArgumentException("camera start object is null");
        }

        boolean isActivity = object instanceof android.app.Activity;
        boolean isSupportFragment = object instanceof android.support.v4.app.Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        if (!(isSupportFragment || isActivity || (isAppFragment && CameraHelper.isNeedRequestPremission()))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    /**
     * 是否需要进行权限申请
     *
     * @return
     */
    public static boolean isNeedRequestPremission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否需要7.0适配
     *
     * @return false 不需要
     */
    public static boolean isNeedUriAdapte() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 获取上下文
     *
     * @param object
     * @return
     */
    public Activity getContextActivity(Object object) {
        return PermissionHelper.getActivity(object);
    }

    /**
     * 通过反射调用startActivityForResult打开activity
     *
     * @param intent
     * @param requestCode
     */

    public void startActivity(Intent intent, int requestCode) {
        try {
            Method method = this.mContext.getClass().getMethod("startActivityForResult", new Class[]{Intent.class, Integer.TYPE});
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(this.mContext, new Object[]{intent, requestCode});
        } catch (Exception var2) {
            var2.printStackTrace();
            ToastUtil.show(getContextActivity(this.mContext), CdApplication.getContext().getString(R.string.error_unknown));
        }
    }


}
