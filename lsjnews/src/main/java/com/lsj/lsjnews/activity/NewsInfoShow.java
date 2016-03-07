package com.lsj.lsjnews.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsDetail;
import com.lsj.lsjnews.http.HttpHelper;
import com.lsj.lsjnews.http.MyApi;
import com.lsj.lsjnews.utils.AnimUtil;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.http.RequestParams;
import zhou.widget.RichText;

/**
 * Created by lsj on 2016/3/2.
 */
public class NewsInfoShow extends MyBaseActivity{
    private RichText mTextView;
    private String mNewsId, mImgSrc;
    private TextView mTxtTitle, mTxtDate, mTxtSource;
    private ImageView mImgHead;
    private VideoView mVideoHead;
    private LsjLoadingView mViewLoading;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        mNewsId = getIntent().getStringExtra("mNewsId");
        mImgSrc = getIntent().getStringExtra("imgSrc");
    }

    @Override
    protected void initView() {
        super.initView();
        mTextView = (RichText) findViewById(R.id.txt_news_content_msg);
        mTxtTitle = (TextView) findViewById(R.id.txt_news_content_title);
        mTxtDate = (TextView) findViewById(R.id.txt_news_content_time);
        mTxtSource = (TextView) findViewById(R.id.txt_news_content_source);
        mImgHead = (ImageView) findViewById(R.id.img_news_head_img);
        mVideoHead = (VideoView) findViewById(R.id.video_news_head);
        mViewLoading = (LsjLoadingView) findViewById(R.id.view_news_video_loading);
    }

    @Override
    protected void initData() {
        getInfo();
        mTextView.startAnimation(AnimUtil.getBottomInAnim100(mContext));
        mTxtDate.startAnimation(AnimUtil.getRightInAnim(mContext));
        mTxtSource.startAnimation(AnimUtil.getRightInAnim(mContext));
        mTxtTitle.startAnimation(AnimUtil.getLeftInAnim(mContext));
//        baseManager.getBaseView().startAnimation(animation);
    }
    private void getInfo(){
        String http_link = MyApi.NEWS_DETAIL+mNewsId+MyApi.ENDDETAIL_URL;
        RequestParams params = new RequestParams(http_link);
        HttpHelper.getNewsData(params, new NewCallBack(){
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                LsjNewsDetail mDetail = JSON.parseObject(s, LsjNewsDetail.class);
                if(mDetail == null || mDetail.getBody() == null){
                    MyToast.showToast(mContext, "新闻获取不了");
                    finish();
                }
                mTextView.setText(Html.fromHtml(mDetail.getBody()));
                mTxtTitle.setText(mDetail.getTitle());
                mTxtDate.setText(mDetail.getPtime());
                mTxtSource.setText(mDetail.getSource());
                if(mDetail.getVideo() == null){

                    mVideoHead.setVisibility(View.GONE);
                    mImgHead.setVisibility(View.VISIBLE);
//                    ImageLoader.getInstance().displayImage(mImgSrc, mImgHead);
                    if(mDetail.getImg() != null && mDetail.getImg().size() != 0){
                        Glide.with(mContext).load(mDetail.getImg().get(0).getSrc()).into(mImgHead)
//                                .placeholder(R.drawable.loading) //加载完之前的图
                        ;
                    }else{
                        Glide.with(mContext).load(mImgSrc).placeholder(R.drawable.loading).into(mImgHead);
                    }

                }else{

                    mVideoHead.setVisibility(View.VISIBLE);
                    mViewLoading.setVisibility(View.VISIBLE);
                    mViewLoading.startLoadingAnim();
                    mImgHead.setVisibility(View.GONE);
                    mVideoHead.setMediaController(new MediaController(mContext));
                    mVideoHead.setVideoURI(Uri.parse(mDetail.getVideo().get(0).getUrl_mp4()));
                    mVideoHead.requestFocus();
                    mVideoHead.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mVideoHead.start();
                        }
                    });
                    mVideoHead.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mViewLoading.setVisibility(View.GONE);
                            mViewLoading.onCleanAnim();
                            mVideoHead.start();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_msg_show;
    }
}
