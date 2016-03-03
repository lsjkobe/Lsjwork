package com.lsj.lsjnews.fragment;


import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.MyRecyclerViewAdapter;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.bean.ApiNewsMsg;
import com.lsj.lsjnews.bean.NewsApi;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.interfaces.OnRefresh;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SocialNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView mRecyclerView;

    private List<ApiNewsMsg.NewsBean> NewsList = new ArrayList<>();
    private MyRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        baseLoadData();
    }
    int key = 0; //滚回顶部为1
    private void initRecycleDate(){
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, onItemClickListener));

//        final Snackbar snackbar = Snackbar.make(mRecyclerView, "SnackbarTest", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final Snackbar mSnackbar = Snackbar.make(mRecyclerView,"回到顶部",Snackbar.LENGTH_LONG).setAction("確定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.smoothScrollToPosition(0);
                        key = 1;
                    }
                });
                if(dy>0){
                    key = 0;
                }else if(page>=5 && key == 0){
                    mSnackbar.show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

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

    public void getNews() {
        RequestParams params = new RequestParams(Conts.API_HTTPS_NEWS_1);
        params.addHeader("apikey", MyHelper.NEWS_API_KEYS);
        params.addBodyParameter("channelId", "5572a109b3cdc86cf39001db");
        params.addBodyParameter("channelName", "科技焦点");
        params.addBodyParameter("page", "1");
        baseManager.http2Get(params, NewsApi.class, new LsjBaseCallBack() {
            @Override
            public void onSucces(Object result) {
                NewsApi mNewsApi = (NewsApi) result;
                if(mNewsApi.getShowapi_res_code() == 0){
                    MyLogger.showLogWithLineNum(3, "成功");
                }
            }
        });
    }

    private void initOrRefresh(){
        if(mAdapter == null){
            mAdapter = new MyRecyclerViewAdapter(mContext, NewsList, 0);
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
        baseLoadData();
    }
}
