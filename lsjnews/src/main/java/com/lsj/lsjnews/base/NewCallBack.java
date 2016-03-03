package com.lsj.lsjnews.base;

import android.content.Context;

import com.example.lsj.httplibrary.callback.BaseCallBack;
import com.example.lsj.httplibrary.callback.FailMessg;
import com.example.lsj.httplibrary.utils.MyLogger;

import org.xutils.common.Callback;


/**
 * Created by Administrator on 2016/2/27.
 */
public class NewCallBack implements Callback.CommonCallback<String> {
    @Override
    public void onSuccess(String s) {

    }

    @Override
    public void onError(Throwable throwable, boolean b) {

    }

    @Override
    public void onCancelled(CancelledException e) {

    }

    @Override
    public void onFinished() {

    }
}
