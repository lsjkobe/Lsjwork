package com.lsj.lsjnews.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by lsj on 2016/4/6.
 * 解决nestedScrollView嵌套RecyclerView滑动没惯性的问题
 */
public class MyNestedScrollView extends NestedScrollView{

    private int downX;
    private int downY;
    private int mTouchSlop;
    public MyNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedScrollView(Context context) {
        this(context,null);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
