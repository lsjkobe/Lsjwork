package lsj.kobe.lsjchat.client.model;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.comment.LsjHelp;

/**
 * Created by Administrator on 2016/5/25.
 */
public class LsjClient {
    private final String HOST = "10.16.4.12";
    private final int PORT = 10001;
    private Context context;
    private Socket socket;
    public LsjClient(Context context){
        this.context = context;
    }
    public boolean sendLoginMsg(Object object){
        try {
            socket = new Socket();
            try{
                socket.connect(new InetSocketAddress(HOST,PORT),2000);
            }catch(SocketTimeoutException e){
                //连接服务器超时
                return false;
            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(object);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            MessageBean message = (MessageBean) ois.readObject();
            if(message.getType().equals("success")){
                //登录用户的id
                LsjHelp.USER_ID = message.getUid();
                LsjHelp.USER_IMG = message.getImg();
                LsjChatClientThread thread = new LsjChatClientThread(socket,context);
                thread.start();
                LsjChatClientThreadManager.addClientThread(((UserBean)object).getAccount(), thread);
                return true;
            }else{
                return false;
            }
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
//            String info = null;
//            while((info=br.readLine())!=null){
////                Message msg = new Message();
////                msg.obj = info;
////                msg.what = 3;
//                Log.i("-----------",info);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
