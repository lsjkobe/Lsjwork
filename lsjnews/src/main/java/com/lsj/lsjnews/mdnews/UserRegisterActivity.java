package com.lsj.lsjnews.mdnews;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Le on 2016/3/15.
 */
public class UserRegisterActivity extends MyBaseActivity implements View.OnClickListener,Handler.Callback{
    private EditText mEditPhone, mEditPwd, mEditCode;
    private CardView mCardRegister,mCardGetCode;
    private ImageView mImgIsClick,mImgGetCodeIsClick;
    private TextView mTxtGetCode;

    private timeCount mTime; // 验证码倒计时
    private String mPhoneGetCode = ""; //获取验证码时记录获取验证码的手机号，防止验证码获取成功后修改手机号
    @Override
    protected void initView() {
        super.initView();
        mCardRegister = (CardView) findViewById(R.id.card_btn_user_register);
        mEditPhone  = (EditText) findViewById(R.id.edit_user_register_phone);
        mEditPwd = (EditText) findViewById(R.id.edit_user_register_password);
        mEditCode = (EditText) findViewById(R.id.edit_user_register_code);
        mImgIsClick = (ImageView) findViewById(R.id.img_register_is_can_click);
        mImgGetCodeIsClick = (ImageView) findViewById(R.id.img_get_code_is_can_click);
        mCardGetCode = (CardView) findViewById(R.id.card_btn_get_code);
        mTxtGetCode = (TextView) findViewById(R.id.txt_register_get_code);

        mCardRegister.setOnClickListener(this);
        mCardGetCode.setOnClickListener(this);
        mTime = new timeCount(60000, 1000);
    }
    @Override
    protected void initData() {

        mEditPhone.addTextChangedListener(new registerWatcher(1));
        mEditPwd.addTextChangedListener(new registerWatcher(0));
        mCardGetCode.setClickable(false);
        mCardGetCode.setEnabled(false);
        mCardRegister.setClickable(false);
        mCardRegister.setEnabled(false);
        initSDK();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_btn_user_register:
//                userRegister();
                if(mEditPhone.getText().toString().equals("") || mEditPhone.getText().toString() == null){
                    MyToast.showToast(mContext,"电话号码不能为空");
                }else if(mPhoneGetCode.equals("")){
                    MyToast.showToast(mContext,"没有获取验证码");
                }else if(mEditCode.getText().toString().equals("") || mEditCode.getText().toString() == null){
                    MyToast.showToast(mContext,"验证码不能为空");
                }else if(!mPhoneGetCode.equals(mEditPhone.getText().toString())){
                    MyToast.showToast(mContext,"手机号改变了，重新获取验证码");
                }else{
                    SMSSDK.submitVerificationCode("86",mEditPhone.getText().toString(),mEditCode.getText().toString());
                }
                break;
            case R.id.card_btn_get_code:
//                userRegister();
                SMSSDK.getVerificationCode("86", mEditPhone.getText().toString());

                break;
        }
    }


    private class registerWatcher implements TextWatcher{
        int key ;
        public registerWatcher(int key){
            this.key = key;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(key == 0){
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
            if(key == 1){
                if(s.length() >= 11){
                    mCardGetCode.setClickable(true);
                    mCardGetCode.setEnabled(true);
                    mImgGetCodeIsClick.setVisibility(View.INVISIBLE);
                }else{
                    mCardGetCode.setClickable(false);
                    mCardGetCode.setEnabled(false);
                    mImgGetCodeIsClick.setVisibility(View.VISIBLE);
                }
            }

        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void userRegister(){
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/user_register.php");
        params.addBodyParameter("phone", mEditPhone.getText().toString().trim());
        params.addBodyParameter("password",mEditPwd.getText().toString().trim());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
//                MyToast.showToast(mContext, s);
                baseBean bean = JSON.parseObject(s,baseBean.class);
                switch (bean.getResultCode()){
                    case 101:
                        MyToast.showToast(mContext, "手机号已经存在");
                        break;
                    case 1:
                        MyToast.showToast(mContext, "注册成功");
                        break;
                    case 0:
                        MyToast.showToast(mContext, "注册失败");
                        break;
                }
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
        SMSSDK.initSDK(this, "1077c5d8774ee", "84a5f9ee0cb55f01703548026df279e3",false);
//        SMSSDK.initSDK(this, "f6a97e467f20", "7c75beadd45786c06c29df2f7d6f85f6");

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
            if (event == 3) {
//                MyToast.showToast(mContext, "验证成功");
                userRegister();
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                MyToast.showToast(mContext, "验证码已经发送");
                mPhoneGetCode = mEditPhone.getText().toString();
                mTime.start();
            }
        } else {
            ((Throwable) data).printStackTrace();
            Toast.makeText(mContext, "验证码获取失败", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    class timeCount extends CountDownTimer {

        public timeCount(long millisInFuture, long countDownInterval) {
            //millisInFuture
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            mCardGetCode.setClickable(true);
            mTxtGetCode.setText("重获验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            mCardGetCode.setClickable(false);
            mTxtGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

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
