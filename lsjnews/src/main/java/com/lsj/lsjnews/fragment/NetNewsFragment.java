package com.lsj.lsjnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.NetNewsListAdapter;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.mainHelper;
import com.lsj.lsjnews.http.MyApi;
import com.lsj.lsjnews.interfaces.OnRefresh;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class NetNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "NetNewsFragment.class";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NetNewsListAdapter mAdapter;
    private List<LsjNewsBean> mSportNewsList = new ArrayList<>();
    private int page = 0;
    private int mFragmentType;
    private boolean first = true;
    public static NetNewsFragment newInstance(int type){
        NetNewsFragment mNetNewsFragment = new NetNewsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("type", type);
        mNetNewsFragment.setArguments(mBundle);
        return mNetNewsFragment;
    }
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        mFragmentType = getArguments().getInt("type");
        MyLogger.showLogWithLineNum(3, "====================type:" + mFragmentType);
    }

    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_news_main_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_news_main_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
    }

    @Override
    protected void initData() {
        initRecycleDate();
        if(mFragmentType == 0){
            baseLoadData();
        }
    }

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
                final Snackbar mSnackbar = Snackbar.make(mRecyclerView, "回到顶部", Snackbar.LENGTH_SHORT).setAction("確定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                });
                if (dy < 0) {
                    mSnackbar.show();
                }
            }

        });

    }

    @Override
    public void baseLoadData() {
        super.baseLoadData();
        //nc/article/{type}/{id}/{startPage}-20.html
        RequestParams params = new RequestParams(MyApi.NEWS_DETAIL+MyApi.HEADLINE_TYPE+"/"+MyHelper.mTypeMap.get(mFragmentType)+"/"+page+ MyApi.END_URL);
//        RequestParams params = new RequestParams(MyApi.NEWS_DETAIL+MyApi.HEADLINE_TYPE+"/"+MyHelper.mTypeMap.get(MyHelper.NBA_News_Type)+"/"+"0"+ MyApi.END_URL);
        mSwipeRefreshLayout.setRefreshing(true);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String str) {
                isLoadSuccess = true;
                if (mainHelper.getNewsDataByType(mFragmentType, str) != null) {
                    if (page == 0) {
                        mSportNewsList.clear();
                    }
                    mSportNewsList.addAll(mainHelper.getNewsDataByType(mFragmentType, str));
                    initOrRefresh();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                MyLogger.showLogWithLineNum(3, TAG + ":onError");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mSwipeRefreshLayout.setRefreshing(false);
                MyLogger.showLogWithLineNum(3, TAG + ":onFinished");
                if(first){
                    first = false;
                    mAdapter.setOnRefresh(new OnRefresh() {
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

    private void initOrRefresh(){
        if(mAdapter == null){
            mAdapter = new NetNewsListAdapter(mContext, mSportNewsList, true);
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
        page = 0;
        baseLoadData();
    }
}
