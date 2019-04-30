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
    private String idFront;
    private String idReverse;
    private String interviewPic;
    private String loanRole;
    private String mobile;
    private String relation;
    private String userName;
    private String creditCardOccupation;
    private String bankCreditResultPdf;
    private String bankCreditResultRemark;
    //再下个界面填写的数据
    private String bankCreditReport;//
    private String creditNote;
    private String bankResult;
    private String dataCreditReport;


    public String getBankCreditReport() {
        return bankCreditReport;
    }

    public void setBankCreditReport(String bankCreditReport) {
        this.bankCreditReport = bankCreditReport;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public String getBankResult() {
        return bankResult;
    }

    public void setBankResult(String bankResult) {
        this.bankResult = bankResult;
    }

    public String getDataCreditReport() {
        return dataCreditReport;
    }

    public void setDataCreditReport(String dataCreditReport) {
        this.dataCreditReport = dataCreditReport;
    }

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

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdReverse() {
        return idReverse;
    }

    public void setIdReverse(String idReverse) {
        this.idReverse = idReverse;
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
