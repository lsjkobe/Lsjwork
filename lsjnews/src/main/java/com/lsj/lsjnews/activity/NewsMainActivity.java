package com.lsj.lsjnews.activity;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.TopMenuRecyclerAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.fragment.FragmentFactory;
import com.lsj.lsjnews.fragment.NetNewsFragment;
import com.lsj.lsjnews.fragment.SocialNewsFragment;
import com.lsj.lsjnews.fragment.SportNewsFragment;

import java.util.ArrayList;

public class NewsMainActivity extends MyBaseActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyTopMenu;
    private TopMenuRecyclerAdapter mTopMenuAdapter;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentList;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DrawerLayout mLayDrawer;
    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mRecyTopMenu = (RecyclerView) findViewById(R.id.recy_view_top_menu);
        mToolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.lay_main_top_collapsing);
        mLayDrawer = (DrawerLayout) findViewById(R.id.lay_main_drawer);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_news_msg);

    }
    private SocialNewsFragment mLsjMainActivity;
    private SportNewsFragment mSportNewsFragment;
    private NetNewsFragment mNetNewsFragment;
    private NetNewsFragment mNetNewsFragmen1;
    @Override
    protected void initData() {
        fragmentList = new ArrayList<Fragment>();
        mLsjMainActivity = new SocialNewsFragment();
        mSportNewsFragment = new SportNewsFragment();
        mNetNewsFragment = NetNewsFragment.newInstance(0);
        mNetNewsFragmen1 = NetNewsFragment.newInstance(1);
        fragmentList.add(mLsjMainActivity);
        fragmentList.add(mSportNewsFragment);
        fragmentList.add(mNetNewsFragment);
        fragmentList.add(mNetNewsFragmen1);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(0);
        initToolbar();
        initRecycleDate();
    }

    private void initToolbar() {
        mCollapsingToolbarLayout.setTitle("科技热点");
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

    private void initRecycleDate(){
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 1);
        mGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyTopMenu.setLayoutManager(mGridLayoutManager);
        mTopMenuAdapter = new TopMenuRecyclerAdapter(mContext);
        mRecyTopMenu.setAdapter(mTopMenuAdapter);
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;
        public MainPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> list) {
            super(fragmentManager);
            this.list = list;
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

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
