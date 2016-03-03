package com.lsj.lsjnews.activity;

import android.text.Html;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsDetail;
import com.lsj.lsjnews.http.HttpHelper;
import com.lsj.lsjnews.http.MyApi;
import org.xutils.http.RequestParams;
import zhou.widget.RichText;

/**
 * Created by Le on 2016/3/2.
 */
public class NewsInfoShow extends MyBaseActivity{
    private RichText mTextView;
    private String mNewsId;
    private TextView mTxtTitle, mTxtDate, mTxtSource;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        mNewsId = getIntent().getStringExtra("mNewsId");
    }

    @Override
    protected void initView() {
        super.initView();
        mTextView = (RichText) findViewById(R.id.txt_news_content_msg);
        mTxtTitle = (TextView) findViewById(R.id.txt_news_content_title);
        mTxtDate = (TextView) findViewById(R.id.txt_news_content_time);
        mTxtSource = (TextView) findViewById(R.id.txt_news_content_source);
    }

    @Override
    protected void initData() {
        getInfo();
    }
    private void getInfo(){
        String http_link = MyApi.NEWS_DETAIL+mNewsId+MyApi.ENDDETAIL_URL;
        RequestParams params = new RequestParams(http_link);
        HttpHelper.getNewsData(params, new NewCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                LsjNewsDetail mDetail = JSON.parseObject(s, LsjNewsDetail.class);
                mTextView.setText(Html.fromHtml(mDetail.getBody()));
                mTxtTitle.setText(mDetail.getTitle());
                mTxtDate.setText(mDetail.getPtime());
                mTxtSource.setText(mDetail.getSource());
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_msg_show;
    }
}
