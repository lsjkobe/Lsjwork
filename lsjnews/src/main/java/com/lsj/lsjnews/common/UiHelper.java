package com.lsj.lsjnews.common;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.activity.ActivityNewsWeb;
import com.lsj.lsjnews.activity.NewsInfoShow;
import com.lsj.lsjnews.activity.lsj_test1;

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
    public static void showNewsInfoThis(Context context, String id, String imgSrc, View view){
        Intent intent = new Intent();
        intent.setClass(context, NewsInfoShow.class);
        intent.putExtra("mNewsId", id);
        intent.putExtra("imgSrc", imgSrc);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context, view.findViewById(R.id.img_news_header_1), "photos");
            context.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2,
                            view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
        }
    }

    public static void showTest(Context context){
        Intent intent = new Intent();
        intent.setClass(context, lsj_test1.class);
        context.startActivity(intent);
    }
}
