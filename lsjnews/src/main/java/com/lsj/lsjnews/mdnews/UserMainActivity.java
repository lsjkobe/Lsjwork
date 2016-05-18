package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.CacheUtils;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by lsj on 2016/3/25.
 */
public class UserMainActivity extends MyBaseActivity implements View.OnClickListener{
    private CardView mCardLogout;
    private TextView mTxtUserName,mTxtUserState,mTxtUserFansCount,mTxtUserBBSCount,mTxtUserForwardCount;
    private ImageView mImgUser;
    private LinearLayout mLayoutBBS,mLayoutFans,mLayoutRelation,mLayoutPhoto,mLayoutCollect;
    @Override
    protected void initView() {
        super.initView();
        mCardLogout = (CardView) findViewById(R.id.card_user_logout);
        mTxtUserName = (TextView) findViewById(R.id.txt_user_main_name);
        mTxtUserState = (TextView) findViewById(R.id.txt_user_main_state);
        mTxtUserFansCount = (TextView) findViewById(R.id.txt_user_main_fans_count);
        mTxtUserBBSCount = (TextView) findViewById(R.id.txt_user_main_bbs_count);
        mTxtUserForwardCount = (TextView) findViewById(R.id.txt_user_main_forward_count);
        mImgUser = (ImageView) findViewById(R.id.img_user_main_head);
        mLayoutBBS = (LinearLayout) findViewById(R.id.layout_user_main_bbs);
        mLayoutFans = (LinearLayout) findViewById(R.id.layout_user_main_fans);
        mLayoutRelation = (LinearLayout) findViewById(R.id.layout_user_main_relation);
        mLayoutPhoto = (LinearLayout) findViewById(R.id.layout_user_main_photo);
        mLayoutCollect = (LinearLayout) findViewById(R.id.layout_user_main_collect);
        mCardLogout.setOnClickListener(this);
        mLayoutBBS.setOnClickListener(this);
        mLayoutFans.setOnClickListener(this);
        mLayoutRelation.setOnClickListener(this);
        mLayoutPhoto.setOnClickListener(this);
        mLayoutCollect.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Glide.with(mContext).load(MyHelper.USER_HEAD_IMG).into(mImgUser);
        mTxtUserName.setText(MyHelper.USER_NAME+"");
        mTxtUserState.setText(MyHelper.USER_STATE_CONTENT+"");
        mTxtUserFansCount.setText(MyHelper.USER_FANS_COUNT+"");
        mTxtUserBBSCount.setText(MyHelper.USER_RELEAS_COUNT+"");
        mTxtUserForwardCount.setText(MyHelper.USER_FROWARD_COUNT+"");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_user_logout:
                userLogout();
                break;
            case R.id.layout_user_main_bbs:
                UiHelper.showOtherUserMain(mContext,MyHelper.USER_ID);
                break;
            case R.id.layout_user_main_fans:
                UiHelper.showUserFans(mContext,"0");
                break;
            case R.id.layout_user_main_relation:
                UiHelper.showUserFans(mContext,"1");
                break;
            case R.id.layout_user_main_photo:
                UiHelper.showUserPhoto(mContext);
                break;
            case R.id.layout_user_main_collect:
                UiHelper.showUserCollect(mContext);
                break;
        }
    }

    private void userLogout() {
        RequestParams logoutParams = new RequestParams(Conts.POST_USER_LOGOUT);
        x.http().post(logoutParams, new NewCallBack() {
            @Override
            public void onSuccess(String s) {
                if(s.equals("1")){
                    MyToast.showToast(mContext,"注销成功");
                    MyHelper.USER_ID = -1;
                    MyHelper.USER_HEAD_IMG = "";
                    MyHelper.USER_NAME = "";
                    MyHelper.USER_SEXY = 0;
                    MyHelper.USER_STATE_CONTENT = "";
                    MyHelper.USER_FANS_COUNT = 0;
                    MyHelper.USER_RELEAS_COUNT = 0;
                    MyHelper.USER_FROWARD_COUNT = 0;
                    CacheUtils.setCacheClear(mContext);
                    UiHelper.showUserLogin(mContext,1);
                    AppManager.getAppManager().finishActivity();
                }
            }
        });
    }
}
