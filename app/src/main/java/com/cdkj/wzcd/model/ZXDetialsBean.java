package com.cdkj.wzcd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @updateDts 2019/4/25
 */
public class ZXDetialsBean {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 1
     * totalCount : 42
     * totalPage : 42
     * list : [{"code":"CB201904251534335678302","bizType":"0","companyCode":"DP201800000000000000001","teamCode":"BT201812010242065583765","captainCode":"U201812010235582541850","saleUserId":"U201812010243173663058","insideJob":"U201812010243173663058","loanBank":"BA201806011006085041799","loanAmount":10000000,"status":"002","mqStatus":"001","zfStatus":"0","curNodeCode":"a3","intevCurNodeCode":"b02","applyDatetime":"Apr 25, 2019 3:34:33 PM","remark":"说明","attachments":[{"code":"AT201904251534336142581","bizCode":"CB201904251534335678302","category":"credit_user","kname":"id_no_front_apply","vname":"申请人身份证正面","attachType":"图片","url":"ANDROID_1556177340083_1080_1920.jpg"},{"code":"AT201904251534336284938","bizCode":"CB201904251534335678302","category":"credit_user","kname":"id_no_reverse_apply","vname":"申请人身份证反面","attachType":"图片","url":"ANDROID_1556177346179_1080_1920.jpg"},{"code":"AT201904251534336424222","bizCode":"CB201904251534335678302","category":"credit_user","kname":"auth_pdf_apply","vname":"申请人征信查询授权书","attachType":"图片","url":"ANDROID_1556177353362_1080_1920.jpg"},{"code":"AT201904251534336551374","bizCode":"CB201904251534335678302","category":"credit_user","kname":"interview_pic_apply","vname":"申请人面签照片","attachType":"图片","url":"ANDROID_1556177358545_1080_1920.jpg"},{"code":"AT201904251548372259653","bizCode":"CB201904251534335678302","category":"credit_user","kname":"bank_report_apply","vname":"申请人银行征信报告","attachType":"图片","url":"ANDROID_1556178458332_1080_1920.jpg"},{"code":"AT201904251548372389925","bizCode":"CB201904251534335678302","category":"credit_user","kname":"data_report_apply","vname":"申请人大数据报告","attachType":"图片","url":"ANDROID_1556178464808_1080_1920.jpg"},{"code":"AT201904251638323731986","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_video","vname":"银行视频","attachType":"视频","url":"kv06fuVJqnjxPIVk"},{"code":"AT201904251638324048918","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_photo","vname":"银行面签照片","attachType":"图片","url":"IOS_1556181439240286_4032_3024.jpg"},{"code":"AT201904251638324291725","bizCode":"CB201904251534335678302","category":"interview","kname":"company_video","vname":"公司视频","attachType":"视频","url":"voKZwEn4ZOzNKhIg"},{"code":"AT201904251638324556870","bizCode":"CB201904251534335678302","category":"interview","kname":"company_contract","vname":"公司合同","attachType":"图片","url":"IOS_1556181451760215_1440_1080.jpg"},{"code":"AT201904251638324806308","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_contract","vname":"银行合同","attachType":"图片","url":"IOS_1556181446159026_4032_3024.jpg"},{"code":"AT201904251638325086768","bizCode":"CB201904251534335678302","category":"interview","kname":"advance_fund_amount_pdf","vname":"资金划转授权书","attachType":"图片","url":"IOS_1556181458460207_4032_3024.jpg"},{"code":"AT201904251638325359601","bizCode":"CB201904251534335678302","category":"interview","kname":"other_video","vname":"其他视频","attachType":"视频","url":"pZHFZmSJACOrmhO6"},{"code":"AT201904251638325622782","bizCode":"CB201904251534335678302","category":"interview","kname":"interview_other_pdf","vname":"面签其他资料","attachType":"图片","url":"IOS_1556181468530194_4032_3024.jpg"}],"bizTasks":[{"code":"BT201904251534336002578","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b01","content":"你有新的待新录面签信息面签单","createDatetime":"Apr 25, 2019 3:34:33 PM","status":"1","finishDatetime":"Apr 25, 2019 4:38:32 PM"},{"code":"BT201904251534336657393","bizCode":"CB201904251534335678302","refType":"a","refOrder":"CB201904251534335678302","refNode":"a1","content":"你有新的待新录征信资料征信单","createDatetime":"Apr 25, 2019 3:34:33 PM","status":"0","operater":"U201812010243173663058","operateRole":"SR201800000000000000YWY"},{"code":"BT201904251638326056963","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b02","content":"你有新的待主管审核面签信息面签单","createDatetime":"Apr 25, 2019 4:38:32 PM","status":"0","operater":"U201904171602435343144","operateRole":"RO201800000000000001"},{"code":"BT201904251709179858353","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b02","content":"你有新的待主管审核面签信息面签单","createDatetime":"Apr 25, 2019 5:09:17 PM","status":"0","operater":"U201904171602435343144","operateRole":"RO201800000000000001"}],"bizLogs":[{"id":257,"bizCode":"CB201904251534335678302","refType":"a","refOrder":"CB201904251534335678302","dealNode":"a1","dealNote":"说明","operateRole":"SR201800000000000000YWY","operator":"U201812010243173663058","operatorName":"张明","operatorMobile":"15699150679","startDatetime":"Apr 25, 2019 3:34:33 PM","endDatetime":"Apr 25, 2019 3:34:33 PM","speedTime":"0"},{"id":259,"bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","dealNode":"b01","dealNote":"新录面签信息","operateRole":"RO201800000000000001","operator":"U201904171602435343144","operatorName":"zqb","operatorMobile":"13506537519","startDatetime":"Apr 25, 2019 4:38:32 PM","endDatetime":"Apr 25, 2019 4:38:32 PM","speedTime":"0"},{"id":260,"bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","dealNode":"b01","dealNote":"新录面签信息","operateRole":"RO201800000000000001","operator":"U201904171602435343144","operatorName":"zqb","operatorMobile":"13506537519","startDatetime":"Apr 25, 2019 5:09:17 PM","endDatetime":"Apr 25, 2019 5:09:17 PM","speedTime":"0"}],"companyName":"乌鲁木齐华途威通汽车销售有限公司","teamName":"乌鲁木齐业务六部","saleUserName":"张明","creditUser":{"code":"CU201904251534336056731","bizCode":"CB201904251534335678302","relation":"4","loanRole":"1","userName":"姓名2","mobile":"13333333333","idNo":"410621199505101019","bankCreditResult":"0","bankCreditResultRemark":"思路going","idFront":"ANDROID_1556177340083_1080_1920.jpg","idReverse":"ANDROID_1556177346179_1080_1920.jpg","authPdf":"ANDROID_1556177353362_1080_1920.jpg","interviewPic":"ANDROID_1556177358545_1080_1920.jpg","dataCreditReport":"ANDROID_1556178464808_1080_1920.jpg","BankCreditReport":"ANDROID_1556178458332_1080_1920.jpg"},"loanBankName":"中国工商银行","creditUserList":[{"code":"CU201904251534336056731","bizCode":"CB201904251534335678302","relation":"4","loanRole":"1","userName":"姓名2","mobile":"13333333333","idNo":"410621199505101019","bankCreditResult":"0","bankCreditResultRemark":"思路going","idFront":"ANDROID_1556177340083_1080_1920.jpg","idReverse":"ANDROID_1556177346179_1080_1920.jpg","authPdf":"ANDROID_1556177353362_1080_1920.jpg","interviewPic":"ANDROID_1556177358545_1080_1920.jpg","dataCreditReport":"ANDROID_1556178464808_1080_1920.jpg","BankCreditReport":"ANDROID_1556178458332_1080_1920.jpg"}],"creditJours":[]}]
     */

