package com.cdkj.wzcd.api;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.wzcd.model.CreditModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cdkj on 2018/5/29.
 */

public interface MyApiServer {

    /**
     * 获取用户信息详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UserModel>> getUserInfoDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 获取数据字典
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<DataDictionary>> getDataDictionary(@Field("code") String code, @Field("json") String json);


    //--------------------------------------------征信API--------------------------------------------


    /**
     * 获征信列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<CreditModel>>> getCreditList(@Field("code") String code, @Field("json") String json);

    /**
     * 获征信列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CreditModel>> getCredit(@Field("code") String code, @Field("json") String json);

}
