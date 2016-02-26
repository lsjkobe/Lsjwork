package com.lsj.lsjnews;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.widget.RecyclerItemClickListener;
import com.lsj.lsjnews.adapter.MyRecyclerViewAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void initData() {
        showTopView(false);
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
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
