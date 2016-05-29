package lsj.kobe.lsjchat.client.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.view.fragment.FriendListsFragment;
import lsj.kobe.lsjchat.comment.LsjHelp;
import lsj.kobe.lsjchat.comment.LsjMessageType;

/**
 * Created by lsj on 2016/5/26.
 */
public class LsjChatClientThread extends Thread{
    private Socket socket;
    private Context context;
    public LsjChatClientThread(Socket socket, Context context){
        this.socket = socket;
        this.context = context;
    }
    public void run() {
        ObjectInputStream ois = null;
        MessageBean msg;
        try {
            while(true){
            ois = new ObjectInputStream(socket.getInputStream());
//            Log.i("--------------","其它:"+ois.readObject());
            msg = (MessageBean) ois.readObject();
            if(msg.getType().equals(LsjMessageType.COM_MES) || msg.getType().equals(LsjMessageType.GROUP_MES)){
//                Toast.makeText()
                Log.i("--------------","聊天信息:"+msg.getContent());
//                handler.sendEmptyMessage(0);
                Intent intent = new Intent(LsjHelp.ACTION_MSG);
//                String message = msg.getContent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("msg",msg);
                intent.putExtras(bundle);
                context.sendBroadcast(intent);
            }else if(msg.getType().equals(LsjMessageType.RET_ONLINE_FRIENDS)){
                Log.i("--------------","其它:"+msg.getContent());
//                UserBean user = JSON.parseObject(msg.getContent(), UserBean.class);
//                List<UserBean> userLists = (List<UserBean>) JSON.parseArray(msg.getContent(),UserBean.class);
//                Log.i("--------------:",userLists.get(0).getAccount());
                FriendListsFragment.userContent = msg.getContent();
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Socket getSocket() {return socket;}
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what == 0){
//                Toast.makeText(context,"信息",Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
}
