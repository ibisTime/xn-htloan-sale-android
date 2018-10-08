package com.cdkj.wzcd.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * @updateDts 2018/9/28
 */

public class PickerViewDataBean implements IPickerViewData {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getPickerViewText() {
        return key;
    }
}
