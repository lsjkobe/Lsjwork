package lsj.kobe.lsjchat.client.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.client.model.LsjChatClientThreadManager;
import lsj.kobe.lsjchat.client.view.adapter.chatAdapter;
import lsj.kobe.lsjchat.comment.LsjHelp;
import lsj.kobe.lsjchat.comment.LsjMessageType;

/**
 * Created by lsj on 2016/5/26.
 */
public class LsjChat extends Activity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    protected Context context = this;
    private chatAdapter mAdapter;
    private EditText mEditSendMsg;
    private Button mBtnSend;
    private List<MessageBean> msgLists = new ArrayList<>();
    private String account; //接收方账号
    public static String userImg; // 接收方头像
    private String chatAccount; //发送方账号
    private UserBean user; // 点击的好友的对象
    MyBroadcastReceiver br; //广播
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = (UserBean) getIntent().getSerializableExtra("user");
        account = user.getAccount();
        userImg = user.getImg();
        chatAccount = LsjHelp.USER_ACCOUNT;
        initView();
        initDate();
    }
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_chat_list);
        mEditSendMsg = (EditText) findViewById(R.id.edit_chat_content);
        mBtnSend = (Button) findViewById(R.id.btn_chat_send);
        mBtnSend.setOnClickListener(this);
    }

    private void initDate() {
        initRecycler();
        initBroadcast();
    }

    private void initBroadcast() {
        //注册广播
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(LsjHelp.ACTION_MSG);
        br=new MyBroadcastReceiver();
        registerReceiver(br, myIntentFilter);
    }

    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new chatAdapter(context,msgLists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_chat_send:
                if(mEditSendMsg.getText().toString().equals("") || mEditSendMsg.getText().length() == 0){
                    Toast.makeText(context,"消息不能为空",Toast.LENGTH_SHORT).show();
                }else{
//                    MessageBean msg = new MessageBean();
//                    msg.setContent(mEditSendMsg.getText().toString());
                    MessageBean sendMsg = new MessageBean();
                    sendMsg.setKey(0);
                    sendMsg.setType(LsjMessageType.COM_MES);
                    sendMsg.setContent(mEditSendMsg.getText().toString());
                    handler.sendEmptyMessage(0);
                    sendMsg.setSender(chatAccount);
                    sendMsg.setReceiver(account);

                    SimpleDateFormat  sdf = new SimpleDateFormat("HH:mm:ss");
                    sendMsg.setSendTime(sdf.format(new Date()));
                    msgLists.add(sendMsg);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                    sengMsg(sendMsg);
//                    mEditSendMsg.setText("");

                }
                break;
        }
    }

    private void sengMsg(final MessageBean sendMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Log.i("---------123:",LsjHelp.USER_ACCOUNT);
                    ObjectOutputStream oos = new ObjectOutputStream(LsjChatClientThreadManager.getClientThread(LsjHelp.USER_ACCOUNT).getSocket().getOutputStream());
                    oos.writeObject(sendMsg);
//                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //广播接收器
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            MessageBean msg = (MessageBean) intent.getSerializableExtra("msg");
            msg.setKey(1);
            msgLists.add(msg);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                mEditSendMsg.setText("");
            }
        }
    };
}
