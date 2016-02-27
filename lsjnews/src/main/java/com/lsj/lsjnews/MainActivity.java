package com.lsj.lsjnews;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lsj.httplibrary.callback.BaseCallBack;
import com.example.lsj.httplibrary.callback.FailMessg;
import com.example.lsj.httplibrary.callback.MyCallBack;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.widget.RecyclerItemClickListener;
import com.lsj.lsjnews.adapter.MyRecyclerViewAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.bean.ApiNewsMsg;
import com.lsj.lsjnews.common.Conts;
import com.lsj.lsjnews.common.MyHelper;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void initData() {
        showTopView(false);
        baseLoadData();
        initRecycleDate();
    }

    private void initRecycleDate(){
        mRecyclerView = (RecyclerView) this.findViewById(R.id.view_main_recyclerView);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        MyRecyclerViewAdapter mAdapter = new MyRecyclerViewAdapter(this, datas, 0);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, onItemClickListener));
//        mAdapter.setOnRecyclerViewIteListener(new MyRecyclerViewAdapter.OnRecyclerViewIteListener() {
//            @Override
//            public void onItemClick(View view, String data) {
//                MyToast.showToast(mContext, data);
//            }
//        });
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            MyToast.showToast(mContext, ""+position);
        }
    };

    @Override
    public void baseLoadData() {
        super.baseLoadData();
        RequestParams params = new RequestParams(Conts.API_HTTPS_NEWS);
        params.addBodyParameter("num", "10");
        params.addBodyParameter("page", "1");
        params.addHeader("apikey", "9877658e3bf15280c371ded239997299");
        baseManager.http2Get(params, ApiNewsMsg.class, new BaseCallBack() {
            @Override
            public void onSucces(Object result) {
                ApiNewsMsg newsMsg = (ApiNewsMsg) result;
                MyToast.showToast(mContext,"nihao");
                if(newsMsg.getCode() == 200){
                    MyToast.showToast(mContext, "1:"+newsMsg.getMsg());
                }else{
                    MyToast.showToast(mContext, newsMsg.getMsg());
                }

            }

            @Override
            public void onResult(String result) {
//                MyToast.showToast(mContext, result.toString());
                MyLogger.showLogWithLineNum(3,result);
            }

            @Override
            public void onStart() {
                //niahoya
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFail(Context context, FailMessg result) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
