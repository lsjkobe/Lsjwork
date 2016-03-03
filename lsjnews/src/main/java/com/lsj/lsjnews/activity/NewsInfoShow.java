package com.lsj.lsjnews.activity;

import android.text.Html;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsDetail;
import com.lsj.lsjnews.http.HttpHelper;
import com.lsj.lsjnews.http.MyApi;
import com.lsj.lsjnews.test_bean.JsonRootBean;
import com.lsj.lsjnews.test_bean.testBean;
import com.lsj.lsjnews.utils.MyUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import zhou.widget.RichText;

/**
 * Created by Le on 2016/3/2.
 */
public class NewsInfoShow extends MyBaseActivity{
    RichText mTextView;
    private String mNewsId;

    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        mNewsId = getIntent().getStringExtra("mNewsId");
    }

    @Override
    protected void initView() {
        super.initView();
        mTextView = (RichText) findViewById(R.id.txt_news_content_msg);
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
                LsjNewsDetail abc = JSON.parseObject(s, LsjNewsDetail.class);
                mTextView.setText(Html.fromHtml(abc.getBody()));
            }
        });
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                String a = MyUtil.getStrFromJson(s);
//                MyLogger.showLogWithLineNum(3,"======================a:"+a);
//                LsjNewsDetail abc = JSON.parseObject(a, LsjNewsDetail.class);
//                mTextView.setText(Html.fromHtml(abc.getBody()));
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//                MyLogger.showLogWithLineNum(3,"======================a:onError");
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//                MyLogger.showLogWithLineNum(3,"======================onCancelled:");
//            }
//
//            @Override
//            public void onFinished() {
//                MyLogger.showLogWithLineNum(3,"======================a:onFinished");
//            }
//        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_msg_show;
    }
}
