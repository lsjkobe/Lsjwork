package com.lsj.lsjnews.mdnews.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.OtherUserMsgAdapter;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/13.
 */
public class OtherUserBBS extends BaseFragment{
    private RecyclerView mRecyclerView;
    private OtherUserMsgAdapter mMsgAdapter;
    private List<bbsBean.Lists> bbsBeanList = new ArrayList<>();
    private bbsBean.Lists nullBean = new bbsBean.Lists(); // 一个空的bean，在adapter会被header代替
    private LsjLoadingView mLoading;
    private int page = 1;
    private int pageCount;
    private int uid;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        showTopView(false);
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_other_user_main);
        mLoading = (LsjLoadingView) findViewById(R.id.loading_other_user_main_bbs);
//        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        getBBSData();
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void getBBSData(){
        RequestParams params = new RequestParams(Conts.GET_OTHER_USER_BBS);
        params.addBodyParameter("uid",String.valueOf(uid));
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
                                bbsBeanList.add(nullBean);
                            }
                            bbsBeanList.addAll(mbbsBean.getLists());
                            initOrRefresh();
                        }
                        break;
                    case 0:
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
            mMsgAdapter = new OtherUserMsgAdapter(mContext, bbsBeanList);
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
    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_user_bbs;
    }
}
