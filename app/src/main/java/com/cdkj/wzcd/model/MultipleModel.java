package com.cdkj.wzcd.model;

/**
 * Created by cdkj on 2018/7/11.
 */

public class MultipleModel {

    private String url;
    private boolean isCanEdit = true;

    public String getUrl() {
        return url;
    }

    public MultipleModel setUrl(String url) {
        this.url = url;

        return this;
    }

    public boolean isCanEdit() {
        return isCanEdit;
    }

    public MultipleModel setCanEdit(boolean canEdit) {
        isCanEdit = canEdit;

        return this;
    }
}
