package com.lsj.lsjnews.mdnews;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Le on 2016/3/15.
 */
public class UserRegisterActivity extends MyBaseActivity implements View.OnClickListener,Handler.Callback{
    private EditText mEditPhone, mEditPwd;
    private CardView mCardRegister;
    private ImageView mImgIsClick;
    @Override
    protected void initView() {
        super.initView();
        mCardRegister = (CardView) findViewById(R.id.card_btn_user_register);
        mEditPhone  = (EditText) findViewById(R.id.edit_user_register_phone);
        mEditPwd = (EditText) findViewById(R.id.edit_user_register_password);
        mImgIsClick = (ImageView) findViewById(R.id.img_register_is_can_click);
        mCardRegister.setOnClickListener(this);

    }
    @Override
    protected void initData() {
        mEditPwd.addTextChangedListener(new registerWatcher());
        initSDK();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_btn_user_register:
//                userRegister();
//                SMSSDK.getVerificationCode("+86","13726226699");
                SMSSDK.getSupportedCountries();
                break;
        }
    }


    private class registerWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() >= 6){
                mCardRegister.setClickable(true);
                mCardRegister.setEnabled(true);
                mImgIsClick.setVisibility(View.INVISIBLE);
            }else{
                mCardRegister.setClickable(false);
                mCardRegister.setEnabled(false);
                mImgIsClick.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void userRegister(){
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/user_register.php");
        params.addBodyParameter("username", mEditPhone.getText().toString().trim());
        params.addBodyParameter("password",mEditPwd.getText().toString().trim());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                MyToast.showToast(mContext, s);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private boolean is_smssdk_register = false;
    private void initSDK() {
        // 初始化短信SDK
        SMSSDK.initSDK(this, "1077c5d8774ee", "84a5f9ee0cb55f01703548026df279e3", true);
        final Handler handler = new Handler(this);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        is_smssdk_register = true;
    }
    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == SMSSDK.RESULT_COMPLETE) {
            //回调完成
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                //提交验证码成功
                MyToast.showToast(mContext,"提交验证码成功");
            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                //获取验证码成功
                MyToast.showToast(mContext,"获取验证码成功");
            }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                //返回支持发送验证码的国家列表

//                HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                MyToast.showToast(mContext,"返回支持发送验证码的国家列表+"+data.toString());
            }
        }else{
            ((Throwable)data).printStackTrace();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if(is_smssdk_register){
            SMSSDK.unregisterAllEventHandler();
        }

        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
    }

}
