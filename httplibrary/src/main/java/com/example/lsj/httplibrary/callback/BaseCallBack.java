package com.example.lsj.httplibrary.callback;

import android.content.Context;

/**
 * Created by Le on 2016/1/9.
 */


public abstract class BaseCallBack {

    public boolean onSuccesBefore(Object result, Context context) {
        return false;
    };

    public abstract void onSucces(Object result);

    public abstract void onResult(String result);

    public abstract void onStart();

    public abstract void onFinish();

    public abstract void onFail(Context context, FailMessg result);
}
