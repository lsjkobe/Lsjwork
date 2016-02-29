package com.lsj.lsjnews;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.widget.RecyclerItemClickListener;
import com.lsj.lsjnews.adapter.MyRecyclerViewAdapter;
import com.lsj.lsjnews.adapter.TopMenuRecyclerAdapter;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.bean.ApiNewsMsg;
import com.lsj.lsjnews.bean.JuheNewsApi;
import com.lsj.lsjnews.common.Conts;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.UiHelper;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyTopMenu;
    private List<ApiNewsMsg.NewsBean> NewsList = new ArrayList<>();
    private MyRecyclerViewAdapter mAdapter;
    private TopMenuRecyclerAdapter mTopMenuAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int page = 1;

    private int count = 0;
    private long firClick = 0;
    private long secClick = 0;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DrawerLayout mLayDrawer;
    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_main_recyclerView);
        mRecyTopMenu = (RecyclerView) findViewById(R.id.recy_view_top_menu);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_news_list);
        mToolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.lay_main_top_collapsing);
        mLayDrawer = (DrawerLayout) findViewById(R.id.lay_main_drawer);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    count++;
                    if (count == 1) {
                        firClick = System.currentTimeMillis();

                    } else if (count == 2) {
                        secClick = System.currentTimeMillis();
                        if (secClick - firClick < 1000) {
                            //双击事件
                            mRecyclerView.smoothScrollToPosition(0);
                        }
                        count = 0;
                        firClick = 0;
                        secClick = 0;

                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {

        initToolbar();
        initRecycleDate();
        baseLoadData();
        getJuheNews();
    }

    private void initToolbar() {
        mCollapsingToolbarLayout.setTitle("科技热点");
        //收缩时的title颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.bule));
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
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, onItemClickListener));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(page%5 == 0){
                    Snackbar.make(mRecyclerView,"双击导航栏回到顶部"+page,Snackbar.LENGTH_LONG).show();
                }

            }
        });
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 1);
        mGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyTopMenu.setLayoutManager(mGridLayoutManager);
        mTopMenuAdapter = new TopMenuRecyclerAdapter(mContext);
        mRecyTopMenu.setAdapter(mTopMenuAdapter);
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            UiHelper.showNewsInfoWeb(mContext, NewsList.get(position).getUrl());
        }
    };
    boolean first = true;
    @Override
    public void baseLoadData() {
        super.baseLoadData();
        RequestParams params = new RequestParams(Conts.API_HTTPS_NEWS);
        params.addBodyParameter("num", "10");
        params.addBodyParameter("page", String.valueOf(page));
        params.addHeader("apikey", MyHelper.NEWS_API_KEYS);

        baseManager.http2Get(params, ApiNewsMsg.class, new LsjBaseCallBack() {
            @Override
            public void onSucces(Object result) {
                mSwipeRefreshLayout.setRefreshing(false);
                ApiNewsMsg newsMsg = (ApiNewsMsg) result;
                if(newsMsg.getCode() == 200){
                    if(page == 1){
                        NewsList.clear();
                        NewsList.addAll(newsMsg.getNewslist());
                    }else{
                        NewsList.addAll(newsMsg.getNewslist());
                    }
                    initOrRefresh();

                }else{
                    MyToast.showToast(mContext, newsMsg.getMsg());
                }

            }

            @Override
            public void onFinish() {
                if(first){
                    first = false;
                    mAdapter.setOnRefresh(new MyRecyclerViewAdapter.OnRefresh() {
                        @Override
                        public void Refresh() {
                            MyLogger.showLogWithLineNum(3,"到底了");
                            page++;
                            baseLoadData();
                            mSwipeRefreshLayout.setRefreshing(true);
                        }
                    });
                }

            }
        });
    }

    public void getJuheNews() {
        super.baseLoadData();
        RequestParams params = new RequestParams(Conts.API_HTTPS_NEWS_JUHE);
        params.addBodyParameter("q", "科技");
        params.addBodyParameter("key", MyHelper.NEWS_API_KEYS_JUHE);
        params.addBodyParameter("dtype", "json");
        baseManager.http2Get(params, JuheNewsApi.class, new LsjBaseCallBack() {
            @Override
            public void onSucces(Object result) {
                JuheNewsApi JuheNewsMsg = (JuheNewsApi) result;
                if(JuheNewsMsg.getError_code() == 0){
                    MyLogger.showLogWithLineNum(3,"1--------------成功");
                }
            }

        });
    }

    private void initOrRefresh(){
        if(mAdapter == null){
            mAdapter = new MyRecyclerViewAdapter(this, NewsList, 0);
            mRecyclerView.setAdapter(mAdapter);

        }else{
            mAdapter.notifyDataSetChanged();
        }

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onRefresh() {
        page = 1;
        baseLoadData();
    }
}
