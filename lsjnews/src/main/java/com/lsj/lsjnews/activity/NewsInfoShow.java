package com.lsj.lsjnews.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.bean.JsonRootBean;
import com.lsj.lsjnews.bean.LsjNewsDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

/**
 * Created by Le on 2016/3/2.
 */
public class NewsInfoShow extends MyBaseActivity{
    TextView mTextView;
    @Override
    protected void initView() {
        super.initView();
        mTextView = (TextView) findViewById(R.id.txt_news_content_msg);
    }

    @Override
    protected void initData() {
        getInfo();
    }
    private void getInfo(){
        RequestParams params = new RequestParams("http://c.m.163.com/nc/article/BFNFMVO800034JAU/full.html");
//        baseManager.http2Post(params, JsonRootBean.class, new LsjBaseCallBack(){
//            @Override
//            public void onSucces(Object result) {
//                JsonRootBean mDetail = (JsonRootBean) result;
//                MyToast.showToast(mContext, "--------------success------------:"+mDetail.getDocid()+":123");
//            }
//        });
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3,"abc："+s);
                LsjNewsDetail mBean = JSON.parseObject(s, LsjNewsDetail.class);
                MyLogger.showLogWithLineNum(3,"--------------success------------:"+mBean.getBody()+":123");
                mTextView.setText(s);
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

            @Override
            public boolean onCache(String s) {
                return false;
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_msg_show;
    }
}
