package com.lsj.lsjnews.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.NetNewsListAdapter;
import com.lsj.lsjnews.base.LsjBaseFragment;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.bean.news_type_bean.LsjNewsList;
import com.lsj.lsjnews.bean.news_type_bean.NBANewsList;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.mainHelper;
import com.lsj.lsjnews.http.MyApi;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class NetNewsFragment extends LsjBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "NetNewsFragment.class";
    private boolean first = true;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NetNewsListAdapter mAdapter;
    private List<LsjNewsBean> mSportNewsList = new ArrayList<>();
    private int page = 1;
    private int mFragmentType;
    public static NetNewsFragment newInstance(int type){
        NetNewsFragment mNetNewsFragment = new NetNewsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("type", type);
        mNetNewsFragment.setArguments(mBundle);
        return mNetNewsFragment;
    }
    @Override
    protected void onVisible() {
        mFragmentType = getArguments().getInt("type");
        MyLogger.showLogWithLineNum(3, "====================type:"+mFragmentType);
        getNews(mFragmentType);
    }
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
    }

    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_news_main_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_news_main_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        initRecycleDate();

    }

    int key = 0; //滚回顶部为1
    private void initRecycleDate(){
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final Snackbar mSnackbar = Snackbar.make(mRecyclerView, "回到顶部", Snackbar.LENGTH_LONG).setAction("確定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.smoothScrollToPosition(0);
                        key = 1;
                    }
                });
                if (dy > 0) {
                    key = 0;
                } else if (page >= 5 && key == 0) {
                    mSnackbar.show();
                }
            }

        });

    }
    //nc/article/{type}/{id}/{startPage}-20.html
    public void getNews(final int type) {
        RequestParams params = new RequestParams(MyApi.NEWS_DETAIL+MyApi.HEADLINE_TYPE+"/"+MyHelper.mTypeMap.get(type)+"/"+"0"+ MyApi.END_URL);
//        RequestParams params = new RequestParams(MyApi.NEWS_DETAIL+MyApi.HEADLINE_TYPE+"/"+MyHelper.mTypeMap.get(MyHelper.NBA_News_Type)+"/"+"0"+ MyApi.END_URL);
        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String str) {
                if(mainHelper.getNewsDataByType(type, str) != null){
                    if(page == 1){
                        mSportNewsList.clear();
                    }
                    mSportNewsList.addAll(mainHelper.getNewsDataByType(type, str));
                    initOrRefresh();
                }

//                switch(type){
//                    case MyHelper.NBA_News_Type:
//                        NBANewsList nbaNewsList = JSON.parseObject(str, NBANewsList.class);
//                        if(nbaNewsList.getT1348649145984() != null){
//                            if(page == 1){
//                                mSportNewsList.clear();
//                            }
//                            mSportNewsList.addAll(nbaNewsList.getT1348649145984());
//                            initOrRefresh();
//                        }
//                        break;
//                    default:
//                    case MyHelper.Sport_News_Type:
//                        LsjNewsList mNewsList = JSON.parseObject(str, LsjNewsList.class);
//                        if(mNewsList.getT1348649079062() != null){
//                            if(page == 1){
//                                mSportNewsList.clear();
//                            }
//                            mSportNewsList.addAll(mNewsList.getT1348649079062());
//                            initOrRefresh();
//                        }
//                        break;
//                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                MyLogger.showLogWithLineNum(3,TAG+":onError");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mSwipeRefreshLayout.setRefreshing(false);
                MyLogger.showLogWithLineNum(3,TAG+":onFinished");
            }
        });

    }
    private void initOrRefresh(){
        if(mAdapter == null){
            mAdapter = new NetNewsListAdapter(mContext, mSportNewsList);
            mRecyclerView.setAdapter(mAdapter);

        }else{
            mAdapter.notifyDataSetChanged();
        }

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_main;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getNews(mFragmentType);
    }
}
