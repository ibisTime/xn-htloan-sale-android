package com.cdkj.wzcd.model;

import java.io.Serializable;

/**
 * Created by cdkj on 2018/6/4.
 */

public class GpsInstallModel implements Serializable {

    private String code;
    private String gpsDevNo;
    private String azLocation;
    private String azDatetime;
    private String azUser;
    private String remark;
    private String devPhotos;
    private String azPhotos;

    public String getDevPhotos() {
        return devPhotos;
    }

    public void setDevPhotos(String devPhotos) {
        this.devPhotos = devPhotos;
    }

    public String getAzPhotos() {
        return azPhotos;
    }

    public void setAzPhotos(String azPhotos) {
        this.azPhotos = azPhotos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGpsDevNo() {
        return gpsDevNo;
    }

    public void setGpsDevNo(String gpsDevNo) {
        this.gpsDevNo = gpsDevNo;
    }

    public String getAzLocation() {
        return azLocation;
    }

    public void setAzLocation(String azLocation) {
        this.azLocation = azLocation;
    }

    public String getAzDatetime() {
        return azDatetime;
    }

    public void setAzDatetime(String azDatetime) {
        this.azDatetime = azDatetime;
    }

    public String getAzUser() {
        return azUser;
    }

    public void setAzUser(String azUser) {
        this.azUser = azUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
