package com.lsj.lsjnews.base;

import android.content.Context;

import com.example.lsj.httplibrary.callback.BaseCallBack;
import com.example.lsj.httplibrary.callback.FailMessg;
import com.example.lsj.httplibrary.utils.MyLogger;


/**
 * Created by Administrator on 2016/2/27.
 */
public class LsjBaseCallBack extends BaseCallBack{

    @Override
    public void onSucces(Object result) {
        MyLogger.showLogWithLineNum(3,"LsjBaseCallBack.....success");
    }

    @Override
    public void onResult(String result) {
        MyLogger.showLogWithLineNum(3, "LsjBaseCallBack.....result");
        MyLogger.showLogWithLineNum(3,result);
//        ApiNewsMsg mBean = JSON.parseObject(result, ApiNewsMsg.class);
//        MyLogger.showLogWithLineNum(3,"error++++++++++++++1:"+mBean.getCode());

    }

    @Override
    public void onStart() {
        MyLogger.showLogWithLineNum(3,"LsjBaseCallBack.....start");
    }

    @Override
    public void onFinish() {
        MyLogger.showLogWithLineNum(3,"LsjBaseCallBack.....finish");
    }

    @Override
    public void onFail(Context context, FailMessg result) {
        MyLogger.showLogWithLineNum(3,"LsjBaseCallBack.....fail");
    }
}
