package com.cdkj.baselibrary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.interfaces.CameraPhotoListener;
import com.cdkj.baselibrary.utils.CameraHelper;
import com.cdkj.baselibrary.utils.ToastUtil;

/**
 * 打开相机 相册 图片裁剪 功能
 */
public class FileSelectActivity extends Activity implements View.OnClickListener, CameraPhotoListener {

    private TextView tv_file;// 文件选取
    private TextView tv_cancle;// 取消
    private View empty_view;// 取消

    private boolean isSplit = false;//执行相机或拍照后是否需要裁剪 默认不需要

    public static final int SHOWPIC = 1; //显示拍照按钮
    public static final int SHOWALBUM = 2;//显示相册

    private CameraHelper cameraHelper;

    /**
     * @param activity
     * @param photoid
     * @param showType 显示的按钮
     * @param isSplit  是否裁剪
     */
    public static void launch(Activity activity, int photoid, int showType, boolean isSplit) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, FileSelectActivity.class);
        intent.putExtra("showType", showType);
        intent.putExtra("isSplit", isSplit);
        activity.startActivityForResult(intent, photoid);
    }

    public static void launch(Activity activity, int requestCode, boolean isSplit) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, FileSelectActivity.class);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void launchFragment(Fragment fragment, int photoid) {
        if (fragment == null || fragment.getActivity() == null) {
            return;
        }
        Intent intent = new Intent(fragment.getActivity(), FileSelectActivity.class);
        fragment.startActivityForResult(intent, photoid);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
        initLayout();
        initVar();
    }

    private void initVar() {
        cameraHelper = new CameraHelper(this, this);
    }

    protected void initLayout() {

        tv_file = (TextView) findViewById(R.id.tv_file);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        empty_view = findViewById(R.id.empty_view);

        tv_file.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        empty_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_file) {
            cameraHelper.startFile();
        } else if (i == R.id.empty_view || i == R.id.tv_cancle) {
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //拍照回调
        cameraHelper.onActivityResult(requestCode, resultCode, data);
    }


    //权限申请回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraHelper != null) {
            cameraHelper.clear();
        }
    }

    @Override
    public void onPhotoSuccessful(int code, String path) {
        setResult(Activity.RESULT_OK, new Intent().putExtra(CameraHelper.staticPath, path));
        finish();
    }

    @Override
    public void onPhotoFailure(int code, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.show(this, msg);
        }
        finish();
    }

    @Override
    public void noPermissions(int code) {
        ToastUtil.show(this, "没有获取相关权限功能无法使用");
    }
}
