package lsj.kobe.lsjchat.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.client.view.LsjChat;
import lsj.kobe.lsjchat.client.view.MainActivity;
import lsj.kobe.lsjchat.test;

/**
 * Created by Administrator on 2016/5/26.
 */
public class UiHelp {

    /**
     * 主界面
     * @param context
     */
    public static void showMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        context.startActivity(intent);
    }
    /**
     * 聊天
     * @param context
     */
    public static void showChatActivity(Context context, UserBean user){
        Intent intent = new Intent(context, LsjChat.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 测试
     * @param context
     */
    public static void showTestActivity(Context context){
        Intent intent = new Intent(context, test.class);
        context.startActivity(intent);
    }
}
