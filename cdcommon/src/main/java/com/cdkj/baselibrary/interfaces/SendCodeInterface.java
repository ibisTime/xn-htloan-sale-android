package com.cdkj.baselibrary.interfaces;

import android.widget.TextView;

/**
 * Created by cdkj on 2017/8/8.
 */

public interface SendCodeInterface {

    void CodeSuccess(String msg,TextView view);    //成功

    void CodeFailed(String code, String msg);   //失败

    void StartSend();   //开始

    void EndSend();   //结束


}
