package com.cdkj.wzcd.util;

import android.content.Context;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.view.item.MyItemNormalLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/30.
 */

public class DataDictionaryHelper {

    public static String credit_user_relation = "credit_user_relation";
    public static String credit_user_loan_role = "credit_user_loan_role";
    public static String budget_orde_biz_typer = "budget_orde_biz_typer";

    private Call call;
    private Context mContext;

    public DataDictionaryHelper(Context context){
        mContext = context;
    }

    public void getValueOnTheKey(String parentKey, String key, MyItemNormalLayout myItemNormalLayout, DataDictionaryInterface dataDictionaryInterface){
        Map<String, String> map = new HashMap<>();
        map.put("dkey", key);
        map.put("orderColumn", "");
        map.put("orderDir", "");
        map.put("parentKey", parentKey);
        map.put("type", "");
        map.put("updater", "");

        call = RetrofitUtils.createApi(MyApiServer.class).getDataDictionary("630036", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseListCallBack<DataDictionary>(mContext) {

            @Override
            protected void onSuccess(List<DataDictionary> data, String SucMessage) {
                if (data == null || data.size() == 0)
                    return;

                if (myItemNormalLayout != null)
                    myItemNormalLayout.setContext(data.get(0).getDvalue());

                if (dataDictionaryInterface != null)
                    dataDictionaryInterface.onSuccess(data.get(0));
            }

            @Override
            protected void onFinish() {
                clearCall();
            }
        });
    }

    public interface DataDictionaryInterface{

        void onSuccess(DataDictionary data);

    }

    private void clearCall(){
        if (call != null)
            call.cancel();
    }
}
