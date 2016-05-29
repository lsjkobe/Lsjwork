package lsj.kobe.lsjchat.client.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.client.view.adapter.FriendAdapter;

/**
 * Created by lsj on 2016/5/26.
 */
public class FriendLists extends Activity {
    public static String userContent = "";
    private RecyclerView mRecyclerView;
    protected Context context = this;
    private List<UserBean> userLists = new ArrayList<>();
    private FriendAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_user_friends_list);
    }
    private void initData() {
//        UserBean user = JSON.parseObject(userContent,UserBean.class);
        userLists = (List<UserBean>) JSON.parseArray(userContent,UserBean.class);
        Log.i("--------------:",userLists.get(0).getAccount());
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new FriendAdapter(context,userLists);
        mRecyclerView.setAdapter(mAdapter);
    }
}
