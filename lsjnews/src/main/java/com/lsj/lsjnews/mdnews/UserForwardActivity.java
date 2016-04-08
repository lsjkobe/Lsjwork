package com.lsj.lsjnews.mdnews;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.EmojiAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnEmojiOnclick;
import com.lsj.lsjnews.utils.EmojiParser;
import com.lsj.lsjnews.view.LsjLoadingView;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by lsj on 2016/3/21.
 */
public class UserForwardActivity extends MyBaseActivity implements View.OnClickListener{
    private EditText mEditForwardContent;
    private ImageView mImgRelease, mImgEmoji;
    private RecyclerView mRecycleEmoji;
    private EmojiAdapter mEmojiAdapter;
    private LsjLoadingView mLoading;
    private Toolbar mToolbar;
    private int sid , mid; //原圈子id

    private boolean is_emoji_open = false;

    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        sid = getIntent().getIntExtra("sid",-1);
        mid = getIntent().getIntExtra("mid",-1);
        MyLogger.showLogWithLineNum(3,"----:"+sid+":"+mid);
    }

    @Override
    protected void initView() {
        super.initView();
        mEditForwardContent = (EditText) findViewById(R.id.edit_forward_bbs_content);
        mRecycleEmoji = (RecyclerView) findViewById(R.id.recycler_forward_emoji_select);
        mImgRelease = (ImageView) findViewById(R.id.img_forward_bbs_release);
        mImgEmoji = (ImageView) findViewById(R.id.img_forward_bbs_emoji);
        mLoading = (LsjLoadingView) findViewById(R.id.loading_forward_write_bbs);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_forward_bbs_main);
        mImgRelease.setOnClickListener(this);
        mImgEmoji.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initToolbar();
        initRecycler();
        mEditForwardContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    is_emoji_open = false;
                    mRecycleEmoji.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecycler() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecycleEmoji.setLayoutManager(mGridLayoutManager);
        mEmojiAdapter = new EmojiAdapter(mContext, EmojiParser.DEFAULT_EMOJI_RES_IDS);
        mEmojiAdapter.setOnEmojiOnclick(new OnEmojiOnclick() {
            @Override
            public void getPosition(int position) {
                int currentPosition = mEditForwardContent.getSelectionStart();
                mEditForwardContent.getText().insert(currentPosition,EmojiParser.getInstance(mContext).edittextReplace(position,mEditForwardContent.getTextSize()));
            }
        });
        mRecycleEmoji.setAdapter(mEmojiAdapter);

    }


    private void initToolbar() {
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setTitle("转发圈子");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_forward_bbs_release:
                    releaseBBS();
                break;
            case R.id.img_forward_bbs_emoji:
                if(is_emoji_open){
                    is_emoji_open = false;
                    mRecycleEmoji.setVisibility(View.GONE);
                }else{
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditForwardContent.clearFocus();
                    is_emoji_open = true;
                    mRecycleEmoji.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    private String content;
    private void releaseBBS() {
        RequestParams params = new RequestParams(Conts.POST_FORWARD_BBS);
        if(mEditForwardContent.getText().toString().length() == 0){
            content = "转发";
        }else{
            content = mEditForwardContent.getText().toString();
        }
        params.addBodyParameter("content", content);
        params.addBodyParameter("sid", String.valueOf(sid));
        params.addBodyParameter("mid", String.valueOf(mid));
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {

            }
            @Override
            public void onStarted() {
                mLoading.setVisibility(View.VISIBLE);
                mLoading.startLoadingAnim();
            }

            @Override
            public void onLoading(long total, long current, boolean b) {
                MyLogger.showLogWithLineNum(3, "total:current:" + current);
            }

            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3, "---" + s);
                baseBean bean = JSON.parseObject(s, baseBean.class);
                switch (bean.getResultCode()) {
                    case 1:
                        MyToast.showToast(mContext, "转发成功");
                        setResult(1);
                        AppManager.getAppManager().finishActivity();
                        break;
                    case 0:
                        MyToast.showToast(mContext, "转发失败");
                        break;
                    case 400:
                        MyToast.showToast(mContext, "没登录");
                        break;
                    case 401:
                        //不是post
                        break;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forward_bbs;
    }
}
