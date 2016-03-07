package com.lsj.lsjnews.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.mainHelper;
import com.lsj.lsjnews.fragment.FragmentFactory;
import com.lsj.lsjnews.fragment.NetNewsFragment;


public class NewsMainActivity extends MyBaseActivity{

    private TabLayout mTabTopMenu;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
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
    MainPagerAdapter mAdapter = new MainPagerAdapter(getSupportFragmentManager());
    @Override
    protected void initData() {
        mViewPager.setOffscreenPageLimit(MyHelper.News_Type_Count);
//        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new mOnPageChangeListener());
//        mViewPager.setCurrentItem(0);
        initTab();
        initToolbar();
    }

    private void initTab(){
        mTabTopMenu.setupWithViewPager(mViewPager);
        mTabTopMenu.setScrollPosition(0, 0, true);
        mainHelper.dynamicSetTablayoutMode(mTabTopMenu);
    }
    private int count = 0;
    private long firClick = 0,secClick = 0;
    private void initToolbar() {
        //双击回到顶部
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
                            ((NetNewsFragment)FragmentFactory.getFragment(mPosition)).ToTop();
                        }
                        count = 0;
                        firClick = 0;
                        secClick = 0;

                    }
                }
                return true;
            }
        });
        mCollapsingToolbarLayout.setTitle(MyHelper.News_Name_List[0]);
        //收缩时的title颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#003300"));
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00000000"));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
//            return list.get(position);
            return FragmentFactory.createFragment(position);
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            return FragmentFactory.createFragment(position);
//        }

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
            mPosition = position;
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
