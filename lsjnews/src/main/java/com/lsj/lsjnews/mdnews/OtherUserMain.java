package com.lsj.lsjnews.mdnews;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.userBean;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.mdnews.fragment.OtherUserBBS;
import com.lsj.lsjnews.utils.CircleImageView;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/12.
 */
public class OtherUserMain extends MyBaseActivity{
    private CircleImageView mImgOtherUserHead;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabDefoult,mTabTop;
    private ViewPager mViewPager;
    private TextView mTxtName,mTxtReleasCount,mTxtFansCount,mTxtFollowCount,mTxtStateContent;
    private otherUserViewPagerAdapter mViewPagerAdapter;
    private List<BaseFragment> datas;
    private ImageView mImgSexy;
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
        mTabDefoult = (TabLayout) findViewById(R.id.tablayout_other_user_main);
        mTabTop = (TabLayout) findViewById(R.id.tablayout_other_user_main_top);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_other_user_main);
        mTxtName = (TextView) findViewById(R.id.txt_other_user_main_name);
        mTxtReleasCount = (TextView) findViewById(R.id.txt_other_user_main_releas_count);
        mTxtFansCount = (TextView) findViewById(R.id.txt_other_user_main_fans_count);
        mTxtFollowCount = (TextView) findViewById(R.id.txt_other_user_main_follow_count);
        mTxtStateContent = (TextView) findViewById(R.id.txt_other_user_main_state_content);
        mImgSexy = (ImageView) findViewById(R.id.img_other_user_mian_sexy);
    }

    private boolean is_show_top_tab = false;
    @Override
    protected void initData() {
        getUserMsg();
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
//                MyLogger.showLogWithLineNum(3,"------------:"+verticalOffset+":"+mAppBarLayout.getHeight()+":"+mToolbar.getHeight());

                if( verticalOffset == mToolbar.getHeight() - mAppBarLayout.getHeight() ){
                    //刚好把整个appbarlayout滑出就显示topTablayout设置is_show_top_tab = true
                    is_show_top_tab = true;
                    mTabTop.setVisibility(View.VISIBLE);
                    mFragmentBBS1.setRecyclerViewScroll(true);
                    mFragmentBBS2.setRecyclerViewScroll(true);
                }else if(is_show_top_tab){
                    //把整个appbarlayout滑出屏幕后向下滑的一瞬间就隐藏topTablayout设置is_show_top_tab = false
                    is_show_top_tab = false;
                    mTabTop.setVisibility(View.GONE);
                    mFragmentBBS1.setRecyclerViewScroll(false);
                    mFragmentBBS2.setRecyclerViewScroll(false);
                }
            }
        });

    }
    OtherUserBBS mFragmentBBS1,mFragmentBBS2;
    private void initOtherUserFragment() {
        datas = new ArrayList<>();
        mFragmentBBS1 = new OtherUserBBS();
        mFragmentBBS1.setUid(uid);
        datas.add(mFragmentBBS1);
        mFragmentBBS2 = new OtherUserBBS();
        datas.add(mFragmentBBS2);
        mViewPagerAdapter = new otherUserViewPagerAdapter(getSupportFragmentManager(),datas);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        initTabLayout();
    }

    private void initTabLayout() {
        mTabDefoult.setupWithViewPager(mViewPager);
        mTabDefoult.setScrollPosition(0,0,true);
        mTabDefoult.setTabMode(TabLayout.MODE_FIXED);
        mTabDefoult.setTabGravity(TabLayout.GRAVITY_FILL);

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
                userBean user = JSON.parseObject(s,userBean.class);
                if(user.getResultCode() == 0){
                    MyToast.showToast(mContext,"用户不存在");
                }else{
                    mTxtName.setText(user.getuName());
                    mTxtReleasCount.setText(""+user.getuReleasCount());
                    mTxtFansCount.setText(""+user.getuFansCount());
                    mTxtFollowCount.setText(""+user.getuFollowCount());
                    mTxtStateContent.setText(user.getuStateContent());
                    Glide.with(mContext).load(user.getuImg()).into(mImgOtherUserHead);
                    if(user.getuSexy() == 0){
                        mImgSexy.setBackgroundResource(R.mipmap.ic_sexy_boy);
                    }else{
                        mImgSexy.setBackgroundResource(R.mipmap.ic_sexy_girl);
                    }
                }
            }
        });
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
            return "测试";
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_user_main;
    }
}
