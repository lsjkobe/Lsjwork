package com.lsj.lsjnews.mdnews;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.utils.CircleImageView;

/**
 * Created by Administrator on 2016/4/12.
 */
public class OtherUserMain extends MyBaseActivity{
    private CircleImageView mImgOtherUserHead;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabDefoult,mTabTop;
    private ViewPager mViewPager;
    @Override
    protected void initView() {
        super.initView();
        mImgOtherUserHead = (CircleImageView) findViewById(R.id.img_other_user_main_head);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.lay_other_user_main_appbarLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_other_user_mian);
        mTabDefoult = (TabLayout) findViewById(R.id.tablayout_other_user_main);
        mTabTop = (TabLayout) findViewById(R.id.tablayout_other_user_main_top);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_other_user_main);
    }

    private boolean is_show_top_tab = false;
    @Override
    protected void initData() {
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
                }else if(is_show_top_tab){
                    //把整个appbarlayout滑出屏幕后向下滑的一瞬间就隐藏topTablayout设置is_show_top_tab = false
                    is_show_top_tab = false;
                    mTabTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private class otherUserViewPagerAdapter extends FragmentPagerAdapter{

        public otherUserViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
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
