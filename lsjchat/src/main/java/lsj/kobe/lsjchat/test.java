package lsj.kobe.lsjchat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.ObjectOutputStream;

import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.model.LsjChatClientThreadManager;
import lsj.kobe.lsjchat.comment.LsjHelp;
import lsj.kobe.lsjchat.comment.LsjMessageType;

/**
 * Created by Administrator on 2016/5/26.
 */
public class test extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
    }
    public void sendMsg(View view){
        try {
//                    Log.i("---------123:",LsjHelp.USER_ACCOUNT);
            ObjectOutputStream oos = new ObjectOutputStream(LsjChatClientThreadManager.getClientThread(LsjHelp.USER_ACCOUNT).getSocket().getOutputStream());
            MessageBean sendMsg = new MessageBean();
            sendMsg.setType(LsjMessageType.COM_MES);
            sendMsg.setContent("nihao");
            sendMsg.setSender(LsjHelp.USER_ACCOUNT);
            sendMsg.setReceiver(LsjHelp.USER_ACCOUNT);
            oos.writeObject(sendMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
