package com.cdkj.wzcd.model;

/**
 * @updateDts 2019/4/17
 */
public class ZRDDetialsBean {

    private String title;
    private boolean isSelect;

    public ZRDDetialsBean() {

    }

    public ZRDDetialsBean(String title) {
        this.title = title;
    }

    public ZRDDetialsBean(String title, boolean isSelect) {
        this.title = title;
        this.isSelect = isSelect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
