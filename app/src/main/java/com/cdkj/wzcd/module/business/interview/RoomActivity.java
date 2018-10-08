package com.cdkj.wzcd.module.business.interview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.databinding.ActivityRoomBinding;
import com.cdkj.wzcd.tencent.IRoomView;
import com.cdkj.wzcd.tencent.RoomHelper;
import com.cdkj.wzcd.util.FileUtil;
import com.tencent.av.TIMAvManager;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILivePushOption;
import com.tencent.ilivesdk.core.ILiveRecordOption;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.data.ILivePushRes;
import com.tencent.ilivesdk.data.ILivePushUrl;

import java.io.File;
import java.util.List;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/14.
 */

public class RoomActivity extends AbsBaseLoadActivity implements IRoomView {

    private RoomHelper helper;
    private ActivityRoomBinding mBinding;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;//相机id  默认打开的是前置摄像头
    private int roomId;

    public static void open(Context context, int roomId) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(DATA_SIGN, roomId);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_room, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

//        TXLivePusher mLivePusher = new TXLivePusher(getActivity());
//        mLivePushConfig = new TXLivePushConfig();
//        mLivePusher.setConfig(mLivePushConfig);
//        mLivePusher.startScreenCapture();

        mBaseBinding.titleView.setMidTitle("面签");
        initOnclick();

        helper = new RoomHelper(this);
        // 设置渲染控件
        helper.setRootView(mBinding.avRootView);

        if (getIntent() == null || getIntent().getExtras() == null)
            return;

        roomId = getIntent().getIntExtra(DATA_SIGN, -1);
        LogUtil.E("pppppp房间号id为:" + roomId);
        if (roomId >= 1 && roomId <= 10000000)
            helper.createRoom(roomId);
        else
            ToastUtil.show(this, "请输正确的入房间号(1~10000000)");
    }

    private void initOnclick() {

        mBinding.btnRecord.setOnClickListener(view -> {
            String btnStr = mBinding.btnRecord.getText().toString();
            if (TextUtils.equals("开始录制", btnStr)) {
                mBinding.btnRecord.setText("停止录制");
                mBinding.btnRecord.setEnabled(false);//变为不可点击状态  避免用户连续点击 导致还没开始录制就暂停的问题
//                startRecord();
                startPush(true);
            } else {
                mBinding.btnRecord.setText("开始录制");
                mBinding.btnRecord.setEnabled(false);
//                stopRecord();
                stopPush();
            }
        });

        mBinding.btnCameraSwitch.setOnClickListener(view -> {

            if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            } else {
                cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            }
            ILiveRoomManager.getInstance().switchCamera(cameraId);
        });
    }

    private void startPush(boolean bRecord) {
        ILivePushOption.RecordFileType recordFileType = bRecord ?
                ILivePushOption.RecordFileType.RECORD_HLS_FLV_MP4 : ILivePushOption.RecordFileType.NONE;
        ILivePushOption option = new ILivePushOption()
                .encode(ILivePushOption.Encode.HLS_AND_RTMP)         // 旁路直播协议类型
                .setRecordFileType(recordFileType)      // 录制文件格式
                //手动推流自动录制时，如果需要后台识别特定的录制文件，用户可以通过这个字段做区分。
                // (使用这个字段时，控制台的“自动旁路直播”开关必须关闭)
                .setRecordId(123456);

        ILiveRoomManager.getInstance().startPushStream(option, new ILiveCallBack<ILivePushRes>() {
            @Override
            public void onSuccess(ILivePushRes data) {
                //录制成功
                LogUtil.E("pppppp录制成功");
                if (null != data.getUrls()) {
                    // 遍历推流类型及地址
                    for (ILivePushUrl url : data.getUrls()) {
                        // 处理播放地址

                        LogUtil.E("pppppp地址为" + url.getUrl());
                    }
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                // 处理推流失败
                LogUtil.E("pppppp处理推流失败" + errMsg);
            }
        });
        mBinding.btnRecord.setEnabled(true);//初始化完成变为可点击状态
    }

    private void stopPush() {
        ILiveRoomManager.getInstance().stopPushStream(0, // 直播码模式下填0即可
                new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        // 停止成功
                        LogUtil.E("pppppp停止成功推流");
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        // 停止失败
                        LogUtil.E("pppppp停止推流失败");
                    }
                });
        mBinding.btnRecord.setEnabled(true);//初始化完成变为可点击状态
    }

    /**
     * 录制
     */
    private void startRecord() {
        String path = Environment.getExternalStorageDirectory().toString();
        File videoFile = FileUtil.createNewFile(path + "/htwt/shipin.mp4");
        ILiveRecordOption iLiveRecordOption = new ILiveRecordOption();
        iLiveRecordOption.recordType(TIMAvManager.RecordType.VIDEO);
        iLiveRecordOption.fileName(videoFile.getName());
        iLiveRecordOption.addTag("标签");
        iLiveRecordOption.classId(0);

        ILiveRoomManager.getInstance().startRecordVideo(iLiveRecordOption, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                LogUtil.E("录制成功" + data.toString());
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                LogUtil.E("录制失败module:" + module + " errCode:" + errCode + " errMsg:" + errMsg);
            }
        });
        mBinding.btnRecord.setEnabled(true);//初始化完成变为可点击状态

    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        ILiveRoomManager.getInstance().stopRecordVideo(new ILiveCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                for (String item : data) {
                    LogUtil.E("停止成功" + item);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                LogUtil.E("停止异常" + errMsg);
            }
        });
        mBinding.btnRecord.setEnabled(true);//初始化完成变为可点击状态
    }

    @Override
    public void onEnterRoom() {
        LogUtil.E("onEnterRoom()  创建房间成功");
        ToastUtil.show(this, "创建房间成功");
    }


    @Override
    public void onEnterRoomFailed(String module, int errCode, String errMsg) {
        LogUtil.E("onEnterRoomFailed()  创建房间失败：" + errCode + "::::" + errMsg);
        ToastUtil.show(this, "创建房间失败：" + errCode + "::::" + errMsg);
    }

    @Override
    public void onQuitRoomSuccess() {
        LogUtil.E("onQuitRoomSuccess()  退出房间成功");
        ToastUtil.show(this, "退出房间成功");
    }

    @Override
    public void onQuitRoomFailed(String module, int errCode, String errMsg) {
        LogUtil.E("onQuitRoomFailed()  退出房间失败：" + errCode + "::::" + errMsg);
        ToastUtil.show(this, "退出房间失败：" + errCode + "::::" + errMsg);
    }

    @Override
    public void onRoomDisconnect(int errCode, String errMsg) {
        LogUtil.E("onRoomDisconnect()  连接断开：" + errCode + "::::" + errMsg);
        ToastUtil.show(this, "连接断开：" + errCode + "::::" + errMsg);
    }

    // 处理Activity事件
    public void onPause() {
        super.onPause();
        helper.onPause();
    }

    // 处理Activity事件
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.quitRoom();
    }
}
