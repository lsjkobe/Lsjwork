package com.lsj.lsjnews.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lsj on 2016/4/6.
 */
public class MyScrollView extends ScrollView{

    private int lastScrollY;
    private OnScrollListener mOnScrollListener;
    public MyScrollView(Context context) {
        this(context,null);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener mOnScrollListener){
        this.mOnScrollListener = mOnScrollListener;
    }
    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = MyScrollView.this.getScrollY();
            if(mOnScrollListener != null){
                mOnScrollListener.onScroll(scrollY,lastScrollY);
            }
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }


        };

    };

    /**
     * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
     * MyScrollView滑动的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        if(mOnScrollListener != null){
//            mOnScrollListener.onScroll(lastScrollY = this.getScrollY());
//        }
        switch(ev.getAction()){
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
                break;
            case MotionEvent.ACTION_DOWN:
                lastScrollY = getScrollY();
                mOnScrollListener.onScroll(getScrollY(),lastScrollY);
                break;
            case MotionEvent.ACTION_MOVE:
                mOnScrollListener.onScroll(getScrollY(),lastScrollY);
                lastScrollY = getScrollY();
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 滚动的回调接口
     * @author lsj
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        public void onScroll(int scrollY,int oldscrollY);
    }
}
