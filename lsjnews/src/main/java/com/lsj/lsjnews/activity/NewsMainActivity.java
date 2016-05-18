package com.lsj.lsjnews.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
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
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.CacheUtils;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.common.mainHelper;
import com.lsj.lsjnews.fragment.NetNewsFragment;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.mdnews.UserAutoLogin;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NewsMainActivity extends MyBaseActivity{

    private TabLayout mTabTopMenu;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DrawerLayout mLayDrawer;
    private int mPosition = 0;
    private List<NetNewsFragment> datas = new ArrayList<>();

//    private RecycleMenuAdapter menuAdapter;
//    private RecyclerView mMenuRecycler;

    private AppBarLayout mAppBarLayout;

    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        if(CacheUtils.getLoginCache(mContext,"phone") != null && CacheUtils.getLoginCache(mContext,"phone")!=null){
            UserAutoLogin.getInstance().autoLogin(mContext);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mToolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.lay_main_top_collapsing);
        mLayDrawer = (DrawerLayout) findViewById(R.id.lay_main_drawer);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_news_msg);
        mTabTopMenu = (TabLayout) findViewById(R.id.tabs_news_top_menu);
//        mMenuRecycler = (RecyclerView) findViewById(R.id.view_navigation_recycler_menu);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.lay_main_top_bar);


        //supportv7-23.2.1 bug,解决SwipeRefreshLayout与AppbarLayout冲突（supportv7-23.1.1可以，但recycleview设置高度wrap_content没效果）
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0){
                    datas.get(mPosition).getSwipeRefreshLayout().setEnabled(true);
                }else {
                    datas.get(mPosition).getSwipeRefreshLayout().setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void initData() {
        initFragment();
        MainPagerAdapter mAdapter = new MainPagerAdapter(getSupportFragmentManager(), datas);
        mViewPager.setOffscreenPageLimit(MyHelper.News_Type_Count);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new mOnPageChangeListener());
//        mViewPager.setCurrentItem(0);
        initTab();
        initToolbar();
//        initRecycler();
    }

//    private void initRecycler() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mMenuRecycler.setLayoutManager(linearLayoutManager);
//        menuAdapter = new RecycleMenuAdapter(mContext);
//        mMenuRecycler.setAdapter(menuAdapter);
//    }

    //初始化新闻Fragment 0焦点 1体育 2社会 3nba 4足球
    private void initFragment(){
        NetNewsFragment mFragment = NetNewsFragment.newInstance(0);
        datas.add(mFragment);
        NetNewsFragment mFragment1 = NetNewsFragment.newInstance(1);
        datas.add(mFragment1);
        NetNewsFragment mFragment2 = NetNewsFragment.newInstance(2);
        datas.add(mFragment2);
        NetNewsFragment mFragment3 = NetNewsFragment.newInstance(3);
        datas.add(mFragment3);
        NetNewsFragment mFragment4 = NetNewsFragment.newInstance(4);
        datas.add(mFragment4);
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
                            datas.get(mPosition).ToTop();
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

        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mLayDrawer, mToolbar, R.string.app_name,
//                R.string.app_name){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//                //刚划出来时启动动画效果
//                if(slideOffset == 0 && mLayDrawer.isShown()){
//                    menuAdapter = new RecycleMenuAdapter(mContext);
//                    mMenuRecycler.setAdapter(menuAdapter);
//                }
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                menuAdapter = null;
//
//            }
//        };
//        mLayDrawer.setDrawerListener(toggle);
//        toggle.syncState();
    }
    public class MainPagerAdapter extends FragmentPagerAdapter {
        List<NetNewsFragment> datas;
        public MainPagerAdapter(FragmentManager fragmentManager,List<NetNewsFragment> datas) {
            super(fragmentManager);
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
            if(datas.get(position).isLoadSuccess != true){
//                datas.get(position).getSwipeRefreshLayout().setRefreshing(true);
                datas.get(position).getViewLoading().setVisibility(View.VISIBLE);
                datas.get(position).getViewLoading().startLoadingAnim();
                datas.get(position).baseLoadData();
            }
            mPosition = position;
            mCollapsingToolbarLayout.setTitle(MyHelper.News_Name_List[position]);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

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

    private void showPopUpWindow(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_mdnews_main_top_right_menu, null);
        CardView mCardItem1 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item1);
        CardView mCardItem2 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item2);
        CardView mCardItem3 = (CardView) contentView.findViewById(R.id.card_mdnews_top_right_menu_item3);
        mCardItem3.setCardBackgroundColor(0xffeeeeee);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popWnd.showAsDropDown(mToolbar,0,0,Gravity.RIGHT);
        }
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
                    UiHelper.showUserBBS(mContext);
                    popWnd.dismiss();
                    AppManager.getAppManager().finishActivity();
                    break;
                case R.id.card_mdnews_top_right_menu_item3:
                    popWnd.dismiss();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        }.start();
        Glide.get(mContext).clearMemory();
    }

    private void isLogin(){
        RequestParams params = new RequestParams(Conts.isLogin);
        x.http().get(params, new NewCommonCallBack(){

            @Override
            public void onSuccess(String s) {
                if(s.equals("1")){
//                    MyToast.showToast(mContext,"已经登录了");
                    UiHelper.showUserMain(mContext);
                }else{
                    MyHelper.USER_HEAD_IMG = "";
                    UiHelper.showUserLogin(mContext,0);
                }
            }
        });
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
        return R.layout.activity_main;
    }

}
