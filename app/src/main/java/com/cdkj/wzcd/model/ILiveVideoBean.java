package com.cdkj.wzcd.model;

/**
 * @updateDts 2018/10/25
 */

public class ILiveVideoBean {
    String videoUrl;
    String roomId;
    String streamId;
    boolean isBoos;

    public boolean isBoos() {
        return isBoos;
    }

    public void setBoos(boolean boos) {
        isBoos = boos;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
