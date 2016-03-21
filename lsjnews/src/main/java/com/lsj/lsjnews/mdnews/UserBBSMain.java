package com.lsj.lsjnews.mdnews;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.UserMsgAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserBBSMain extends MyBaseActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyleMsg;
    private UserMsgAdapter mMsgAdapter;
    private List<bbsBean.Lists> bbsBeanList = new ArrayList<>();
    private TextView mTxtToolbarName;
    private FloatingActionButton mFabWrite;
    @Override
    protected void initView() {
        super.initView();
        mToolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        mRecyleMsg = (RecyclerView) findViewById(R.id.recyle_user_bbs);
        mTxtToolbarName = (TextView) findViewById(R.id.txt_toolbar_center_username);
        mFabWrite = (FloatingActionButton) findViewById(R.id.fab_user_bbs_write);
    }

    @Override
    protected void initData() {
        mTxtToolbarName.setText(MyHelper.USER_NAME);
        initRecyleView();
        initToolbar();
        getBBSData();
        mFabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showUserWrite(mContext);
            }
        });
    }


    float downX,downY;
    @TargetApi(Build.VERSION_CODES.M)
    private void initRecyleView(){

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyleMsg.setLayoutManager(mLinearLayoutManager);
        //向上滑动隐藏fab向下滑动显示发布

        mRecyleMsg.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    float curTranslationX = mFabWrite.getTranslationX();
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mFabWrite, "translationX", curTranslationX, 200f);
                    animator.setDuration(300);
                    animator.start();
                }else if(dy < 0){
                    float curTranslationX = mFabWrite.getTranslationX();
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mFabWrite, "translationX", curTranslationX, 0);
                    animator.setDuration(300);
                    animator.start();
                }
            }
        });
    }

    private int count = 0;
    private long firClick = 0,secClick = 0;
    private void initToolbar(){
        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    count++;
                    if(count == 1){
                        firClick = System.currentTimeMillis();

                    } else if (count == 2){
                        secClick = System.currentTimeMillis();
                        if(secClick - firClick < 1000){
                            //双击事件
                            mRecyleMsg.smoothScrollToPosition(0);
                        }
                        count = 0;
                        firClick = 0;
                        secClick = 0;

                    }
                }
                return true;
            }
        });
        setSupportActionBar(mToolbar);
    }
    public void getBBSData() {
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/getBBSDate.php");
        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {

                bbsBean mbbsBean = JSON.parseObject(s, bbsBean.class);
//                        DbCookieStore instance = DbCookieStore.INSTANCE;
//                        List cookies = instance.getCookies();
                switch(mbbsBean.getResultCode()){
                    case 400:
                        MyToast.showToast(mContext,"没登录");
                        break;
                    case 1:
                        if(mbbsBean.getLists() != null && mbbsBean.getLists().size() != 0){
                            bbsBeanList.addAll(mbbsBean.getLists());
                            initOrRefresh();
                        }
                        MyLogger.showLogWithLineNum(3,"--------------userBBS:"+s);
                        break;
                    case 0:
                        MyToast.showToast(mContext,"没数据");
                        break;
                }
            }

        });
    }

    private void initOrRefresh(){

        if(mMsgAdapter == null){
            mMsgAdapter = new UserMsgAdapter(mContext, bbsBeanList);
            mRecyleMsg.setAdapter(mMsgAdapter);
        }else{
            mMsgAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_people){
            showPopUpWindow();
        }

        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showPopUpWindow(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_mdnews_main_top_right_menu, null);
        CardView mCardItem1 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item1);
        CardView mCardItem2 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item2);
        CardView mCardItem3 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item3);
        ImageView mImgUser = (ImageView) contentView.findViewById(R.id.img_right_menu_user_img);
        Glide.with(mContext).load(MyHelper.USER_HEAD_IMG).into(mImgUser);
        PopupWindow popWnd = new PopupWindow (contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCardItem1.setOnClickListener(new myOnclickListener(popWnd));
        mCardItem2.setOnClickListener(new myOnclickListener(popWnd));
        mCardItem3.setOnClickListener(new myOnclickListener(popWnd));
        popWnd.setAnimationStyle(R.style.popwin_anim_style);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable());
        popWnd.showAsDropDown(mToolbar,0,0, Gravity.RIGHT);
    }
    private class myOnclickListener implements View.OnClickListener {
        PopupWindow popWnd;
        public myOnclickListener(PopupWindow popWnd){
            this.popWnd = popWnd;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_mdnews_top_right_menu_item1:
                    isLogin();
                    popWnd.dismiss();
                    break;
                case R.id.card_mdnews_top_right_menu_item2:
                    popWnd.dismiss();
                    break;
                case R.id.card_mdnews_top_right_menu_item3:
                    UiHelper.showUserNews(mContext);
                    popWnd.dismiss();
                    AppManager.getAppManager().finishActivity();
                    break;
            }
        }
    }
    private void isLogin(){
        RequestParams params = new RequestParams(Conts.isLogin);
        x.http().get(params, new NewCommonCallBack(){

            @Override
            public void onSuccess(String s) {
                if(s.equals("1")){
                    MyToast.showToast(mContext,"已经登录了");
                }else{
                    MyHelper.USER_HEAD_IMG = "";
                    UiHelper.showUserLogin(mContext,1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                mTxtToolbarName.setText(MyHelper.USER_NAME);
                getBBSData();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按返回键退出但不销毁程序
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs;
    }
}
