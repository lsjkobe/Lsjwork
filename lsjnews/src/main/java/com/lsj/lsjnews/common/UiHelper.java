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
import com.lsj.lsjnews.activity.NewsMainActivity;
import com.lsj.lsjnews.activity.lsj_test1;
import com.lsj.lsjnews.mdnews.UserBBSMain;
import com.lsj.lsjnews.mdnews.UserForwardActivity;
import com.lsj.lsjnews.mdnews.UserLoginActivity;
import com.lsj.lsjnews.mdnews.UserMainActivity;
import com.lsj.lsjnews.mdnews.UserRegisterActivity;
import com.lsj.lsjnews.mdnews.UserSelectPhoto;
import com.lsj.lsjnews.mdnews.UserWriteActivity;

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

    /**
     * 显示用户登录
     * @param context
     * type那个页面跳转过来的
     * 0新闻页面 1社区页面
     */
    public static void showUserLogin(Context context, int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, UserLoginActivity.class);
        ((Activity) context).startActivityForResult(intent, 1);
    }
    /**
     * 显示用户主界面
     * @param context
     */
    public static void showUserMain(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserMainActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示用户注册
     * @param context
     */
    public static void showUserRegister(Context context){
        Intent intent = new Intent();
        intent.setClass(context, UserRegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示bbs首页
     * @param context
     */
    public static void showUserBBS(Context context){
        Intent intent = new Intent();
        // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(context, UserBBSMain.class);
        context.startActivity(intent);
    }
    /**
     * 显示新闻首页
     * @param context
     */
    public static void showUserNews(Context context){
        Intent intent = new Intent();
        // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(context, NewsMainActivity.class);
        context.startActivity(intent);
    }
    /**
     * 选择图片界面
     * @param context
     */
    public static void showSelectPhoto(Context context){
        Intent intent = new Intent();
        intent.setClass(context, UserSelectPhoto.class);
        ((Activity)context).startActivityForResult(intent,1);
    }
    /**
     * 写微博界面
     * @param context
     */
    public static void showUserWrite(Context context){
        Intent intent = new Intent();
        intent.setClass(context, UserWriteActivity.class);
        ((Activity)context).startActivityForResult(intent,1);
    }

    /**
     * 转发微博界面
     * @param context
     */
    public static void showUserForward(Context context,int mid){
        Intent intent = new Intent();
        intent.putExtra("mid",mid);
        intent.setClass(context, UserForwardActivity.class);
        ((Activity)context).startActivityForResult(intent,1);
    }
    /**
     * 测试
     * @param context
     */
    public static void showTest(Context context){
        Intent intent = new Intent();
        intent.setClass(context, lsj_test1.class);
        context.startActivity(intent);
    }
}
