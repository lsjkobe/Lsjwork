package com.lsj.lsjnews.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.NetNewsListAdapter;
import com.lsj.lsjnews.adapter.SportNewsAdapter;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.bean.LsjNewsList;
import com.lsj.lsjnews.bean.NewsApi;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.http.HttpHelper;
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
    private boolean first = true;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NetNewsListAdapter mAdapter;
    private List<LsjNewsBean> mSportNewsList = new ArrayList<>();
    private int page = 1;
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
        getNews();
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
    public void getNews() {
        RequestParams params = new RequestParams(MyApi.NEWS_DETAIL+MyApi.HEADLINE_TYPE+"/"+MyApi.SPORTS_ID+"/"+"0"+ MyApi.END_URL);
//        baseManager.http2Get(params, LsjNewsList.class, new LsjBaseCallBack() {
//            @Override
//            public void onSucces(Object result) {
//                LsjNewsList mNewsApi = (LsjNewsList) result;
//                MyLogger.showLogWithLineNum(3,TAG+":"+mNewsApi.getT1348649079062().get(0).getTitle());
//            }
//
//        });
        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String str) {
                LsjNewsList mNewsList = JSON.parseObject(str, LsjNewsList.class);
                if(mNewsList.getT1348649079062() != null){
                    if(page == 1){
                        mSportNewsList.clear();
                    }
                    mSportNewsList.addAll(mNewsList.getT1348649079062());
                    initOrRefresh();
                }

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
        getNews();
    }
}
