package com.cdkj.wzcd.model;

import java.util.List;

/**
 * Created by cdkj on 2018/5/31.
 */

public class CreditModel {


    /**
     * code : C201805311100095375102
     * loanBankCode : 联合银行
     * loanAmount : 200000000
     * bizType : 0
     * saleUserId : U201805302312352752420
     * applyDatetime : May 31, 2018 11:00:09 AM
     * curNodeCode : 001_02
     * creditUser : {"code":"CU201805311100095449077","creditCode":"C201805311100095375102","userName":"雷黔","relation":"1","loanRole":"1","mobile":"18984955240","idNo":"52210119507184619","idNoFront":"ANDROID_1527706707613_1080_1920.jpg","idNoReverse":"ANDROID_1527706714742_1080_1920.jpg","authPdf":"ANDROID_1527706723860_1080_1920.jpg","interviewPic":"ANDROID_1527706731874_1080_1920.jpg"}
     */

    private String code;
    private String loanBankCode;
    private String loanBankName;
    private String loanAmount;
    private String bizType;
    private String status;
    private String saleUserId;
    private String applyDatetime;
    private String curNodeCode;
    private String secondCarReport;
    private String xszFront;
    private String xszReverse;
    private String operatorName;
    private String note;
    private String companyName;
    private String saleUserName;
    private String teamName;
    private CreditUserModel creditUser;
    private List<CreditUserModel> creditUserList;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSaleUserName() {
        return saleUserName;
    }

    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLoanBankName() {
        return loanBankName;
    }

    public void setLoanBankName(String loanBankName) {
        this.loanBankName = loanBankName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public List<CreditUserModel> getCreditUserList() {
        return creditUserList;
    }

    public void setCreditUserList(List<CreditUserModel> creditUserList) {
        this.creditUserList = creditUserList;
    }

    public String getSecondCarReport() {
        return secondCarReport;
    }

    public void setSecondCarReport(String secondCarReport) {
        this.secondCarReport = secondCarReport;
    }

    public String getXszFront() {
        return xszFront;
    }

    public void setXszFront(String xszFront) {
        this.xszFront = xszFront;
    }

    public String getXszReverse() {
        return xszReverse;
    }

    public void setXszReverse(String xszReverse) {
        this.xszReverse = xszReverse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoanBankCode() {
        return loanBankCode;
    }

    public void setLoanBankCode(String loanBankCode) {
        this.loanBankCode = loanBankCode;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(String saleUserId) {
        this.saleUserId = saleUserId;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getCurNodeCode() {
        return curNodeCode;
    }

    public void setCurNodeCode(String curNodeCode) {
        this.curNodeCode = curNodeCode;
    }

    public CreditUserModel getCreditUser() {
        return creditUser;
    }

    public void setCreditUser(CreditUserModel creditUser) {
        this.creditUser = creditUser;
    }

}
