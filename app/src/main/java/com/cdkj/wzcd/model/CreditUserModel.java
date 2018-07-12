package com.cdkj.wzcd.model;

import java.io.Serializable;

/**
 * Created by cdkj on 2018/5/30.
 */

public class CreditUserModel implements Serializable {

    private String code;
    private String creditCode;
    private String authPdf;
    private String idNo;
    private String idNoFront;
    private String idNoReverse;
    private String interviewPic;
    private String loanRole;
    private String mobile;
    private String relation;
    private String userName;
    private String creditCardOccupation;
    private String bankCreditResultPdf;
    private String bankCreditResultRemark;

    public String getCreditCardOccupation() {
        return creditCardOccupation;
    }

    public void setCreditCardOccupation(String creditCardOccupation) {
        this.creditCardOccupation = creditCardOccupation;
    }

    public String getBankCreditResultPdf() {
        return bankCreditResultPdf;
    }

    public void setBankCreditResultPdf(String bankCreditResultPdf) {
        this.bankCreditResultPdf = bankCreditResultPdf;
    }

    public String getBankCreditResultRemark() {
        return bankCreditResultRemark;
    }

    public void setBankCreditResultRemark(String bankCreditResultRemark) {
        this.bankCreditResultRemark = bankCreditResultRemark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getAuthPdf() {
        return authPdf;
    }

    public void setAuthPdf(String authPdf) {
        this.authPdf = authPdf;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdNoFront() {
        return idNoFront;
    }

    public void setIdNoFront(String idNoFront) {
        this.idNoFront = idNoFront;
    }

    public String getIdNoReverse() {
        return idNoReverse;
    }

    public void setIdNoReverse(String idNoReverse) {
        this.idNoReverse = idNoReverse;
    }

    public String getInterviewPic() {
        return interviewPic;
    }

    public void setInterviewPic(String interviewPic) {
        this.interviewPic = interviewPic;
    }

    public String getLoanRole() {
        return loanRole;
    }

    public void setLoanRole(String loanRole) {
        this.loanRole = loanRole;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
