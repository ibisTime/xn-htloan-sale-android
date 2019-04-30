package com.cdkj.wzcd.model;

import java.util.List;

/**
 * @updateDts 2019/4/16
 */
public class MessageTobeBean {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 52
     * totalPage : 6
     * list : [{"code":"BT201904171817150656237","bizCode":"cb201904171817150105709","refType":"a","refOrder":"C201904171817150159074","refNode":"a1","content":"你有新的待新录征信资料征信单","createDatetime":"Apr 17, 2019 6:17:15 PM","status":"0","operater":"USYS201800000000001","operateRole":"RO201800000000000001"}]
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

    public static class ListBean {
        /**
         * code : BT201904171817150656237
         * bizCode : cb201904171817150105709
         * refType : a
         * refOrder : C201904171817150159074
         * refNode : a1
         * content : 你有新的待新录征信资料征信单
         * createDatetime : Apr 17, 2019 6:17:15 PM
         * status : 0
         * operater : USYS201800000000001
         * operateRole : RO201800000000000001
         */

        private String code;
        private String bizCode;
        private String refType;
        private String refOrder;
        private String refNode;
        private String content;
        private String createDatetime;
        private String status;
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
}
