package com.lsj.lsjnews.common;

import android.content.Context;
import android.content.Intent;

import com.lsj.lsjnews.activity.ActivityNewsWeb;
import com.lsj.lsjnews.activity.NewsInfoShow;

/**
 * Created by lsj on 2016/2/27.
 */
public class UiHelper {

    /**
     * 外部新闻界面
     * @param context
     * @param url
     */
    public static void showNewsInfoWeb(Context context, String url){
        Intent intent = new Intent();
        intent.putExtra("news_url",url);
        intent.setClass(context, ActivityNewsWeb.class);
        context.startActivity(intent);
    }
    public static void showNewsInfoThis(Context context, String id){
        Intent intent = new Intent();
        intent.setClass(context, NewsInfoShow.class);
        intent.putExtra("mNewsId", id);
        context.startActivity(intent);
    }
}
