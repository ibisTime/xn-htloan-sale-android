package com.cdkj.wzcd.model;

import java.util.List;

/**
 * @updateDts 2019/4/16
 */
public class MessageSystemBean {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"XX201903141638233084532","type":"3","title":"805300","content":"用postman调用接口加","status":"1","createDatetime":"Mar 14, 2019 4:38:23 PM","updater":"USYS201800000000001","sysUser":{"userId":"USYS201800000000001","type":"P","loginName":"admin","realName":"admin","createDatetime":"Jun 12, 2018 12:23:42 PM","companyCode":"DP201800000000000000001","departmentCode":"DP201806131311560477449","postCode":"DP201806131312341854756","roleCode":"RO201800000000000001","updater":"USYS201800000000001","status":"1","teamCode":"BT201812010242065583765"}}]
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
         * code : XX201903141638233084532
         * type : 3
         * title : 805300
         * content : 用postman调用接口加
         * status : 1
         * createDatetime : Mar 14, 2019 4:38:23 PM
         * updater : USYS201800000000001
         * sysUser : {"userId":"USYS201800000000001","type":"P","loginName":"admin","realName":"admin","createDatetime":"Jun 12, 2018 12:23:42 PM","companyCode":"DP201800000000000000001","departmentCode":"DP201806131311560477449","postCode":"DP201806131312341854756","roleCode":"RO201800000000000001","updater":"USYS201800000000001","status":"1","teamCode":"BT201812010242065583765"}
         */

        private String code;
        private String type;
        private String title;
        private String content;
        private String status;
        private String createDatetime;
        private String updater;
        private SysUserBean sysUser;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public SysUserBean getSysUser() {
            return sysUser;
        }

        public void setSysUser(SysUserBean sysUser) {
            this.sysUser = sysUser;
        }

        public static class SysUserBean {
            /**
             * userId : USYS201800000000001
             * type : P
             * loginName : admin
             * realName : admin
             * createDatetime : Jun 12, 2018 12:23:42 PM
             * companyCode : DP201800000000000000001
             * departmentCode : DP201806131311560477449
             * postCode : DP201806131312341854756
             * roleCode : RO201800000000000001
             * updater : USYS201800000000001
             * status : 1
             * teamCode : BT201812010242065583765
             */

            private String userId;
            private String type;
            private String loginName;
            private String realName;
            private String createDatetime;
            private String companyCode;
            private String departmentCode;
            private String postCode;
            private String roleCode;
            private String updater;
            private String status;
            private String teamCode;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getCreateDatetime() {
                return createDatetime;
            }

            public void setCreateDatetime(String createDatetime) {
                this.createDatetime = createDatetime;
            }

            public String getCompanyCode() {
                return companyCode;
            }

            public void setCompanyCode(String companyCode) {
                this.companyCode = companyCode;
            }

            public String getDepartmentCode() {
                return departmentCode;
            }

            public void setDepartmentCode(String departmentCode) {
                this.departmentCode = departmentCode;
            }

            public String getPostCode() {
                return postCode;
            }

            public void setPostCode(String postCode) {
                this.postCode = postCode;
            }

            public String getRoleCode() {
                return roleCode;
            }

            public void setRoleCode(String roleCode) {
                this.roleCode = roleCode;
            }

            public String getUpdater() {
                return updater;
            }

            public void setUpdater(String updater) {
                this.updater = updater;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTeamCode() {
                return teamCode;
            }

            public void setTeamCode(String teamCode) {
                this.teamCode = teamCode;
            }
        }
    }
}
