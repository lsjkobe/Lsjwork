package com.example.lsj.lsjwork.socket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lsj.lsjwork.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class AndroidClient extends Activity{
    private final String HOST = "10.16.4.12";
    private final int PORT = 10001;
    private Socket mSocket;
    protected Context mContext=this;
    private PrintStream output;
    private RecyclerView mRecyclerChat;
    private List<String> mListChat = new ArrayList<>();
    private testAdapter mAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        mRecyclerChat = (RecyclerView) findViewById(R.id.recycler_chat_msg);
        initRecycler();
        onSendClick();
    }

    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerChat.setLayoutManager(mLinearLayoutManager);
        mAdapter = new testAdapter(mListChat);
        mRecyclerChat.setAdapter(mAdapter);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Toast.makeText(mContext,"连接成功",Toast.LENGTH_SHORT).show();
            }
            if(msg.what == 2){
                Toast.makeText(mContext,"发送失败",Toast.LENGTH_SHORT).show();
            }
            if(msg.what == 3){
//                Toast.makeText(mContext,msg.obj+":",Toast.LENGTH_SHORT).show();
                if(msg.obj != null){
                    mListChat.add(msg.obj.toString());
                    mAdapter.notifyDataSetChanged();
                }

            }
        }
    };
    private void onSendClick(){
//        Toast.makeText(mContext,"连接..",Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(HOST, PORT);
                    mHandler.sendEmptyMessage(1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMsg(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    output = new PrintStream(mSocket.getOutputStream(), true, "utf-8");
//                    output.print("李上健");
//                    output.flush();
                    if (mSocket.isConnected()) {
                        if (!mSocket.isOutputShutdown()) {
                            output.println("李上健");
                        }
                    }
                    output.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"utf-8"));
                    String info = null;
                    while((info=br.readLine())!=null){
                        Message msg = new Message();
                        msg.obj = info;
                        msg.what = 3;
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    mHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private class testAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<String> mList;
        public testAdapter(List<String> mList){
            this.mList = mList;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_msg,parent,false);
            chatViewHolder viewHolder = new chatViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            chatViewHolder viewHolder = (chatViewHolder) holder;
            viewHolder.mTxtChat.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class chatViewHolder extends RecyclerView.ViewHolder{
            TextView mTxtChat;
            public chatViewHolder(View itemView) {
                super(itemView);
                mTxtChat = (TextView) itemView.findViewById(R.id.txt_chat_msg);
            }
        }
    }
}
