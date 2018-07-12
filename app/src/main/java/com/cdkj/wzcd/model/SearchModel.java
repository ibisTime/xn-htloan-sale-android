package com.cdkj.wzcd.model;

/**
 * Created by cdkj on 2018/7/6.
 */

public class SearchModel {

    // 搜索key文本
    private String keyTypeText;
    // 搜索类型:文本，时间，选择，输入查询
    private String searchType;
    // 搜索 searchKey
    private String searchKey;
    // 搜索 searchValue
    private String searchValue;
    // 搜索 searchValue的转义文本
    private String searchValueText;

    public String getSearchValueText() {
        return searchValueText;
    }

    public void setSearchValueText(String searchValueText) {
        this.searchValueText = searchValueText;
    }

    public String getKeyTypeText() {
        return keyTypeText;
    }

    public SearchModel setKeyTypeText(String keyTypeText) {
        this.keyTypeText = keyTypeText;
        return this;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public SearchModel setSearchKey(String searchKey) {
        this.searchKey = searchKey;

        return this;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public SearchModel setSearchValue(String searchValue) {
        this.searchValue = searchValue;

        return this;
    }

    public String getSearchType() {
        return searchType;
    }

    public SearchModel setSearchType(String searchType) {
        this.searchType = searchType;

        return this;
    }
}
