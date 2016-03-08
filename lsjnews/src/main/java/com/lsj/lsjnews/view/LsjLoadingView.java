package com.lsj.lsjnews.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.lsj.lsjnews.R;

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
    private Paint mPaintPoint0;
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

        mSpace = screenWidth / 18;
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
        mPaintPoint0.setColor(getResources().getColor(R.color.colorPrimary));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mW = MeasureSpec.getSize(widthMeasureSpec);
//        int mH = MeasureSpec.getSize(heightMeasureSpec);
//        int mWMode = MeasureSpec.getMode(widthMeasureSpec);
//        int mHMode = MeasureSpec.getMode(heightMeasureSpec);
//        if(mWMode == MeasureSpec.EXACTLY ){
//            mWidth = mW;
//        }
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(screenWidth/2, screenHight/2 - mStep, mDefoultRadius, mPaintPoint0);
        canvas.drawCircle(mWidth/2, mHight/2  - mStep, mChangeRedius, mPaintPoint0);
        canvas.drawCircle(mWidth/2 - mLEdge, mHight/2  + mSEdge, mChangeRedius, mPaintPoint0);
        canvas.drawCircle(mWidth/2 + mLEdge, mHight/2  + mSEdge, mChangeRedius, mPaintPoint0);

    }

    ValueAnimator mLoadingAnim1,mLoadingAnim2;
    AnimatorSet mAnimationSet;
    public void startLoadingAnim(){
        post(new Runnable() {
            @Override
            public void run() {
                mLoadingAnim1 = new ValueAnimator();
                mLoadingAnim1.setIntValues(0, mSpace);
                mLoadingAnim1.setDuration(300);
                mLoadingAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if ((int) animation.getAnimatedValue() != 0) {
                            mStep = mSpace - (int) animation.getAnimatedValue();
                            mChangeRedius = (int) (mDefoultRadius*mStep/mSpace);
                            mSEdge = mStep / 2;
//                            mLEdge = (float) Math.sqrt(mSEdge*mSEdge + mStep*mStep);
                            mLEdge = (float) (Math.sqrt(3)*mSEdge);
                        }
                        postInvalidate();
                    }
                });
                mLoadingAnim2 = new ValueAnimator();
                mLoadingAnim2.setIntValues(0, mSpace);
                mLoadingAnim2.setDuration(300);
                mLoadingAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if ((int) animation.getAnimatedValue() != 0) {
                            mStep = (int) animation.getAnimatedValue();
                            mChangeRedius = (int) (mDefoultRadius*mStep/mSpace);
                            mSEdge = mStep / 2;
//                            mLEdge = (float) Math.sqrt(mSEdge*mSEdge + mStep*mStep);
                            mLEdge = (float) (Math.sqrt(3)*mSEdge);
                        }
                        postInvalidate();
                    }
                });
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
