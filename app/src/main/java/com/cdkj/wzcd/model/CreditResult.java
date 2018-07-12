package com.cdkj.wzcd.model;

/**
 * Created by cdkj on 2018/6/9.
 */

public class CreditResult {

    private String creditCardOccupation;
    private String bankCreditResultPdf;
    private String bankCreditResultRemark;
    private String creditUserCode;

    public String getCreditCardOccupation() {
        return creditCardOccupation;
    }

    public CreditResult setCreditCardOccupation(String creditCardOccupation) {
        this.creditCardOccupation = creditCardOccupation;
        return this;
    }

    public String getBankCreditResultPdf() {
        return bankCreditResultPdf;
    }

    public CreditResult setBankCreditResultPdf(String bankCreditResultPdf) {
        this.bankCreditResultPdf = bankCreditResultPdf;
        return this;
    }

    public String getBankCreditResultRemark() {
        return bankCreditResultRemark;
    }

    public CreditResult setBankCreditResultRemark(String bankCreditResultRemark) {
        this.bankCreditResultRemark = bankCreditResultRemark;

        return this;
    }

    public String getCreditUserCode() {
        return creditUserCode;
    }

    public CreditResult setCreditUserCode(String creditUserCode) {
        this.creditUserCode = creditUserCode;

        return this;
    }
}
