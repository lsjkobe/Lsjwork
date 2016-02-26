package com.example.lsj.httplibrary.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lsj.httplibrary.callback.BaseCallBack;

import org.xutils.http.RequestParams;

/**
 * Created by lsj on 2016/1/9.
 */
public class BaseManager {
    private BaseHelper baseHelper;
    private Context context;
    private boolean isLoadSuccess = false;

    public BaseManager(Context context){
        this.context = context;
        baseHelper = new BaseHelper(context);
    }

    public BaseHelper getBaseHelper() {
        return baseHelper;
    }

    public Context getContext(){
        return context;
    }

    public void setIsLoadSuccess(boolean isLoadSuccess){
        this.isLoadSuccess=isLoadSuccess;
    }

    public boolean getIsLoadSuccess(){
        return isLoadSuccess;
    }

    /**
     *
     * @param params
     * @param cls
     * @param BaseCallBack
     * @param <T>
     */
    public <T>  void http2Get(RequestParams params, final Class<T> cls, final BaseCallBack BaseCallBack){
        BaseHelper.LoadConfig config=new BaseHelper.LoadConfig(false, false);
        http2Get(params, cls, BaseCallBack,config);
    }

    /**
     *
     * @param params
     * @param cls
     * @param BaseCallBack
     * @param loadConfig
     * @param <T>
     */
    public <T>  void http2Get(RequestParams params, final Class<T> cls, final BaseCallBack BaseCallBack,final BaseHelper.LoadConfig loadConfig){
        baseHelper.getData(params, BaseHelper.HTTPGET, cls, BaseCallBack, loadConfig);
    }

    public <T>  void http2Post(RequestParams params, final Class<T> cls, final BaseCallBack BaseCallBack){
        BaseHelper.LoadConfig config=new BaseHelper.LoadConfig(false, false);
        http2Post(params, cls, BaseCallBack, config);
    }
    public <T>  void http2Post(RequestParams params, final Class<T> cls, final BaseCallBack BaseCallBack,final BaseHelper.LoadConfig loadConfig){
        baseHelper.getData(params, BaseHelper.HTTPPOST, cls, BaseCallBack, loadConfig);
    }
    public void setTitle(String title){
        baseHelper.setTitle(title);
    }

    public ImageView getLeftImage(){
        return  baseHelper.getLeftImage();
    }

    public ImageView getRightImage(){
        return baseHelper.getRightImage();
    }

    public LinearLayout getLeftContent(){
        return baseHelper.getLeftContent();
    }

    public LinearLayout getRightContent(){
        return baseHelper.getRightContent();
    }

    public TextView getTopTitle(){
        return baseHelper.getTitle();
    }

    public View getTopView(){
        return baseHelper.getTopView();
    }

    public void showProgressbar(){
        baseHelper.showProgressbar(new BaseHelper.LoadConfig());
    }

    public void hideProgressbar(){
        baseHelper.hideProgressbar();
    }

    public View initBaseView(int layoutId){
        return baseHelper.initBaseView(layoutId);
    }

    public View getBaseView(){
        return baseHelper.getBaseView();
    }

    public void showTopView(boolean isShow){
        baseHelper.showTopView(isShow);
    }

    public void setOnOnErrorListener(BaseHelper.OnErrorListener onErrorListener){
        baseHelper.setOnOnErrorListener(onErrorListener);
    }
}
