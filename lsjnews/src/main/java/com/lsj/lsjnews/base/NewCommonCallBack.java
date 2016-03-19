package com.lsj.lsjnews.base;

import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/3/19.
 */
public abstract class NewCommonCallBack implements Callback.CommonCallback<String>{
    @Override
    public abstract void onSuccess(String s);

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
