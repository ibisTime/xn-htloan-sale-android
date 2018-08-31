package com.cdkj.wzcd.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author cdkj
 * @updateDts 2018/5/30
 */

public class GpsApplyModel {


    /**
     * code : GA201806042003056459347
     * type : 2
     * applyUser : U201806031626324415728
     * applyDatetime : Jun 4, 2018 8:03:05 PM
     * applyReason : 领两个
     * applyCount : 2
     * status : 0
     * applyUserName : 雷黔
     */

    private String code;
    private String type;
    private String applyUser;
    private String applyDatetime;
    private String applyReason;
    private int applyCount;
    private String status;
    private String applyUserName;
    /**
     * applyCount : 1.0
     * companyCode : DP201800000000000000001
     * companyName : 乌鲁木齐华途威通汽车销售有限公司
     */

    @SerializedName("applyCount")
    private double applyCountX;
    private String companyCode;
    private String companyName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public double getApplyCountX() {
        return applyCountX;
    }

    public void setApplyCountX(double applyCountX) {
        this.applyCountX = applyCountX;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
