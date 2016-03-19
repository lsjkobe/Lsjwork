package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.common.UiHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.util.List;

/**
 * Created by Le on 2016/3/15.
 */
public class UserLoginActivity extends MyBaseActivity implements View.OnClickListener{

    private EditText mEditPhone, mEditPassword;
    private CardView mCardBtnLogin;
    private TextView mTxtForgetPwd, mTxtRegister;
    private ImageView mImgIsClick;
    @Override
    protected void initView() {
        super.initView();
        mEditPhone = (EditText) findViewById(R.id.edit_user_phone);
        mEditPassword = (EditText) findViewById(R.id.edit_user_password);
        mCardBtnLogin = (CardView) findViewById(R.id.card_btn_user_login);
        mTxtForgetPwd = (TextView) findViewById(R.id.txt_btn_forget_psw);
        mTxtRegister = (TextView) findViewById(R.id.txt_btn_register);
        mImgIsClick = (ImageView) findViewById(R.id.img_is_can_click);
        mTxtForgetPwd.setOnClickListener(this);
        mTxtRegister.setOnClickListener(this);
        mCardBtnLogin.setOnClickListener(this);
        mEditPassword.addTextChangedListener(new pwdTextWatcher());
    }
    @Override
    protected void initData() {
        mEditPhone.setText("13726226699");
        mEditPassword.setText("123456");
        mTxtForgetPwd.setText(Html.fromHtml("<u>" + "忘记密码" + "</u>"));
        mTxtRegister.setText(Html.fromHtml("<u>" + "没有帐号" + "</u>"));
        mCardBtnLogin.setClickable(false);
        mCardBtnLogin.setEnabled(false);
//        mCardBtnLogin.setMaxCardElevation(10f);
    }

    private class pwdTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() >= 6){
                mCardBtnLogin.setClickable(true);
                mCardBtnLogin.setEnabled(true);
                mImgIsClick.setVisibility(View.INVISIBLE);
            }else{
                mCardBtnLogin.setClickable(false);
                mCardBtnLogin.setEnabled(false);
                mImgIsClick.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.card_btn_user_login:
                userLogin();
                break;
            case R.id.txt_btn_forget_psw:
                RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/getBBSDate.php");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        MyToast.showToast(mContext, s);
//                        DbCookieStore instance = DbCookieStore.INSTANCE;
//                        List cookies = instance.getCookies();
                        MyLogger.showLogWithLineNum(3,"---------json:"+s);
                    }
                    @Override
                    public void onError(Throwable throwable, boolean b) {}
                    @Override
                    public void onCancelled(CancelledException e) {}
                    @Override
                    public void onFinished() {}
                });
                break;
            case R.id.txt_btn_register:
//                UiHelper.showUserRegister(mContext);
                RequestParams logoutParams = new RequestParams("http://182.254.145.222/lsj/mdnews/user/user_logout.php");
                x.http().post(logoutParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        MyToast.showToast(mContext, s);
//                        DbCookieStore instance = DbCookieStore.INSTANCE;
//                        List cookies = instance.getCookies();
                        MyLogger.showLogWithLineNum(3,"---------json:"+s);
                    }
                    @Override
                    public void onError(Throwable throwable, boolean b) {}
                    @Override
                    public void onCancelled(CancelledException e) {}
                    @Override
                    public void onFinished() {}
                });
                break;
        }
    }

    private void userLogin(){
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/user_login.php");
        params.addBodyParameter("phone", mEditPhone.getText().toString().trim());
        params.addBodyParameter("password", mEditPassword.getText().toString().trim());
//        params.addBodyParameter("username", "李上健");
//        params.addBodyParameter("password","123456");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                MyToast.showToast(mContext, s);
                DbCookieStore instance = DbCookieStore.INSTANCE;
                List cookies = instance.getCookies();
                MyLogger.showLogWithLineNum(3,"---------coolies:"+cookies.get(0).toString());
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_login;
    }
}
