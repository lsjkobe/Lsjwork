package com.example.lsj.httplibrary.callback;

import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;

/**
 * Created by Le on 2016/1/9.
 */
public class MyCallBack implements RequestTracker {
    @Override
    public void onWaiting(RequestParams requestParams) {}

    @Override
    public void onStart(RequestParams requestParams) {}

    @Override
    public void onRequestCreated(UriRequest uriRequest) {}

    @Override
    public void onCache(UriRequest uriRequest, Object o) {

    }

    @Override
    public void onSuccess(UriRequest uriRequest, Object o) {

    }

    @Override
    public void onCancelled(UriRequest uriRequest) {

    }

    @Override
    public void onError(UriRequest uriRequest, Throwable throwable, boolean b) {

    }

    @Override
    public void onFinished(UriRequest uriRequest) {

    }
}
