package com.cdkj.wzcd.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.R;
import com.cdkj.wzcd.api.MyApiServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/5/30.
 */

public class DataRequestActivity extends BaseActivity {

    public static int DATA_DICTIONARY = 0;
    public static int SYSTEM_PARAMETER = 1;

    private String mKey;
    private int mType;

    public static void launch(Activity activity, String key, int type) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("type", type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_request);
        initVar();
    }

    private void initVar() {
        if (getIntent() != null) {
            mKey = getIntent().getStringExtra("key");
            mType = getIntent().getIntExtra("type", 0);
        }
    }

    private void getDataDictionary(){
        Map<String, String> map = new HashMap<>();

        map.put("dkey", "");
        map.put("orderColumn", "");
        map.put("orderDir", "");
        map.put("parentKey", mKey);
        map.put("type", "");
        map.put("updater", "");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getDataDictionary("630036", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<DataDictionary>(this) {

            @Override
            protected void onSuccess(List<DataDictionary> data, String SucMessage) {

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

}
