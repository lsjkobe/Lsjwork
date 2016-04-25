package com.lsj.lsjnews.mdnews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.EmojiAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnEmojiOnclick;
import com.lsj.lsjnews.utils.EmojiParser;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/11.
 */
public class UserBBSComment extends MyBaseActivity implements View.OnClickListener{

    private boolean is_emoji_open = false;
    private Toolbar mToolBar;
    private EditText mEditComment;
    private ImageView mImgEmoji,mImgPhoto,mImgComment;
    private RecyclerView mRecycleEmoji;
    private EmojiAdapter mEmojiAdapter;
    private ImageView mImgCommentPhoto;
    private CardView mCardPhoto;
    private int mid;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        mid = getIntent().getIntExtra("mid",-1);
        if(mid == -1){
            MyToast.showToast(mContext,"圈子不存在");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mToolBar = (Toolbar) findViewById(R.id.toolbar_comment_bbs_main);
        mEditComment = (EditText) findViewById(R.id.edit_comment_bbs_content);
        mImgEmoji = (ImageView) findViewById(R.id.img_comment_bbs_emoji);
        mImgPhoto = (ImageView) findViewById(R.id.img_comment_bbs_select);
        mImgComment = (ImageView) findViewById(R.id.img_comment_bbs_ok);
        mRecycleEmoji = (RecyclerView) findViewById(R.id.recycler_comment_emoji_select);
        mImgCommentPhoto = (ImageView) findViewById(R.id.img_comment_content_photo);
        mImgEmoji.setOnClickListener(this);
        mImgPhoto.setOnClickListener(this);
        mImgComment.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initToolbar();
        initRecycler();
        mEditComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                int currentPosition = mEditComment.getSelectionStart();
                mEditComment.getText().insert(currentPosition,EmojiParser.getInstance(mContext).edittextReplace(position,mEditComment.getTextSize()));
            }
        });
        mRecycleEmoji.setAdapter(mEmojiAdapter);
    }

    private void initToolbar() {
        mToolBar.setNavigationIcon(R.mipmap.ic_back);
        mToolBar.setTitle("评论");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_comment_bbs_select:
                UiHelper.showSelectPhoto(mContext,1);
                break;
            case R.id.img_comment_bbs_emoji:
                if(is_emoji_open){
                    is_emoji_open = false;
                    mRecycleEmoji.setVisibility(View.GONE);
                }else{
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditComment.clearFocus();
                    is_emoji_open = true;
                    mRecycleEmoji.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.img_comment_bbs_ok:
                if(mEditComment.getText() != null && mEditComment.getText().toString().length() != 0){
                    commentCommit();
                }else{
                    MyToast.showToast(mContext,"内容不能为空");
                }
                break;
        }
    }

    private void commentCommit() {
        RequestParams params = new RequestParams(Conts.POST_BBS_COMMENTS);
        params.addBodyParameter("mid",String.valueOf(mid));
        params.addBodyParameter("content", mEditComment.getText().toString());
        if (mImgLists != null && mImgLists.size() != 0) {
            File imgFile = new File(mImgLists.get(0));
            params.addParameter("imgSrc",imgFile);
        } else {
            params.addParameter("imgList", null);
        }
        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyToast.showToast(mContext,s);
            }
        });
    }

    ArrayList<String> mImgLists = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            mImgLists = data.getStringArrayListExtra("imgList");
            if (mImgLists != null) {
                mImgCommentPhoto.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mImgLists.get(0)).into(mImgCommentPhoto);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_bbs;
    }
}
