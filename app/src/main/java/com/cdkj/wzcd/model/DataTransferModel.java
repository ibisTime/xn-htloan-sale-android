package com.cdkj.wzcd.model;

/**
 * Created by cdkj on 2018/6/5.
 */

public class DataTransferModel {


    /**
     * code : L201806051607028009280
     * type : 1
     * bizCode : BO201806050154003632973
     * userId : U201806031626324415728
     * fromNodeCode : 002_11
     * toNodeCode : 002_13
     * refFileList : 购车合同
     * sendFileList : 合同,材料
     * sendType : 1
     * sendNote : 备注
     * status : 1
     * userName : 雷黔
     */

    private String code;
    private String type;
    private String bizCode;
    private String userId;
    private String fromNodeCode;
    private String toNodeCode;
    private String refFileList;
    private String sendFileList;
    private String sendType;
    private String sendNote;
    private String status;
    private String userName;
    private String sendDatetime;
    private String logisticsCode;
    private String logisticsCompany;
    private String customerName;
    private String receiptDatetime;
    private String receiver;
    private String remark;
    private String senderName;// 发件人
    private String teamName;//   团队
    private String userRole;//   角色
    private String receiverName;//   收件人
    private GpsApply gpsApply;
    private String applyWiredCount;//   有线
    private String applyWirelessCount;//   无线
    private String saleUserName;//   信贷专员
    private String insideJobName;//   内勤专员

    public String getSaleUserName() {
        return saleUserName;
    }

    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }

    public String getInsideJobName() {
        return insideJobName;
    }

    public void setInsideJobName(String insideJobName) {
        this.insideJobName = insideJobName;
    }

    public String getApplyWiredCount() {
        return applyWiredCount;
    }

    public void setApplyWiredCount(String applyWiredCount) {
        this.applyWiredCount = applyWiredCount;
    }

    public String getApplyWirelessCount() {
        return applyWirelessCount;
    }

    public void setApplyWirelessCount(String applyWirelessCount) {
        this.applyWirelessCount = applyWirelessCount;
    }

    public GpsApply getGpsApply() {
        return gpsApply;
    }

    public void setGpsApply(GpsApply gpsApply) {
        this.gpsApply = gpsApply;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }





    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(String sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromNodeCode() {
        return fromNodeCode;
    }

    public void setFromNodeCode(String fromNodeCode) {
        this.fromNodeCode = fromNodeCode;
    }

    public String getToNodeCode() {
        return toNodeCode;
    }

    public void setToNodeCode(String toNodeCode) {
        this.toNodeCode = toNodeCode;
    }

    public String getRefFileList() {
        return refFileList;
    }

    public void setRefFileList(String refFileList) {
        this.refFileList = refFileList;
    }

    public String getSendFileList() {
        return sendFileList;
    }

    public void setSendFileList(String sendFileList) {
        this.sendFileList = sendFileList;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReceiptDatetime() {
        return receiptDatetime;
    }

    public void setReceiptDatetime(String receiptDatetime) {
        this.receiptDatetime = receiptDatetime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class GpsApply {

        /**
         * applyCount : 2.0
         * applyDatetime : Sep 29, 2018 2:51:06 PM
         * applyReason : 说明
         * applyUser : U201808311906424335963
         * applyWiredCount : 1.0
         * applyWirelessCount : 1.0
         * code : GA201809291451067588961
         * companyCode : DP201800000000000000001
         * receiveDatetime : Sep 29, 2018 4:32:47 PM
         * sendDatetime : Sep 29, 2018 2:55:01 PM
         * status : 4
         * type : 2
         */

        private double applyCount;
        private String applyDatetime;
        private String applyReason;
        private String applyUser;
        private double applyWiredCount;
        private double applyWirelessCount;
        private String code;
        private String companyCode;
        private String receiveDatetime;
        private String sendDatetime;
        private String status;
        private String type;
        private String customerName;//客户姓名
        private String carFrameNo;//车架号
        private String applyUserName;//申请人姓名
        private String userRole;//申请人角色

        public String getApplyUserName() {
            return applyUserName;
        }

        public void setApplyUserName(String applyUserName) {
            this.applyUserName = applyUserName;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCarFrameNo() {
            return carFrameNo;
        }

        public void setCarFrameNo(String carFrameNo) {
            this.carFrameNo = carFrameNo;
        }

        public double getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(double applyCount) {
            this.applyCount = applyCount;
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

        public String getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(String applyUser) {
            this.applyUser = applyUser;
        }

        public double getApplyWiredCount() {
            return applyWiredCount;
        }

        public void setApplyWiredCount(double applyWiredCount) {
            this.applyWiredCount = applyWiredCount;
        }

        public double getApplyWirelessCount() {
            return applyWirelessCount;
        }

        public void setApplyWirelessCount(double applyWirelessCount) {
            this.applyWirelessCount = applyWirelessCount;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getReceiveDatetime() {
            return receiveDatetime;
        }

        public void setReceiveDatetime(String receiveDatetime) {
            this.receiveDatetime = receiveDatetime;
        }

        public String getSendDatetime() {
            return sendDatetime;
        }

        public void setSendDatetime(String sendDatetime) {
            this.sendDatetime = sendDatetime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
