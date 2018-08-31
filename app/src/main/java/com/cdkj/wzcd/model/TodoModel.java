package com.cdkj.wzcd.model;

/**
 * @updateDts 2018/8/29
 * 待办事项
 */

public class TodoModel {


    /**
     * creditTodo : 2.0
     * interviewTodo : 0.0
     * gpsInstallTodo : 0.0
     * carSettleTodo : 0.0
     * entryMortgageTodo : 0.0
     * logisticsTodo : 0.0
     */

    private int creditTodo;
    private int interviewTodo;
    private int gpsInstallTodo;
    private int carSettleTodo;
    private int entryMortgageTodo;
    private int logisticsTodo;

    public int getCreditTodo() {
        return creditTodo;
    }

    public void setCreditTodo(int creditTodo) {
        this.creditTodo = creditTodo;
    }

    public int getInterviewTodo() {
        return interviewTodo;
    }

    public void setInterviewTodo(int interviewTodo) {
        this.interviewTodo = interviewTodo;
    }

    public int getGpsInstallTodo() {
        return gpsInstallTodo;
    }

    public void setGpsInstallTodo(int gpsInstallTodo) {
        this.gpsInstallTodo = gpsInstallTodo;
    }

    public int getCarSettleTodo() {
        return carSettleTodo;
    }

    public void setCarSettleTodo(int carSettleTodo) {
        this.carSettleTodo = carSettleTodo;
    }

    public int getEntryMortgageTodo() {
        return entryMortgageTodo;
    }

    public void setEntryMortgageTodo(int entryMortgageTodo) {
        this.entryMortgageTodo = entryMortgageTodo;
    }

    public int getLogisticsTodo() {
        return logisticsTodo;
    }

    public void setLogisticsTodo(int logisticsTodo) {
        this.logisticsTodo = logisticsTodo;
    }
}
