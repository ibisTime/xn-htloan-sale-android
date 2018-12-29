package com.cdkj.wzcd.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * @updateDts 2018/12/18
 */
public class TeamBean implements IPickerViewData {

    /**
     * code : BT201809101210588883977
     * name : 正租业务部
     * captain : U201809101210096129537
     * companyCode : DP201800000000000000001
     * status : 1
     * updater : U201806131315524345485
     * updateDatetime : Sep 10, 2018 12:10:58 PM
     * accountNo : 123123123123123
     * bank : CMBC
     * subbranch : 1313131313
     * region : 新疆
     * place : 新疆
     * captainName : 冯志亮
     * companyName : 乌鲁木齐华途威通汽车销售有限公司
     * updaterName : 洪仁飞
     */

    private String code;
    private String name;
    private String captain;
    private String companyCode;
    private String status;
    private String updater;
    private String updateDatetime;
    private String accountNo;
    private String bank;
    private String subbranch;
    private String region;
    private String place;
    private String captainName;
    private String companyName;
    private String updaterName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
