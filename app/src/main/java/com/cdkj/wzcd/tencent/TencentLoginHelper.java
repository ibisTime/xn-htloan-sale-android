package com.cdkj.wzcd.tencent;

import android.content.Context;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.MyCdConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.TencentSignModel;
import com.cdkj.wzcd.tencent.logininterface.TencentLoginInterface;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/6/14.
 */

public class TencentLoginHelper implements ILiveLoginManager.TILVBStatusListener {

    private Context context;
    private TencentLoginInterface loginInterface;

    public TencentLoginHelper(Context context, TencentLoginInterface loginInterface) {
        this.context = context;
        this.loginInterface = loginInterface;
    }


    /**
     * 获取腾讯签名
     */
    public void login(String roomid) {
        if (context == null) {  //没有登录
            return;
        }

        if (!SPUtilHelper.isLoginNoStart()) {  //没有登录
            return;
        }

        Map map = new HashMap<>();

        map.put("userId", SPUtilHelper.getUserId());
        map.put("token", SPUtilHelper.getUserToken());
        map.put("systemCode", MyCdConfig.SYSTEM_CODE);
        map.put("companyCode", MyCdConfig.COMPANY_CODE);

        Call<BaseResponseModel<TencentSignModel>> call = RetrofitUtils.createApi(MyApiServer.class).getTencentSign("630800", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<TencentSignModel>(context) {
            @Override
            protected void onSuccess(TencentSignModel data, String SucMessage) {


//                getRoomId(data.getSign());
                loginTencent(data.getSign(),roomid);
                //这里放开
//                loginTencent(MyCdConfig.User_Sig);
            }


            @Override
            protected void onFinish() {
                call.cancel();
            }
        });
    }

    public void getRoomId(String sign) {
//        RoomActivity.open(this, Integer.parseInt(roomId));
        Call<BaseResponseModel<String>> roomId = RetrofitUtils.createApi(MyApiServer.class).getRoomId("632950","{}");

        roomId.enqueue(new BaseResponseModelCallBack<String>(context) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                loginTencent(sign,data);
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    private void loginTencent(String userSig,String roomId){
        ILiveLoginManager.getInstance().iLiveLogin(SPUtilHelper.getUserId(), userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                loginInterface.onLoginSDKSuccess(roomId);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                loginInterface.onLoginSDKFailed(module, errCode, errMsg);
            }
        });
    }

    @Override
    public void onForceOffline(int error, String message) {
        loginInterface.updateLoginState(false);
    }
}
