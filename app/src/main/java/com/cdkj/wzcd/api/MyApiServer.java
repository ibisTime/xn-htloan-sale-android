package com.cdkj.wzcd.api;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.baselibrary.model.UserModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.wzcd.model.CLQDBean;
import com.cdkj.wzcd.model.ChekRoomIdBean;
import com.cdkj.wzcd.model.CreditModel;
import com.cdkj.wzcd.model.DataTransferModel;
import com.cdkj.wzcd.model.ExchangeBankModel;
import com.cdkj.wzcd.model.FaceSignBean;
import com.cdkj.wzcd.model.GpsApplyModel;
import com.cdkj.wzcd.model.GpsModel;
import com.cdkj.wzcd.model.LoanProductModel;
import com.cdkj.wzcd.model.MessageSystemBean;
import com.cdkj.wzcd.model.MessageTobeBean;
import com.cdkj.wzcd.model.NodeListModel;
import com.cdkj.wzcd.model.NodeListModelFace;
import com.cdkj.wzcd.model.NodeModel;
import com.cdkj.wzcd.model.RecModel;
import com.cdkj.wzcd.model.RepaymentModel;
import com.cdkj.wzcd.model.SuccessBean;
import com.cdkj.wzcd.model.TeamBean;
import com.cdkj.wzcd.model.TencentSignModel;
import com.cdkj.wzcd.model.TodoModel;
import com.cdkj.wzcd.model.ZXDetialsBean;
import com.cdkj.wzcd.model.ZrdModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by cdkj on 2018/5/29.
 */

public interface MyApiServer {

    /**
     * 提交接口  成功的接口  通用成功接口
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<SuccessBean>> success(@Field("code") String code, @Field("json") String json);

    /**
     * rxjava用法  返回  被观察者
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<SuccessBean>> success2(@Field("code") String code, @Field("json") String json);

    /**
     * 获取待办事项
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<TodoModel>> getTodo(@Field("code") String code, @Field("json") String json);

    /**
     * 获取腾讯用户签名
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<TencentSignModel>> getTencentSign(@Field("code") String code, @Field("json") String json);

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
     * 获取用户信息详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<UserModel>>> getUserList(@Field("code") String code, @Field("json") String json);


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

    /**
     * 获取平台银行
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<ExchangeBankModel>> getExchangeBank(@Field("code") String code, @Field("json") String json);

    /**
     * 获取节列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<NodeListModel>>> getNodeList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取面签列表数据
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<NodeListModelFace>> getNodeListFacet(@Field("code") String code, @Field("json") String json);

    /**
     * 获取节列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<NodeModel>> getNodeDataList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取节列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<NodeListModel>> getNode(@Field("code") String code, @Field("json") String json);


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
    Call<BaseResponseModel<ZXDetialsBean>> getCreditList2(@Field("code") String code, @Field("json") String json);

    /**
     * 获征信详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZXDetialsBean.ListBean>> getCredit2(@Field("code") String code, @Field("json") String json);

    /**
     * 获征信详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZXDetialsBean.ListBean>> getCredit3(@Field("code") String code, @Field("json") String json);

    /**
     * 获征信详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CreditModel>> getCredit(@Field("code") String code, @Field("json") String json);


    //--------------------------------------------GPS申领API--------------------------------------------

    /**
     * 获取申领列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<GpsApplyModel>>> getGPSApplyList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取申领列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<GpsApplyModel>> getGps(@Field("code") String code, @Field("json") String json);

    /**
     * 获取申领列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<GpsModel>> getGpsList(@Field("code") String code, @Field("json") String json);


    //--------------------------------------------资料传递--------------------------------------------

    /**
     * 获取申领列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<DataTransferModel>>> getDataTransfer(@Field("code") String code, @Field("json") String json);

    /**
     * 获取申领列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<DataTransferModel>> getDataTransferList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取节列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<DataTransferModel>> getData(@Field("code") String code, @Field("json") String json);


    //--------------------------------------------历史客户--------------------------------------------

    /**
     * 分页查还款业务
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<RepaymentModel>>> getRepaymentList(@Field("code") String code, @Field("json") String json);

    /**
     * 分页查还款业务
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<RepaymentModel>> getRepayment(@Field("code") String code, @Field("json") String json);

    //--------------------------------------------贷前准入--------------------------------------------

    /**
     * 获取平台银行
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<LoanProductModel>> getLoanProduct(@Field("code") String code, @Field("json") String json);

    /**
     * 获取准入单列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<ZrdModel>>> getZrdList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取材料清单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<CLQDBean>> getCLQD(@Field("code") String code, @Field("json") String json);

    /**
     * 获取面签详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<FaceSignBean>> getFaceSign(@Field("code") String code, @Field("json") String json);

    /**
     * 获取面签详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZXDetialsBean.ListBean>> getFaceSign2(@Field("code") String code, @Field("json") String json);

    /**
     * 获取腾讯云录制视频
     *
     * @return
     */
//    @FormUrlEncoded
    @GET("common_access")
    Call<BaseResponseModelCallBack<FaceSignBean>> getILiveVideoList(@QueryMap Map<String, String> map);

    /**
     * 获取房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<String>> getRoomId(@Field("code") String code, @Field("json") String json);

    /**
     * 获取房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ChekRoomIdBean>> getChekRoomId(@Field("code") String code, @Field("json") String json);

    /**
     * 获取房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<String>> getILiveVoide(@Field("code") String code, @Field("json") String json);

    /**
     * 获取房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<RecModel>> getPutStrime(@Field("code") String code, @Field("json") String json);

    /**
     * 检查房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<Integer>> checkRoomId(@Field("code") String code, @Field("json") String json);

    /**
     * 检查房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<SuccessBean>> cleanRoom(@Field("code") String code, @Field("json") String json);

    /**
     * 检查房间id
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<TeamBean>> getTeamData(@Field("code") String code, @Field("json") String json);

    /**
     * 获取系统公告消息
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<MessageSystemBean>> getMessage(@Field("code") String code, @Field("json") String json);

    /**
     * 获取待办消息
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<MessageTobeBean>> getTobeThing(@Field("code") String code, @Field("json") String json);

    /**
     * 获取客户数据
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZXDetialsBean>> getCustomerData(@Field("code") String code, @Field("json") String json);


}

