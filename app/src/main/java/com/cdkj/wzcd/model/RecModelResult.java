package com.cdkj.wzcd.model;

/**
 * @updateDts 2018/11/1
 */

public class RecModelResult {

    /**
     * code : 0
     * event_id : 1541137673
     * message : Success!
     * timestamp : 1541137673
     */

    private int code;
    private String event_id;
    private String message;
    private int timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
