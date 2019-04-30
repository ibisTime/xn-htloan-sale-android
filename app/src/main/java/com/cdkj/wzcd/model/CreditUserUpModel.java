package com.cdkj.wzcd.model;

import java.io.Serializable;

/**
 * @updateDts 2019/4/21
 * 上传的入参bean
 */
public class CreditUserUpModel implements Serializable {


    /**
     * creditUserCode : CU201904191109364015112
     * bankCreditReport : FgGePPB9QLkWa4B5z-Wgt3wrFjmr
     * creditNote : 征信报告说明
     * bankResult : 0
     * dataCreditReport : FgGePPB9QLkWa4B5z-Wgt3wrFjmr||FkrUFJgBi1bpCUYX_5JCLK75ea5E
     */

    private String creditUserCode;
    private String bankCreditReport;
    private String creditNote;
    private String bankResult;
    private String dataCreditReport;

    public String getCreditUserCode() {
        return creditUserCode;
    }

    public void setCreditUserCode(String creditUserCode) {
        this.creditUserCode = creditUserCode;
    }

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
}
