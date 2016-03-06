package com.lsj.lsjnews.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lsj.lsjnews.R;

/**
 * Created by Administrator on 2016/3/6.
 */
public class AnimUtil {


    public static Animation getLeftInAnim(Context context){
        return AnimationUtils.loadAnimation(context, R.anim.item_left_in);
    }
    public static Animation getRightInAnim(Context context){
        return AnimationUtils.loadAnimation(context, R.anim.item_right_in);
    }
    public static Animation getBottomInAnim(Context context){
        return AnimationUtils.loadAnimation(context, R.anim.item_bottom_in);
    }
    public static Animation getBottomInAnim100(Context context){
        return AnimationUtils.loadAnimation(context, R.anim.item_bottom_in_100);
    }
    public static Animation getTopInAnim(Context context){
        return null;
    }
}
