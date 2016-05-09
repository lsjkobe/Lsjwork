package com.lsj.lsjnews.mdnews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.userBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.mdnews.fragment.OtherUserBBS;
import com.lsj.lsjnews.mdnews.fragment.OtherUserPhoto;
import com.lsj.lsjnews.utils.CircleImageView;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/12.
 */
public class OtherUserMain extends MyBaseActivity implements View.OnClickListener{
    private CircleImageView mImgOtherUserHead;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
//    private TabLayout mTabDefoult,mTabTop;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TabLayout mTabTop;
    public static ViewPager mViewPager;
    private TextView mTxtName,mTxtReleasCount,mTxtFansCount,mTxtFollowCount,mTxtStateContent;
    private otherUserViewPagerAdapter mViewPagerAdapter;
    private List<BaseFragment> datas;
    private ImageView mImgSexy;
    private Button mBtnRelation;
    private userBean user;
    private int uid;

    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        uid = getIntent().getIntExtra("uid",-1);
    }

    @Override
    protected void initView() {
        super.initView();
        mImgOtherUserHead = (CircleImageView) findViewById(R.id.img_other_user_main_head);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.lay_other_user_main_appbarLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_other_user_mian);
//        mTabDefoult = (TabLayout) findViewById(R.id.tablayout_other_user_main);
        mTabTop = (TabLayout) findViewById(R.id.tablayout_other_user_main_top);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_other_user_main);
        mTxtName = (TextView) findViewById(R.id.txt_other_user_main_name);
        mTxtReleasCount = (TextView) findViewById(R.id.txt_other_user_main_releas_count);
        mTxtFansCount = (TextView) findViewById(R.id.txt_other_user_main_fans_count);
        mTxtFollowCount = (TextView) findViewById(R.id.txt_other_user_main_follow_count);
        mTxtStateContent = (TextView) findViewById(R.id.txt_other_user_main_state_content);
        mImgSexy = (ImageView) findViewById(R.id.img_other_user_mian_sexy);
        mBtnRelation = (Button) findViewById(R.id.btn_other_user_relation);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.layout_other_user_collapsing);

        mBtnRelation.setOnClickListener(this);
    }

    private boolean is_show_top_tab = false;
    @Override
    protected void initData() {
        getUserMsg();
        initToolbar();
        initOtherUserFragment();

//        mImgOtherUserHead.setVisibility(View.GONE);
//        AlphaAnimation mAlphaAnim = new AlphaAnimation(1f,0f);
//        mAlphaAnim.setDuration(5000);
//        如果fillAfter的值为true,则动画执行后,控件将停留在执行结束的状态
//        mSAnim.setFillAfter(true);
//        mImgOtherUserHead.setAnimation(mAlphaAnim);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                MyLogger.showLogWithLineNum(3,"------------:"+verticalOffset+":"+mAppBarLayout.getHeight()+":"+mToolbar.getHeight());

                if( verticalOffset == mToolbar.getHeight() - mAppBarLayout.getHeight() ){
                    //刚好把整个appbarlayout滑出就显示topTablayout设置is_show_top_tab = true
                    is_show_top_tab = true;
                    mTabTop.setVisibility(View.VISIBLE);
                }else if(is_show_top_tab){
                    //把整个appbarlayout滑出屏幕后向下滑的一瞬间就隐藏topTablayout设置is_show_top_tab = false
                    is_show_top_tab = false;
                    mTabTop.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        //向下扩张时隐藏title
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00000000"));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
    OtherUserBBS mFragmentBBS;
    OtherUserPhoto mFragmentPhoto;
    private void initOtherUserFragment() {
        datas = new ArrayList<>();
        mFragmentBBS = new OtherUserBBS();
        mFragmentBBS.setUid(uid);
        datas.add(mFragmentBBS);
        mFragmentPhoto = new OtherUserPhoto();
        mFragmentPhoto.setUid(uid);
        datas.add(mFragmentPhoto);
        mViewPagerAdapter = new otherUserViewPagerAdapter(getSupportFragmentManager(),datas);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        initTabLayout();
    }

    private void initTabLayout() {
        mTabTop.setupWithViewPager(mViewPager);
        mTabTop.setScrollPosition(0,0,true);
        mTabTop.setTabMode(TabLayout.MODE_FIXED);
        mTabTop.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void getUserMsg(){
        RequestParams params = new RequestParams(Conts.GET_OTHER_USER_MSG);
        params.addBodyParameter("uid", String.valueOf(uid));
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3,s);
                user = JSON.parseObject(s,userBean.class);
                if(user.getResultCode() == 0){
                    MyToast.showToast(mContext,"用户不存在");
                }else{
                    mTxtName.setText(user.getuName());
                    mTxtReleasCount.setText(""+user.getuReleasCount());
                    mTxtFansCount.setText(""+user.getuFansCount());
                    mTxtFollowCount.setText(""+user.getuFollowCount());
                    mTxtStateContent.setText(user.getuStateContent());
                    mCollapsingToolbarLayout.setTitle(user.getuName());
                    Glide.with(mContext).load(user.getuImg()).into(mImgOtherUserHead);
                    if(user.getuSexy() == 0){
                        mImgSexy.setBackgroundResource(R.mipmap.ic_sexy_boy);
                    }else{
                        mImgSexy.setBackgroundResource(R.mipmap.ic_sexy_girl);
                    }
                    switch (user.getKey()){
                        case -1:
                            mBtnRelation.setVisibility(View.GONE);
                            break;
                        case 0:
                            mBtnRelation.setText("关注");
                            break;
                        case 1:
                            mBtnRelation.setText("已关注");
                            break;
                        case 2:
                            mBtnRelation.setText("相互关注");
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_other_user_relation:
                if(user.getKey() == 1 || user.getKey() == 2){
                    showDialog();
                }else{
                    followOrCancel(1);
                }

                break;
        }
    }

    private class otherUserViewPagerSelect implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            mViewPager.getLayoutParams().height = 1000;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class otherUserViewPagerAdapter extends FragmentPagerAdapter{
        List<BaseFragment> datas;
        public otherUserViewPagerAdapter(FragmentManager fm, List<BaseFragment> datas) {
            super(fm);
            this.datas = datas;
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }
        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyHelper.OTHER_USER_MAIN_LIST[position];
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
    }

    //取消关注对话框
    public void showDialog() {

        final Dialog dialog = new Dialog(mContext,R.style.Theme_MyDialog);
        dialog.setContentView(R.layout.dialog_follow_or_cancle);
        Button mButConfirm = (Button) dialog
                .findViewById(R.id.btn_cancel_relation_confirm);
        Button mButCancel = (Button) dialog
                .findViewById(R.id.btn_cancel_relation_cancel);
        mButConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                followOrCancel(0);
                dialog.dismiss();
            }
        });
        mButCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     *
     * @param kind 1关注 0取消关注
     */
    private void followOrCancel(final int kind){
        RequestParams params = new RequestParams(Conts.GET_FOLLOW_OR_CANCEL);
        params.addBodyParameter("uid",String.valueOf(user.getUid()));
        params.addBodyParameter("key",String.valueOf(user.getKey()));
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {

                baseBean bean = JSON.parseObject(s,baseBean.class);
                switch (bean.getResultCode()){
                    case 1:
                        user.setKey(1);
                        mBtnRelation.setText("已关注");
                        break;
                    case 0:
                        mBtnRelation.setText("关注");
                        user.setKey(0);
                        break;
                    case 2:
                        mBtnRelation.setText("相互关注");
                        user.setKey(2);
                        break;
                    case -2:
                        MyToast.showToast(mContext,"重试");
                        break;
                }
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_user_main;
    }
}
