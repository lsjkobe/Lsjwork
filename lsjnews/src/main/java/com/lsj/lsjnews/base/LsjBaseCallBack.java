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

    }

    @Override
    public void onResult(String result) {
        MyLogger.showLogWithLineNum(3,result);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFail(Context context, FailMessg result) {

    }
}
