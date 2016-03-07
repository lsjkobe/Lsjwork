package com.lsj.lsjnews.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;

/**
 * Created by Administrator on 2016/3/7.
 */
public class LsjLoadingView extends View{
    private int screenWidth, screenHight;
    private int mWidth, mHight;
    private int mSpace ;
    private float mLEdge,mSEdge;
    private float mStep = 0;
    private Context mContext;
    private int mDefoultRadius, mChangeRedius;
    private Paint mPaintPoint0, mPaintPoint1,mPaintPoint2,mPaintPoint3;
    public LsjLoadingView(Context context) {
        this(context, null);
    }

    public LsjLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LsjLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //获取屏幕宽度
        screenWidth = LPhone.getScreenWidth(mContext);
        screenHight = screenWidth;

        mSpace = screenWidth / 20;
        mStep = mSpace;
        mDefoultRadius = screenWidth / 60;
        mChangeRedius = screenWidth /60;
        mWidth = mSpace*3;
        mHight = mWidth;
        mLEdge = (float) (mSpace / Math.sqrt(3));
        mSEdge = (float) mSpace/2;
        mLEdge = (float) Math.sqrt(mSpace*mSpace - mSEdge*mSEdge);
        initPaint();

    }

    private void initPaint() {
        mPaintPoint0 = new Paint();
        mPaintPoint0.setStyle(Paint.Style.FILL);
        mPaintPoint0.setAntiAlias(true);
        mPaintPoint0.setColor(Color.WHITE);

        mPaintPoint1 = new Paint();
        mPaintPoint1.setStyle(Paint.Style.FILL);
        mPaintPoint1.setAntiAlias(true);
        mPaintPoint1.setColor(Color.BLUE);

        mPaintPoint2 = new Paint();
        mPaintPoint2.setStyle(Paint.Style.FILL);
        mPaintPoint2.setAntiAlias(true);
        mPaintPoint2.setColor(Color.RED);

        mPaintPoint3 = new Paint();
        mPaintPoint3.setStyle(Paint.Style.FILL);
        mPaintPoint3.setAntiAlias(true);
        mPaintPoint3.setColor(Color.GREEN);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(screenWidth/2, screenHight/2 - mStep, mDefoultRadius, mPaintPoint0);
        canvas.drawCircle(mWidth/2, mHight/2 + mSpace/4  - mStep, mChangeRedius, mPaintPoint1);
        canvas.drawCircle(mWidth/2 - mLEdge, mHight/2 + mSpace/4  + mSEdge, mChangeRedius, mPaintPoint2);
        canvas.drawCircle(mWidth/2 + mLEdge, mHight/2 + mSpace/4  + mSEdge, mChangeRedius, mPaintPoint3);

    }

    ValueAnimator mLoadingAnim1,mLoadingAnim2;
    AnimatorSet mAnimationSet;
    public void startLoadingAnim(){
        post(new Runnable() {
            @Override
            public void run() {
                mLoadingAnim1 = new ValueAnimator();
                mLoadingAnim1.setIntValues(0, mSpace);
                mLoadingAnim1.setDuration(1000);
                mLoadingAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if ((int) animation.getAnimatedValue() != 0) {
                            mStep = mSpace - (int) animation.getAnimatedValue();
                            mChangeRedius = (int) (mDefoultRadius*mStep/mSpace);
                            mSEdge = mStep / 2;
                            mLEdge = (float) Math.sqrt(mSEdge*mSEdge + mStep*mStep);
                        }
                        postInvalidate();
                    }
                });
//                mLoadingAnim1.start();

                mLoadingAnim2 = new ValueAnimator();
                mLoadingAnim2.setIntValues(0, mSpace);
                mLoadingAnim2.setDuration(1000);
                mLoadingAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if ((int) animation.getAnimatedValue() != 0) {
                            mStep = (int) animation.getAnimatedValue();
                            mChangeRedius = (int) (mDefoultRadius*mStep/mSpace);
                            mSEdge = mStep / 2;
                            mLEdge = (float) Math.sqrt(mSEdge*mSEdge + mStep*mStep);
                        }
                        postInvalidate();
                    }
                });
//                mLoadingAnim1.start();
                mAnimationSet = new AnimatorSet();
                mAnimationSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnimationSet.start();
                    }
                });
                mAnimationSet
                        .playSequentially(mLoadingAnim1, mLoadingAnim2);
                mAnimationSet.start();

            }
        });
    }

    public void onCleanAnim(){
        if (mAnimationSet != null && mAnimationSet.isRunning()) {
            mLoadingAnim1.removeAllListeners();
            mLoadingAnim1.removeAllUpdateListeners();
            mLoadingAnim1.cancel();
            mLoadingAnim2.removeAllListeners();
            mLoadingAnim2.removeAllUpdateListeners();
            mLoadingAnim2.cancel();
            mAnimationSet.removeAllListeners();
            mAnimationSet.cancel();
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimationSet != null && mAnimationSet.isRunning()) {
            mLoadingAnim1.removeAllListeners();
            mLoadingAnim1.removeAllUpdateListeners();
            mLoadingAnim1.cancel();
            mLoadingAnim2.removeAllListeners();
            mLoadingAnim2.removeAllUpdateListeners();
            mLoadingAnim2.cancel();
            mAnimationSet.removeAllListeners();
            mAnimationSet.cancel();
        }
    }
}
