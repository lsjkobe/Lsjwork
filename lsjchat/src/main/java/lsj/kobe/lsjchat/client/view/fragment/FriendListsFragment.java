package lsj.kobe.lsjchat.client.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.client.view.adapter.FriendAdapter;
import lsj.kobe.lsjchat.comment.LsjHelp;

/**
 * Created by lsj on 2016/5/26.
 */
public class FriendListsFragment extends Fragment {
    private View view;
    public static String userContent = "";
    private RecyclerView mRecyclerView;
    protected Context context;
    private List<UserBean> userLists = new ArrayList<>();
    private FriendAdapter mAdapter;

    msgBroadcastReceiver br ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_user_friends_list, container, false);
        context = getActivity();
        initView();
        initData();
        return view;
    }
    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_user_friends_list);
    }
    private void initData() {
        if(!userContent.equals("")){
            userLists = (List<UserBean>) JSON.parseArray(userContent,UserBean.class);
        }
//        Log.i("--------------:",userContent);
        initRecycler();
        initBroadcast();

    }

    private void initBroadcast() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(LsjHelp.ACTION_MSG);
        br = new msgBroadcastReceiver();
        context.registerReceiver(br,mFilter);
    }

    private class msgBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            MessageBean msg = (MessageBean) intent.getSerializableExtra("msg");
            Log.i("--------------:",msg.getSender());
            for(int i=0;i<userLists.size(); i++){
                if(userLists.get(i).getAccount().equals(msg.getSender())){
                    userLists.get(i).setMsgCount(userLists.get(i).getMsgCount()+1);
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new FriendAdapter(context,userLists);
        mRecyclerView.setAdapter(mAdapter);
    }
}
