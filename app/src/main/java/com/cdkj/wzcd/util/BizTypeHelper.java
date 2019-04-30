package com.cdkj.wzcd.util;

import android.content.Context;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.wzcd.MainActivity;
import com.cdkj.wzcd.api.MyApiServer;
import com.cdkj.wzcd.model.ZXDetialsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by cdkj on 2018/6/5.
 */

public class BizTypeHelper {


    /**
     * //节点状态   如  待录入征信  风控审核....
     */
    public static String cdbiz_status = "cdbiz_status";//节点状态   如  待录入征信  风控审核....

    /**
     * //新车 / 二手车
     */
    public static String budget_orde_biz_typer = "budget_orde_biz_typer";//新车 / 二手车

    /**
     * 与贷款人关系  如:配偶...
     */
    public static String credit_user_relation = "credit_user_relation";//与贷款人关系  如:配偶...

    /**
     * 角色    如:贷款人本人...
     */
    public static String credit_user_loan_role = "credit_user_relation";//角色    如:贷款人本人...

    private static Call call;

    public static String getNameOnTheKey(String key, List<DataDictionary> data) {
        if (data == null) {
            return "";
        }
        for (DataDictionary dictionary : data) {
            if (TextUtils.equals(key, dictionary.getDkey()))
                return dictionary.getDvalue();
        }

        return "";
    }

    public static String getNameOnTheKey(String key) {
        if (MainActivity.BASE_BIZ_TYPE == null || MainActivity.BASE_BIZ_TYPE.size() == 0)
            return "";

        if (TextUtils.isEmpty(key))
            return "";

        for (DataDictionary dictionary : MainActivity.BASE_BIZ_TYPE) {
            if (TextUtils.equals(key, dictionary.getDkey()))
                return dictionary.getDvalue();
        }

        return "";
    }

    public static ArrayList<DataDictionary> getParentList(String parentkey) {
        if (MainActivity.BASE_BIZ_TYPE == null || MainActivity.BASE_BIZ_TYPE.size() == 0) {

            return null;
        }

        if (TextUtils.isEmpty(parentkey))
            return null;
        ArrayList<DataDictionary> parentList = new ArrayList<>();
        for (DataDictionary dictionary : MainActivity.BASE_BIZ_TYPE) {
            if (TextUtils.equals(parentkey, dictionary.getParentKey()))
                parentList.add(dictionary);
        }

        return parentList;
    }


    public static String getNameOnTheKey(String parentkey, String dKey) {
        if (MainActivity.BASE_BIZ_TYPE == null || MainActivity.BASE_BIZ_TYPE.size() == 0)
            return "";

        if (TextUtils.isEmpty(parentkey) || TextUtils.isEmpty(dKey))
            return "";
        ArrayList<DataDictionary> parentList = new ArrayList<>();
        for (DataDictionary dictionary : MainActivity.BASE_BIZ_TYPE) {
            if (TextUtils.equals(parentkey, dictionary.getParentKey()))
                parentList.add(dictionary);
        }
        for (DataDictionary dataDictionary : parentList) {
            if (TextUtils.equals(dKey, dataDictionary.getDkey()))
                return dataDictionary.getDvalue();
        }

        return "";
    }

    public static void getBizTypeBaseDataRequest(Context context, String parentKey, BizTypeInterface listInterface) {
        Map<String, String> map = new HashMap<>();
        map.put("dkey", "");
        map.put("orderColumn", "");
        map.put("orderDir", "");
        map.put("parentKey", parentKey);
        map.put("type", "");
        map.put("updater", "");

        call = RetrofitUtils.createApi(MyApiServer.class).getDataDictionary("630036", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseListCallBack<DataDictionary>(context) {

            @Override
            protected void onSuccess(List<DataDictionary> data, String SucMessage) {
                if (data == null || data.size() == 0)
                    return;

                if (listInterface != null)
                    listInterface.onSuccess(data);
            }

            @Override
            protected void onFinish() {
                clearCall();
            }
        });

    }

    public interface BizTypeInterface {

        void onSuccess(List<DataDictionary> list);

        void onReqFailure(String errorCode, String errorMessage);

    }

    private static void clearCall() {
        if (call != null)
            call.cancel();
    }


    // =============================interview（面签资料）===================================
    public static String bank_video = "bank_video";    //银行视频
    public static String bank_contract = "bank_contract";    //银行合同
    public static String bank_photo = "bank_photo";//银行面签照片
    public static String company_video = "company_video";//	公司视频
    public static String company_contract = "company_contract";//公司合同
    public static String other_video = "other_video";//其他视频
    public static String advance_fund_amount_pdf = "advance_fund_amount_pdf";//	资金划转授权书
    public static String interview_other_pdf = "interview_other_pdf";//	面签其他资料


    /**
     * 根据key 获取对应的url数据
     *
     * @param key
     * @param data
     * @return
     */
    public static String getKayToUrl(String key, List<ZXDetialsBean.ListBean.AttachmentsBean> data) {
        if (TextUtils.isEmpty(key) || data == null || data.isEmpty()) {
            return "";
        }
        for (ZXDetialsBean.ListBean.AttachmentsBean item : data) {
            if (TextUtils.equals(item.getKname(), key)) {
                return item.getUrl();
            }
        }

        return "";
    }

    /**
     * 根据key  获取对应的值说明
     *
     * @param key
     * @param data
     * @return
     */
    public static String getKayToValue(String key, List<ZXDetialsBean.ListBean.AttachmentsBean> data) {
        if (TextUtils.isEmpty(key) || data == null || data.isEmpty()) {
            return "";
        }
        for (ZXDetialsBean.ListBean.AttachmentsBean item : data) {
            if (TextUtils.equals(item.getKname(), key)) {
                return item.getVname();
            }
        }
        return "";
    }

    /**
     * 根据key  获取对应的数据
     *
     * @param key
     * @param data
     * @return
     */
    public static ZXDetialsBean.ListBean.AttachmentsBean getKayToData(String key, List<ZXDetialsBean.ListBean.AttachmentsBean> data) {
        if (TextUtils.isEmpty(key) || data == null || data.isEmpty()) {
            return null;
        }
        for (ZXDetialsBean.ListBean.AttachmentsBean item : data) {
            if (TextUtils.equals(item.getKname(), key)) {
                return item;
            }
        }
        return null;
    }
}
