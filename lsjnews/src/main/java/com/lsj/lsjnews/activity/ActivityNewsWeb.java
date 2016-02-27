package com.lsj.lsjnews.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;

/**
 * Created by Administrator on 2016/2/27.
 */
public class ActivityNewsWeb extends MyBaseActivity{

    private WebView mWebNewsInfo;
    private String url;
    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mWebNewsInfo = (WebView) findViewById(R.id.web_news_info);
        url = getIntent().getStringExtra("news_url");
    }

    @Override
    protected void initData() {
        mWebNewsInfo.loadUrl(url);
        WebSettings webSettings = mWebNewsInfo.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        mWebNewsInfo.setWebViewClient(new WebViewClient());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_web;
    }
}
