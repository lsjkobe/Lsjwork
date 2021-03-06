package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.UserMsgAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * Created by lsj on 2016/5/18.
 */
public class UserMainCollectBBS extends MyBaseActivity{
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private UserMsgAdapter mMsgAdapter;
    private List<bbsBean.Lists> bbsBeanList = new ArrayList<>();
    private LsjLoadingView mLoading;
    private TextView mTxtNoData;
    private int page = 1;
    private int pageCount = 1;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        showTopView(false);
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_user_main_collect_bbs);
        mLoading = (LsjLoadingView) findViewById(R.id.loading_user_main_collect_bbs);
        mTxtNoData = (TextView) findViewById(R.id.txt_user_main_collect_bbs_no_data);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_main_collect_bbs);
//        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        initToolbar();
        getBBSData();
        initRecycler();
    }
    private void initToolbar() {
        mToolbar.setBackgroundResource(R.color.colorPrimary);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setTitle("我的收藏");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void getBBSData(){
        RequestParams params = new RequestParams(Conts.GET_USER_COLLECT_BBS);
        params.addBodyParameter("page",String.valueOf(page));
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3,s);
                bbsBean mbbsBean = JSON.parseObject(s, bbsBean.class);
                switch(mbbsBean.getResultCode()){
                    case 400:
                        MyToast.showToast(mContext,"没登录");
                        break;
                    case 1:
                        pageCount = mbbsBean.getPageCount();
                        if(mbbsBean.getLists() != null && mbbsBean.getLists().size() != 0){
                            if(page == 1){
                                bbsBeanList.clear();
                            }
                            bbsBeanList.addAll(mbbsBean.getLists());
                            initOrRefresh();
                        }
                        break;
                    case 0:
                        initOrRefresh();
                        mTxtNoData.setVisibility(View.VISIBLE);
                        MyToast.showToast(mContext,"没数据");
                        break;
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
            }
        });

    }
    private void initOrRefresh(){

        if(mMsgAdapter == null){
            mMsgAdapter = new UserMsgAdapter(mContext, bbsBeanList);
            mRecyclerView.setAdapter(mMsgAdapter);
            mMsgAdapter.setOnRefresh(new OnRefresh() {
                @Override
                public void Refresh() {
                    page++;
                    if(page > pageCount){
                        MyToast.showToast(mContext,"没有数据了");
                    }else{
                        mLoading.setVisibility(View.VISIBLE);
                        mLoading.startLoadingAnim();
                        getBBSData();
                    }
                }
            });
        }else{
            //数据长度改变时动态改变配置，防止数组下标越界
            mMsgAdapter.changeNewConfig();
            mMsgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main_collect_bbs;
    }
}
