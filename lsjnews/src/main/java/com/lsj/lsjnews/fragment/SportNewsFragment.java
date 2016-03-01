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
import com.lsj.lsjnews.adapter.SportNewsAdapter;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.bean.NewsApi;
import com.lsj.lsjnews.common.Conts;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.interfaces.OnRefresh;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class SportNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private boolean first = true;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SportNewsAdapter mAdapter;
    private List<NewsApi.Contentlist> NewsList = new ArrayList<>();
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

        });

    }
    public void getNews() {
        RequestParams params = new RequestParams(Conts.API_HTTPS_NEWS_1);
        params.addHeader("apikey", MyHelper.NEWS_API_KEYS);

        params.addBodyParameter("channelId", "5572a109b3cdc86cf39001db");
        params.addBodyParameter("channelName", "科技焦点");
        params.addBodyParameter("page", "page");
        baseManager.http2Get(params, NewsApi.class, new LsjBaseCallBack() {
            @Override
            public void onSucces(Object result) {
                mSwipeRefreshLayout.setRefreshing(false);
                NewsApi mNewsApi = (NewsApi) result;
                if(mNewsApi.getShowapi_res_code() == 0){
                    MyLogger.showLogWithLineNum(3,"______________success");
                    if(page == 1){
                        NewsList.clear();
                    }
                    NewsList.addAll(mNewsApi.getShowapi_res_body().getPagebean().getContentlist());
                    initOrRefresh();
                }else{
                    MyToast.showToast(mContext, mNewsApi.getShowapi_res_error()+"：123456");
                }
            }
            @Override
            public void onFinish() {
//                if(first){
//                    first = false;
//                    mAdapter.setOnRefresh(new OnRefresh() {
//                        @Override
//                        public void Refresh() {
//                            MyLogger.showLogWithLineNum(3,"到底了");
//                            page++;
//                            getNews();
//                            mSwipeRefreshLayout.setRefreshing(true);
//                        }
//                    });
//                }

            }
        });

    }
    private void initOrRefresh(){
        if(mAdapter == null){
            mAdapter = new SportNewsAdapter(mContext, NewsList);
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
