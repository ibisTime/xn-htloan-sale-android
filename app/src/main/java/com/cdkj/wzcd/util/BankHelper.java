package com.cdkj.wzcd.util;

import android.content.Context;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.model.BankModel;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.view.item.MyItemNormalLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/30.
 */

public class BankHelper {

    private Call call;
    private Context mContext;

    public BankHelper(Context context){
        mContext = context;
    }

    public void getValueOnTheKey(String bankCode, MyItemNormalLayout myItemNormalLayout, BankInterface bankInterface){

        Map<String, String> map = new HashMap<>();

        map.put("token", SPUtilHelper.getUserToken());
        map.put("bankCode", bankCode);
        map.put("bankName", "");
        map.put("channelType", "");
        map.put("status", "");
        map.put("paybank", "");

        call = RetrofitUtils.getBaseAPiService().getBackModel("802116", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseListCallBack<BankModel>(mContext) {

            @Override
            protected void onSuccess(List<BankModel> data, String SucMessage) {
                if (data == null || data.size() == 0)
                    return;

                if (myItemNormalLayout != null)
                    myItemNormalLayout.setContext(data.get(0).getBankName());

                if (bankInterface != null)
                    bankInterface.onSuccess(data.get(0));
            }

            @Override
            protected void onFinish() {
                clearCall();
            }
        });
    }

    public interface BankInterface{

        void onSuccess(BankModel data);

    }

    private void clearCall(){
        if (call != null)
            call.cancel();
    }
}
