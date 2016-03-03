package com.lsj.lsjnews.http;

import android.text.Html;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsDetail;
import com.lsj.lsjnews.utils.MyUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Le on 2016/3/3.
 */
public class HttpHelper {

    private static final String TAG = "HttpHelper.class";
    public static void getNewsData(RequestParams params, final NewCallBack mCallBack){
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                String str = MyUtil.getStrFromJson(s);

                MyLogger.showLogWithLineNum(3,TAG+""+str);
                mCallBack.onSuccess(str);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                mCallBack.onError(throwable, b);
            }

            @Override
            public void onCancelled(CancelledException e) {
                MyLogger.showLogWithLineNum(3,"======================onCancelled:");
            }

            @Override
            public void onFinished() {
                MyLogger.showLogWithLineNum(3,"======================a:onFinished");
            }
        });
    }
}
