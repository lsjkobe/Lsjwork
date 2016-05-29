package lsj.kobe.lsjchat.client.model;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/26.
 */
public class LsjChatClientThreadManager {
    private static HashMap<String,LsjChatClientThread> hm=new HashMap<>();
    //把创建好的ClientConServerThread放入到hm
    public static void addClientThread(String account,LsjChatClientThread ccst){
        hm.put(account, ccst);
    }

    //可以通过account取得该线程
    public static LsjChatClientThread getClientThread(String i){
        return (LsjChatClientThread)hm.get(i);
    }
}
