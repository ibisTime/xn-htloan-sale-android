package com.cdkj.wzcd;

import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.appmanager.SPUtilHelper;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.model.DataDictionary;
import com.cdkj.wzcd.model.NodeModel;
import com.cdkj.wzcd.module.user.SignInActivity;
import com.cdkj.wzcd.util.BizTypeHelper;
import com.cdkj.wzcd.util.NodeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by cdkj on 2018/4/10.
 */

public class SplashActivity extends BaseActivity {


    // 节点列表
    public static List<NodeModel> BASE_NODE_LIST = new ArrayList<>();
    // 业务种类
    public static List<DataDictionary> BASE_BIZ_TYPE = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity

        try {
            if (getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.activity_splash);

        getNoedAll();
    }

    private void jion() {
        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    if (!SPUtilHelper.isLoginNoStart()) {  //没有登录
                        SignInActivity.open(this, false);
                    } else {
                        MainActivity.open(this);
                    }
                    finish();

                }, Throwable::printStackTrace));
    }

    private void getNoedAll() {
        //获取所有的节点  和  数据字典

        NodeHelper.getNodeBaseDataRequest(null, "", "", new NodeHelper.NodeInterface() {
            @Override
            public void onSuccess(List<NodeModel> list) {

                BASE_NODE_LIST = list;
                BizTypeHelper.getBizTypeBaseDataRequest(null, "", new BizTypeHelper.BizTypeInterface() {
                    @Override
                    public void onSuccess(List<DataDictionary> list) {
                        BASE_BIZ_TYPE = list;
                        jion();
                    }

                    @Override
                    public void onReqFailure(String errorCode, String errorMessage) {
                        jion();
                    }
                });

            }

            @Override
            public void onReqFailure(String errorCode, String errorMessage) {
                jion();
            }
        });
    }


}
