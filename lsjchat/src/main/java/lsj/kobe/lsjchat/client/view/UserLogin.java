package lsj.kobe.lsjchat.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectOutputStream;
import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.client.model.HeartbeatService;
import lsj.kobe.lsjchat.client.model.LsjChatClientThreadManager;
import lsj.kobe.lsjchat.client.model.LsjClient;
import lsj.kobe.lsjchat.comment.LsjHelp;
import lsj.kobe.lsjchat.comment.LsjMessageType;
import lsj.kobe.lsjchat.comment.UiHelp;

/**
 * Created by lsj on 2016/5/25.
 */
public class UserLogin extends Activity implements View.OnClickListener{
    private Context context = this;
    private EditText mEditNum,mEditPwd;
    private Button mBtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initView();
    }

    private void initView() {
        mEditNum = (EditText) findViewById(R.id.edit_user_num);
        mEditPwd = (EditText) findViewById(R.id.edit_user_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_user_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_login:
                if(mEditNum.getText().toString().equals("") || mEditPwd.getText().toString().equals("") || mEditNum.getText().length() == 0 || mEditPwd.getText().length() == 0){
                    Toast.makeText(this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }else{

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userLogin();
                        }
                    }).start();
                }
                break;
        }
    }

    private void userLogin() {
        UserBean mUser = new UserBean();
        mUser.setAccount(mEditNum.getText().toString());
        mUser.setPassword(mEditPwd.getText().toString());
        mUser.setOperation("login");
        //登录成功发送好友列表请求
        if(new LsjClient(this).sendLoginMsg(mUser)){
            Log.i("----------","成功");
            handler.sendEmptyMessage(1);
            try {
                ObjectOutputStream oos = new ObjectOutputStream( LsjChatClientThreadManager.getClientThread(mUser.getAccount()).getSocket().getOutputStream());
                MessageBean msg = new MessageBean();
                msg.setType(LsjMessageType.RET_ONLINE_FRIENDS);
                msg.setUid(LsjHelp.USER_ID);
                msg.setSender(mUser.getAccount());
                oos.writeObject(msg);
//                ObjectInputStream ois = new ObjectInputStream(LsjChatClientThreadManager.getClientThread(mUser.getAccount()).getSocket().getInputStream());
//                MessageBean msg = (MessageBean) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LsjHelp.USER_ACCOUNT = mUser.getAccount();
            UiHelp.showMainActivity(context);
            this.finish();
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Intent startIntent = new Intent(UserLogin.this, HeartbeatService.class);

                startService(startIntent);
            }
        }
    };
}
