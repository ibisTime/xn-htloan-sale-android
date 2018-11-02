package com.cdkj.wzcd.module.business.interview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.base.AbsBaseLoadActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.databinding.ActivityRoomBinding;
import com.cdkj.wzcd.model.ILiveVideoBean;
import com.cdkj.wzcd.model.RecModel;
import com.cdkj.wzcd.model.RecModelResult;
import com.cdkj.wzcd.tencent.IRoomView;
import com.cdkj.wzcd.tencent.RoomHelper;
import com.tencent.ilivesdk.core.ILiveRoomManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.CdRouteHelper.DATA_SIGN;

/**
 * Created by cdkj on 2018/6/14.
 */

public class RoomActivity extends AbsBaseLoadActivity implements IRoomView {

    private RoomHelper helper;
    private ActivityRoomBinding mBinding;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;//相机id  默认打开的是前置摄像头
    private int roomId;
    private long startTime;
    private Call<BaseResponseModel<RecModel>> iLiveVoide;
    private String streamId="";

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
        if (roomId >= 1 && roomId <= 10000000) {
            helper.createRoom(roomId);
            LogUtil.E("房间id为: " + roomId);
        } else {
            ToastUtil.show(this, "请输正确的入房间号(1~10000000)");

        }
    }

    private void initOnclick() {

        mBinding.btnRecord.setOnClickListener(view -> {
            startRec();
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

    /**
     * 开始录制
     */
    public void startRec() {

        HashMap<String, String> map = new HashMap<>();
        map.put("roomId", roomId + "");
        iLiveVoide = RetrofitUtils.createApi(MyApiServer.class).getPutStrime("632951", StringUtils.getJsonToString(map));
        showLoadingDialog();
        iLiveVoide.enqueue(new BaseResponseModelCallBack<RecModel>(this) {
            @Override
            protected void onSuccess(RecModel data, String SucMessage) {

                String result = data.getResult();
                RecModelResult recModelResult = JSON.parseObject(result, new TypeReference<RecModelResult>() {
                });
                if (recModelResult.getCode() == 0) {
                    mBinding.btnRecord.setEnabled(false);
                    streamId = data.getStreamId();
                } else {
                    UITipDialog.showFail(RoomActivity.this, "录制失败请点击重试");
                    mBinding.btnRecord.setEnabled(true);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    public void onEnterRoom() {
        LogUtil.E("onEnterRoom()  创建房间成功");
        ToastUtil.show(this, "创建房间成功");
        startTime = System.currentTimeMillis();
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

        ILiveVideoBean bean = new ILiveVideoBean();
//        bean.setVideoUrl("http://1257046543.vod2.myqcloud.com/c78eb187vodcq1257046543/e435427d5285890782240300405/f0.mp4");
        bean.setRoomId(roomId + "");
        bean.setStreamId(streamId + "");
        EventBus.getDefault().post(bean);
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
