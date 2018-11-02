package com.cdkj.wzcd.model;

import java.util.List;

/**
 * @updateDts 2018/11/2
 */

public class RecVideoBean {


    /**
     * errmsg : return successfully!
     * message : return successfully!
     * output : {"all_count":1,"file_list":[{"appid":"1257046543","create_time":"2018-11-02 13:48:12","end_time":"2018-11-02 13:48:09","err_code":"0","expire_time":"2038-01-19 11:14:07","file_format":"3","file_id":"5285890782777207037","file_size":"4571794","id":"276837460","record_file_id":"5285890782777207037","record_file_url":"http://1257046543.vod2.myqcloud.com/c78eb187vodcq1257046543/07ef3cd35285890782777207037/f0.mp4","record_type":null,"report_message":null,"start_time":"2018-11-02 13:47:40","stream_id":"32810_f0403e6ebfbe1f6d2df9b4d6b0eba2f0","task_id":null,"task_sub_type":"0","vid":"210130088_4532e20cf01c42f99afbf549fe71901c","vod2Flag":"1"}]}
     * ret : 0
     * retcode : 0
     */

    private String errmsg;
    private String message;
    private OutputBean output;
    private int ret;
    private int retcode;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OutputBean getOutput() {
        return output;
    }

    public void setOutput(OutputBean output) {
        this.output = output;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public static class OutputBean {
        /**
         * all_count : 1
         * file_list : [{"appid":"1257046543","create_time":"2018-11-02 13:48:12","end_time":"2018-11-02 13:48:09","err_code":"0","expire_time":"2038-01-19 11:14:07","file_format":"3","file_id":"5285890782777207037","file_size":"4571794","id":"276837460","record_file_id":"5285890782777207037","record_file_url":"http://1257046543.vod2.myqcloud.com/c78eb187vodcq1257046543/07ef3cd35285890782777207037/f0.mp4","record_type":null,"report_message":null,"start_time":"2018-11-02 13:47:40","stream_id":"32810_f0403e6ebfbe1f6d2df9b4d6b0eba2f0","task_id":null,"task_sub_type":"0","vid":"210130088_4532e20cf01c42f99afbf549fe71901c","vod2Flag":"1"}]
         */

        private int all_count;
        private List<FileListBean> file_list;

        public int getAll_count() {
            return all_count;
        }

        public void setAll_count(int all_count) {
            this.all_count = all_count;
        }

        public List<FileListBean> getFile_list() {
            return file_list;
        }

        public void setFile_list(List<FileListBean> file_list) {
            this.file_list = file_list;
        }

        public static class FileListBean {
            /**
             * appid : 1257046543
             * create_time : 2018-11-02 13:48:12
             * end_time : 2018-11-02 13:48:09
             * err_code : 0
             * expire_time : 2038-01-19 11:14:07
             * file_format : 3
             * file_id : 5285890782777207037
             * file_size : 4571794
             * id : 276837460
             * record_file_id : 5285890782777207037
             * record_file_url : http://1257046543.vod2.myqcloud.com/c78eb187vodcq1257046543/07ef3cd35285890782777207037/f0.mp4
             * record_type : null
             * report_message : null
             * start_time : 2018-11-02 13:47:40
             * stream_id : 32810_f0403e6ebfbe1f6d2df9b4d6b0eba2f0
             * task_id : null
             * task_sub_type : 0
             * vid : 210130088_4532e20cf01c42f99afbf549fe71901c
             * vod2Flag : 1
             */

            private String appid;
            private String create_time;
            private String end_time;
            private String err_code;
            private String expire_time;
            private String file_format;
            private String file_id;
            private String file_size;
            private String id;
            private String record_file_id;
            private String record_file_url;
            private Object record_type;
            private Object report_message;
            private String start_time;
            private String stream_id;
            private Object task_id;
            private String task_sub_type;
            private String vid;
            private String vod2Flag;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getErr_code() {
                return err_code;
            }

            public void setErr_code(String err_code) {
                this.err_code = err_code;
            }

            public String getExpire_time() {
                return expire_time;
            }

            public void setExpire_time(String expire_time) {
                this.expire_time = expire_time;
            }

            public String getFile_format() {
                return file_format;
            }

            public void setFile_format(String file_format) {
                this.file_format = file_format;
            }

            public String getFile_id() {
                return file_id;
            }

            public void setFile_id(String file_id) {
                this.file_id = file_id;
            }

            public String getFile_size() {
                return file_size;
            }

            public void setFile_size(String file_size) {
                this.file_size = file_size;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRecord_file_id() {
                return record_file_id;
            }

            public void setRecord_file_id(String record_file_id) {
                this.record_file_id = record_file_id;
            }

            public String getRecord_file_url() {
                return record_file_url;
            }

            public void setRecord_file_url(String record_file_url) {
                this.record_file_url = record_file_url;
            }

            public Object getRecord_type() {
                return record_type;
            }

            public void setRecord_type(Object record_type) {
                this.record_type = record_type;
            }

            public Object getReport_message() {
                return report_message;
            }

            public void setReport_message(Object report_message) {
                this.report_message = report_message;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getStream_id() {
                return stream_id;
            }

            public void setStream_id(String stream_id) {
                this.stream_id = stream_id;
            }

            public Object getTask_id() {
                return task_id;
            }

            public void setTask_id(Object task_id) {
                this.task_id = task_id;
            }

            public String getTask_sub_type() {
                return task_sub_type;
            }

            public void setTask_sub_type(String task_sub_type) {
                this.task_sub_type = task_sub_type;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getVod2Flag() {
                return vod2Flag;
            }

            public void setVod2Flag(String vod2Flag) {
                this.vod2Flag = vod2Flag;
            }
        }
    }
}
