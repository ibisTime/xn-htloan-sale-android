package com.cdkj.wzcd.view.popup;

import android.content.Context;

import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.util.UserHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/7/10.
 */

public class SearchHelper {

    private static Call call;

    public static void getUserRequest(Context context, String realName, searchUserInterface listInterface){
        Map<String, String> map = new HashMap<>();
        map.put("type", "P");
        map.put("realName", realName);
        map.put("roleCode", UserHelper.YWY);
        map.put("limit", "20");
        map.put("start", "1");

        call = RetrofitUtils.createApi(MyApiServer.class).getUserList("630065", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<ResponseInListModel<UserModel>>(context) {

            @Override
            protected void onSuccess(ResponseInListModel<UserModel> data, String SucMessage) {
                if (data == null || data.getList() == null || data.getList().size() == 0){
                    UITipDialog.showInfo(context, "无搜索内容");
                    return;
                }


                if (listInterface != null)
                    listInterface.onSuccess(data.getList());
            }

            @Override
            protected void onReqFailure(String errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);

                if (listInterface != null)
                    listInterface.onReqFailure(errorCode, errorMessage);
            }

            @Override
            protected void onFinish() {
                clearCall();
            }
        });
    }

    public interface searchUserInterface {

        void onSuccess(List<UserModel> list);
        void onReqFailure(String errorCode, String errorMessage);

    }

    private static void clearCall(){
        if (call != null)
            call.cancel();
    }
}
