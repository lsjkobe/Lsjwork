package com.lsj.lsjnews.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.mainHelper;
import com.lsj.lsjnews.fragment.FragmentFactory;
import com.lsj.lsjnews.fragment.SocialNewsFragment;
import com.lsj.lsjnews.fragment.SportNewsFragment;

import java.util.ArrayList;

public class NewsMainActivity extends MyBaseActivity{

    private TabLayout mTabTopMenu;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentList;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DrawerLayout mLayDrawer;
    private int mPosition = 0;
    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mToolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.lay_main_top_collapsing);
        mLayDrawer = (DrawerLayout) findViewById(R.id.lay_main_drawer);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_news_msg);
        mTabTopMenu = (TabLayout) findViewById(R.id.tabs_news_top_menu);
    }
    private SocialNewsFragment mLsjMainActivity;
    private SportNewsFragment mSportNewsFragment;
    @Override
    protected void initData() {
        fragmentList = new ArrayList<Fragment>();
        mLsjMainActivity = new SocialNewsFragment();
        mSportNewsFragment = new SportNewsFragment();
        fragmentList.add(mLsjMainActivity);
        fragmentList.add(mSportNewsFragment);
        mViewPager.setOffscreenPageLimit(MyHelper.News_Type_Count);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new mOnPageChangeListener());
        mViewPager.setCurrentItem(0);

        initTab();
        initToolbar();
    }

    private void initTab(){
        mTabTopMenu.setupWithViewPager(mViewPager);
        mTabTopMenu.setScrollPosition(0, 0, true);
        mainHelper.dynamicSetTablayoutMode(mTabTopMenu);
    }
    private void initToolbar() {
        mCollapsingToolbarLayout.setTitle(MyHelper.News_Name_List[0]);
        //收缩时的title颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00000000"));

        setSupportActionBar(mToolbar);
//        getSupportActionBar().setLogo(R.mipmap.ic_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mLayDrawer, mToolbar, R.string.app_name,
                R.string.app_name);
        mLayDrawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    public class MainPagerAdapter extends FragmentPagerAdapter {
        public MainPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
//            return list.get(position);
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return MyHelper.News_Type_Count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyHelper.News_Name_List[position];
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
    }
    private class mOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            if(!FragmentFactory.createFragment(position).isLoadSuccess){
                FragmentFactory.createFragment(position).baseLoadData();
            }
            mCollapsingToolbarLayout.setTitle(MyHelper.News_Name_List[position]);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
