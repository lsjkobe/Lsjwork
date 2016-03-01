package com.lsj.lsjnews.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;

/**
 * Created by Administrator on 2016/2/27.
 */
public class ActivityNewsWeb extends MyBaseActivity{
    private WebView mWebNewsInfo;
    private String url;
    private ProgressBar mProgressBar;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        url = getIntent().getStringExtra("news_url");
    }

    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mWebNewsInfo = (WebView) findViewById(R.id.web_news_info);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_news_web);
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
        mWebNewsInfo.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
            }

        });

        mWebNewsInfo.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected void onPause ()
    {
        //解决播放视频途中退出还有声音的问题
        mWebNewsInfo.reload ();
        super.onPause ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_web;
    }
}
