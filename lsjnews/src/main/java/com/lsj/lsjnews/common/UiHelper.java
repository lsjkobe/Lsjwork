package com.lsj.lsjnews.common;

import android.content.Context;
import android.content.Intent;

import com.lsj.lsjnews.activity.ActivityNewsWeb;

/**
 * Created by lsj on 2016/2/27.
 */
public class UiHelper {

    public static void showNewsInfoWeb(Context context, String url){
        Intent intent = new Intent();
        intent.putExtra("news_url",url);
        intent.setClass(context, ActivityNewsWeb.class);
        context.startActivity(intent);
    }
}