    private int pageNO;
    private int start;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements  Serializable{
        /**
         * code : CB201904251534335678302
         * bizType : 0
         * companyCode : DP201800000000000000001
         * teamCode : BT201812010242065583765
         * captainCode : U201812010235582541850
         * saleUserId : U201812010243173663058
         * insideJob : U201812010243173663058
         * loanBank : BA201806011006085041799
         * loanAmount : 10000000
         * status : 002
         * mqStatus : 001
         * zfStatus : 0
         * curNodeCode : a3
         * intevCurNodeCode : b02
         * applyDatetime : Apr 25, 2019 3:34:33 PM
         * remark : 说明
         * attachments : [{"code":"AT201904251534336142581","bizCode":"CB201904251534335678302","category":"credit_user","kname":"id_no_front_apply","vname":"申请人身份证正面","attachType":"图片","url":"ANDROID_1556177340083_1080_1920.jpg"},{"code":"AT201904251534336284938","bizCode":"CB201904251534335678302","category":"credit_user","kname":"id_no_reverse_apply","vname":"申请人身份证反面","attachType":"图片","url":"ANDROID_1556177346179_1080_1920.jpg"},{"code":"AT201904251534336424222","bizCode":"CB201904251534335678302","category":"credit_user","kname":"auth_pdf_apply","vname":"申请人征信查询授权书","attachType":"图片","url":"ANDROID_1556177353362_1080_1920.jpg"},{"code":"AT201904251534336551374","bizCode":"CB201904251534335678302","category":"credit_user","kname":"interview_pic_apply","vname":"申请人面签照片","attachType":"图片","url":"ANDROID_1556177358545_1080_1920.jpg"},{"code":"AT201904251548372259653","bizCode":"CB201904251534335678302","category":"credit_user","kname":"bank_report_apply","vname":"申请人银行征信报告","attachType":"图片","url":"ANDROID_1556178458332_1080_1920.jpg"},{"code":"AT201904251548372389925","bizCode":"CB201904251534335678302","category":"credit_user","kname":"data_report_apply","vname":"申请人大数据报告","attachType":"图片","url":"ANDROID_1556178464808_1080_1920.jpg"},{"code":"AT201904251638323731986","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_video","vname":"银行视频","attachType":"视频","url":"kv06fuVJqnjxPIVk"},{"code":"AT201904251638324048918","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_photo","vname":"银行面签照片","attachType":"图片","url":"IOS_1556181439240286_4032_3024.jpg"},{"code":"AT201904251638324291725","bizCode":"CB201904251534335678302","category":"interview","kname":"company_video","vname":"公司视频","attachType":"视频","url":"voKZwEn4ZOzNKhIg"},{"code":"AT201904251638324556870","bizCode":"CB201904251534335678302","category":"interview","kname":"company_contract","vname":"公司合同","attachType":"图片","url":"IOS_1556181451760215_1440_1080.jpg"},{"code":"AT201904251638324806308","bizCode":"CB201904251534335678302","category":"interview","kname":"bank_contract","vname":"银行合同","attachType":"图片","url":"IOS_1556181446159026_4032_3024.jpg"},{"code":"AT201904251638325086768","bizCode":"CB201904251534335678302","category":"interview","kname":"advance_fund_amount_pdf","vname":"资金划转授权书","attachType":"图片","url":"IOS_1556181458460207_4032_3024.jpg"},{"code":"AT201904251638325359601","bizCode":"CB201904251534335678302","category":"interview","kname":"other_video","vname":"其他视频","attachType":"视频","url":"pZHFZmSJACOrmhO6"},{"code":"AT201904251638325622782","bizCode":"CB201904251534335678302","category":"interview","kname":"interview_other_pdf","vname":"面签其他资料","attachType":"图片","url":"IOS_1556181468530194_4032_3024.jpg"}]
         * bizTasks : [{"code":"BT201904251534336002578","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b01","content":"你有新的待新录面签信息面签单","createDatetime":"Apr 25, 2019 3:34:33 PM","status":"1","finishDatetime":"Apr 25, 2019 4:38:32 PM"},{"code":"BT201904251534336657393","bizCode":"CB201904251534335678302","refType":"a","refOrder":"CB201904251534335678302","refNode":"a1","content":"你有新的待新录征信资料征信单","createDatetime":"Apr 25, 2019 3:34:33 PM","status":"0","operater":"U201812010243173663058","operateRole":"SR201800000000000000YWY"},{"code":"BT201904251638326056963","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b02","content":"你有新的待主管审核面签信息面签单","createDatetime":"Apr 25, 2019 4:38:32 PM","status":"0","operater":"U201904171602435343144","operateRole":"RO201800000000000001"},{"code":"BT201904251709179858353","bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","refNode":"b02","content":"你有新的待主管审核面签信息面签单","createDatetime":"Apr 25, 2019 5:09:17 PM","status":"0","operater":"U201904171602435343144","operateRole":"RO201800000000000001"}]
         * bizLogs : [{"id":257,"bizCode":"CB201904251534335678302","refType":"a","refOrder":"CB201904251534335678302","dealNode":"a1","dealNote":"说明","operateRole":"SR201800000000000000YWY","operator":"U201812010243173663058","operatorName":"张明","operatorMobile":"15699150679","startDatetime":"Apr 25, 2019 3:34:33 PM","endDatetime":"Apr 25, 2019 3:34:33 PM","speedTime":"0"},{"id":259,"bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","dealNode":"b01","dealNote":"新录面签信息","operateRole":"RO201800000000000001","operator":"U201904171602435343144","operatorName":"zqb","operatorMobile":"13506537519","startDatetime":"Apr 25, 2019 4:38:32 PM","endDatetime":"Apr 25, 2019 4:38:32 PM","speedTime":"0"},{"id":260,"bizCode":"CB201904251534335678302","refType":"b0","refOrder":"CB201904251534335678302","dealNode":"b01","dealNote":"新录面签信息","operateRole":"RO201800000000000001","operator":"U201904171602435343144","operatorName":"zqb","operatorMobile":"13506537519","startDatetime":"Apr 25, 2019 5:09:17 PM","endDatetime":"Apr 25, 2019 5:09:17 PM","speedTime":"0"}]
         * companyName : 乌鲁木齐华途威通汽车销售有限公司
         * teamName : 乌鲁木齐业务六部
         * saleUserName : 张明
         * creditUser : {"code":"CU201904251534336056731","bizCode":"CB201904251534335678302","relation":"4","loanRole":"1","userName":"姓名2","mobile":"13333333333","idNo":"410621199505101019","bankCreditResult":"0","bankCreditResultRemark":"思路going","idFront":"ANDROID_1556177340083_1080_1920.jpg","idReverse":"ANDROID_1556177346179_1080_1920.jpg","authPdf":"ANDROID_1556177353362_1080_1920.jpg","interviewPic":"ANDROID_1556177358545_1080_1920.jpg","dataCreditReport":"ANDROID_1556178464808_1080_1920.jpg","BankCreditReport":"ANDROID_1556178458332_1080_1920.jpg"}
         * loanBankName : 中国工商银行
         * creditUserList : [{"code":"CU201904251534336056731","bizCode":"CB201904251534335678302","relation":"4","loanRole":"1","userName":"姓名2","mobile":"13333333333","idNo":"410621199505101019","bankCreditResult":"0","bankCreditResultRemark":"思路going","idFront":"ANDROID_1556177340083_1080_1920.jpg","idReverse":"ANDROID_1556177346179_1080_1920.jpg","authPdf":"ANDROID_1556177353362_1080_1920.jpg","interviewPic":"ANDROID_1556177358545_1080_1920.jpg","dataCreditReport":"ANDROID_1556178464808_1080_1920.jpg","BankCreditReport":"ANDROID_1556178458332_1080_1920.jpg"}]
         * creditJours : []
         */

        private String code;
        private String bizType;
        private String companyCode;
        private String teamCode;
        private String captainCode;
        private String saleUserId;
        private String insideJob;
        private String loanBank;
        private BigDecimal loanAmount;
        private String status;
        private String mqStatus;
        private String zfStatus;
        private String curNodeCode;
        private String intevCurNodeCode;
        private String applyDatetime;
        private String remark;
        private String companyName;
        private String teamName;
        private String saleUserName;
        private CreditUserBean creditUser;
        private String loanBankName;
        private List<AttachmentsBean> attachments;
        private List<BizTasksBean> bizTasks;
        private List<BizLogsBean> bizLogs;
        private List<CreditUserListBean> creditUserList;
        private List<?> creditJours;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getTeamCode() {
            return teamCode;
        }

        public void setTeamCode(String teamCode) {
            this.teamCode = teamCode;
        }

        public String getCaptainCode() {
            return captainCode;
        }

        public void setCaptainCode(String captainCode) {
            this.captainCode = captainCode;
        }

        public String getSaleUserId() {
            return saleUserId;
        }

        public void setSaleUserId(String saleUserId) {
            this.saleUserId = saleUserId;
        }

        public String getInsideJob() {
            return insideJob;
        }

        public void setInsideJob(String insideJob) {
            this.insideJob = insideJob;
        }

        public String getLoanBank() {
            return loanBank;
        }

        public void setLoanBank(String loanBank) {
            this.loanBank = loanBank;
        }

        public BigDecimal getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(BigDecimal loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMqStatus() {
            return mqStatus;
        }

        public void setMqStatus(String mqStatus) {
            this.mqStatus = mqStatus;
        }

        public String getZfStatus() {
            return zfStatus;
        }

        public void setZfStatus(String zfStatus) {
            this.zfStatus = zfStatus;
        }

        public String getCurNodeCode() {
            return curNodeCode;
        }

        public void setCurNodeCode(String curNodeCode) {
            this.curNodeCode = curNodeCode;
        }

        public String getIntevCurNodeCode() {
            return intevCurNodeCode;
        }

        public void setIntevCurNodeCode(String intevCurNodeCode) {
            this.intevCurNodeCode = intevCurNodeCode;
        }

        public String getApplyDatetime() {
            return applyDatetime;
        }

        public void setApplyDatetime(String applyDatetime) {
            this.applyDatetime = applyDatetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getSaleUserName() {
            return saleUserName;
        }

        public void setSaleUserName(String saleUserName) {
            this.saleUserName = saleUserName;
        }

        public CreditUserBean getCreditUser() {
            return creditUser;
        }

        public void setCreditUser(CreditUserBean creditUser) {
            this.creditUser = creditUser;
        }

        public String getLoanBankName() {
            return loanBankName;
        }

        public void setLoanBankName(String loanBankName) {
            this.loanBankName = loanBankName;
        }

        public List<AttachmentsBean> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<AttachmentsBean> attachments) {
            this.attachments = attachments;
        }

        public List<BizTasksBean> getBizTasks() {
            return bizTasks;
        }

        public void setBizTasks(List<BizTasksBean> bizTasks) {
            this.bizTasks = bizTasks;
        }

        public List<BizLogsBean> getBizLogs() {
            return bizLogs;
        }

        public void setBizLogs(List<BizLogsBean> bizLogs) {
            this.bizLogs = bizLogs;
        }

        public List<CreditUserListBean> getCreditUserList() {
            return creditUserList;
        }

        public void setCreditUserList(List<CreditUserListBean> creditUserList) {
            this.creditUserList = creditUserList;
        }

        public List<?> getCreditJours() {
            return creditJours;
        }

        public void setCreditJours(List<?> creditJours) {
            this.creditJours = creditJours;
        }

        public static class CreditUserBean implements  Serializable{
            /**
             * code : CU201904251534336056731
             * bizCode : CB201904251534335678302
             * relation : 4
             * loanRole : 1
             * userName : 姓名2
             * mobile : 13333333333
             * idNo : 410621199505101019
             * bankCreditResult : 0
             * bankCreditResultRemark : 思路going
             * idFront : ANDROID_1556177340083_1080_1920.jpg
             * idReverse : ANDROID_1556177346179_1080_1920.jpg
             * authPdf : ANDROID_1556177353362_1080_1920.jpg
             * interviewPic : ANDROID_1556177358545_1080_1920.jpg
             * dataCreditReport : ANDROID_1556178464808_1080_1920.jpg
             * BankCreditReport : ANDROID_1556178458332_1080_1920.jpg
             */

            private String code;
            private String bizCode;
            private String relation;
            private String loanRole;
            private String userName;
            private String mobile;
            private String idNo;
            private String bankCreditResult;
            private String bankCreditResultRemark;
            private String idFront;
            private String idReverse;
            private String authPdf;
            private String interviewPic;
            private String dataCreditReport;
            private String BankCreditReport;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getLoanRole() {
                return loanRole;
            }

            public void setLoanRole(String loanRole) {
                this.loanRole = loanRole;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getBankCreditResult() {
                return bankCreditResult;
            }

            public void setBankCreditResult(String bankCreditResult) {
                this.bankCreditResult = bankCreditResult;
            }

            public String getBankCreditResultRemark() {
                return bankCreditResultRemark;
            }

            public void setBankCreditResultRemark(String bankCreditResultRemark) {
                this.bankCreditResultRemark = bankCreditResultRemark;
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

            public String getAuthPdf() {
                return authPdf;
            }

            public void setAuthPdf(String authPdf) {
                this.authPdf = authPdf;
            }

            public String getInterviewPic() {
                return interviewPic;
            }

            public void setInterviewPic(String interviewPic) {
                this.interviewPic = interviewPic;
            }

            public String getDataCreditReport() {
                return dataCreditReport;
            }

            public void setDataCreditReport(String dataCreditReport) {
                this.dataCreditReport = dataCreditReport;
            }

            public String getBankCreditReport() {
                return BankCreditReport;
            }

            public void setBankCreditReport(String BankCreditReport) {
                this.BankCreditReport = BankCreditReport;
            }
        }

        public static class AttachmentsBean implements  Serializable{
            /**
             * code : AT201904251534336142581
             * bizCode : CB201904251534335678302
             * category : credit_user
             * kname : id_no_front_apply
             * vname : 申请人身份证正面
             * attachType : 图片
             * url : ANDROID_1556177340083_1080_1920.jpg
             */

            private String code;
            private String bizCode;
            private String category;
            private String kname;
            private String vname;
            private String attachType;
            private String url;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getKname() {
                return kname;
            }

            public void setKname(String kname) {
                this.kname = kname;
            }

            public String getVname() {
                return vname;
            }

            public void setVname(String vname) {
                this.vname = vname;
            }

            public String getAttachType() {
                return attachType;
            }

            public void setAttachType(String attachType) {
                this.attachType = attachType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class BizTasksBean implements  Serializable{
            /**
             * code : BT201904251534336002578
             * bizCode : CB201904251534335678302
             * refType : b0
             * refOrder : CB201904251534335678302
             * refNode : b01
             * content : 你有新的待新录面签信息面签单
             * createDatetime : Apr 25, 2019 3:34:33 PM
             * status : 1
             * finishDatetime : Apr 25, 2019 4:38:32 PM
             * operater : U201812010243173663058
             * operateRole : SR201800000000000000YWY
             */

            private String code;
            private String bizCode;
            private String refType;
            private String refOrder;
            private String refNode;
            private String content;
            private String createDatetime;
            private String status;
            private String finishDatetime;
            private String operater;
            private String operateRole;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getRefType() {
                return refType;
            }

            public void setRefType(String refType) {
                this.refType = refType;
            }

            public String getRefOrder() {
                return refOrder;
            }

            public void setRefOrder(String refOrder) {
                this.refOrder = refOrder;
            }

            public String getRefNode() {
                return refNode;
            }

            public void setRefNode(String refNode) {
                this.refNode = refNode;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateDatetime() {
                return createDatetime;
            }

            public void setCreateDatetime(String createDatetime) {
                this.createDatetime = createDatetime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getFinishDatetime() {
                return finishDatetime;
            }

            public void setFinishDatetime(String finishDatetime) {
                this.finishDatetime = finishDatetime;
            }

            public String getOperater() {
                return operater;
            }

            public void setOperater(String operater) {
                this.operater = operater;
            }

            public String getOperateRole() {
                return operateRole;
            }

            public void setOperateRole(String operateRole) {
                this.operateRole = operateRole;
            }
        }

        public static class BizLogsBean implements  Serializable{
            /**
             * id : 257
             * bizCode : CB201904251534335678302
             * refType : a
             * refOrder : CB201904251534335678302
             * dealNode : a1
             * dealNote : 说明
             * operateRole : SR201800000000000000YWY
             * operator : U201812010243173663058
             * operatorName : 张明
             * operatorMobile : 15699150679
             * startDatetime : Apr 25, 2019 3:34:33 PM
             * endDatetime : Apr 25, 2019 3:34:33 PM
             * speedTime : 0
             */

            private int id;
            private String bizCode;
            private String refType;
            private String refOrder;
            private String dealNode;
            private String dealNote;
            private String operateRole;
            private String operator;
            private String operatorName;
            private String operatorMobile;
            private String startDatetime;
            private String endDatetime;
            private String speedTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getRefType() {
                return refType;
            }

            public void setRefType(String refType) {
                this.refType = refType;
            }

            public String getRefOrder() {
                return refOrder;
            }

            public void setRefOrder(String refOrder) {
                this.refOrder = refOrder;
            }

            public String getDealNode() {
                return dealNode;
            }

            public void setDealNode(String dealNode) {
                this.dealNode = dealNode;
            }

            public String getDealNote() {
                return dealNote;
            }

            public void setDealNote(String dealNote) {
                this.dealNote = dealNote;
            }

            public String getOperateRole() {
                return operateRole;
            }

            public void setOperateRole(String operateRole) {
                this.operateRole = operateRole;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getOperatorName() {
                return operatorName;
            }

            public void setOperatorName(String operatorName) {
                this.operatorName = operatorName;
            }

            public String getOperatorMobile() {
                return operatorMobile;
            }

            public void setOperatorMobile(String operatorMobile) {
                this.operatorMobile = operatorMobile;
            }

            public String getStartDatetime() {
                return startDatetime;
            }

            public void setStartDatetime(String startDatetime) {
                this.startDatetime = startDatetime;
            }

            public String getEndDatetime() {
                return endDatetime;
            }

            public void setEndDatetime(String endDatetime) {
                this.endDatetime = endDatetime;
            }

            public String getSpeedTime() {
                return speedTime;
            }

            public void setSpeedTime(String speedTime) {
                this.speedTime = speedTime;
            }
        }

        public static class CreditUserListBean implements Serializable {
            /**
             * code : CU201904251534336056731
             * bizCode : CB201904251534335678302
             * relation : 4
             * loanRole : 1
             * userName : 姓名2
             * mobile : 13333333333
             * idNo : 410621199505101019
             * bankCreditResult : 0
             * bankCreditResultRemark : 思路going
             * idFront : ANDROID_1556177340083_1080_1920.jpg
             * idReverse : ANDROID_1556177346179_1080_1920.jpg
             * authPdf : ANDROID_1556177353362_1080_1920.jpg
             * interviewPic : ANDROID_1556177358545_1080_1920.jpg
             * dataCreditReport : ANDROID_1556178464808_1080_1920.jpg
             * BankCreditReport : ANDROID_1556178458332_1080_1920.jpg
             */

            private String code;
            private String bizCode;
            private String relation;
            private String loanRole;
            private String userName;
            private String mobile;
            private String idNo;
            private String bankCreditResult;
            private String bankCreditResultRemark;
            private String idFront;
            private String idReverse;
            private String authPdf;
            private String interviewPic;
            private String dataCreditReport;
            private String BankCreditReport;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getLoanRole() {
                return loanRole;
            }

            public void setLoanRole(String loanRole) {
                this.loanRole = loanRole;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getBankCreditResult() {
                return bankCreditResult;
            }

            public void setBankCreditResult(String bankCreditResult) {
                this.bankCreditResult = bankCreditResult;
            }

            public String getBankCreditResultRemark() {
                return bankCreditResultRemark;
            }

            public void setBankCreditResultRemark(String bankCreditResultRemark) {
                this.bankCreditResultRemark = bankCreditResultRemark;
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

            public String getAuthPdf() {
                return authPdf;
            }

            public void setAuthPdf(String authPdf) {
                this.authPdf = authPdf;
            }

            public String getInterviewPic() {
                return interviewPic;
            }

            public void setInterviewPic(String interviewPic) {
                this.interviewPic = interviewPic;
            }

            public String getDataCreditReport() {
                return dataCreditReport;
            }

            public void setDataCreditReport(String dataCreditReport) {
                this.dataCreditReport = dataCreditReport;
            }

            public String getBankCreditReport() {
                return BankCreditReport;
            }

            public void setBankCreditReport(String BankCreditReport) {
                this.BankCreditReport = BankCreditReport;
            }
        }
    }
}
