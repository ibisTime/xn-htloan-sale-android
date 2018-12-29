package com.cdkj.wzcd.model;

/**
 * @updateDts 2018/12/4
 */
public class ChekRoomIdBean {

    /**
     * code : 8617700
     * createDatetime : Dec 4, 2018 3:59:30 PM
     * budgetCode : BO201811031338513094212
     */

    private String code;
    private String createDatetime;
    private String budgetCode;
    private String status;

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

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode;
    }
}
