package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.CacheUtils;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.userBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;

import org.xutils.common.Callback;
import org.xutils.common.util.MD5;
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

    private int type; //0新闻界面进入， 1社区界面进入
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        type = getIntent().getIntExtra("type",0);
    }

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
        if(mEditPassword.getText().length() < 6){
            mCardBtnLogin.setClickable(false);
            mCardBtnLogin.setEnabled(false);
        }

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
                if(mEditPhone.getText().length() == 0){
                    MyToast.showToast(mContext,"手机号为空了");
                }else{
                    userLogin();
                }

                break;
            case R.id.txt_btn_forget_psw:

                break;
            case R.id.txt_btn_register:
                UiHelper.showUserRegister(mContext);
                break;
        }
    }

    private void userLogin(){
        String md5Pwd = MD5.md5(mEditPassword.getText().toString().trim());
        RequestParams params = new RequestParams(Conts.POST_USER_LOGIN);
        params.addBodyParameter("phone", mEditPhone.getText().toString().trim());
        params.addBodyParameter("password", md5Pwd);
//        params.addBodyParameter("username", "李上健");
//        params.addBodyParameter("password","123456");

        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
//                DbCookieStore instance = DbCookieStore.INSTANCE;
//                List cookies = instance.getCookies();
                userBean mUserBean = JSON.parseObject(s,userBean.class);
                switch (mUserBean.getResultCode()){
                    case 1:
                        MyToast.showToast(mContext, "登录成功");
                        CacheUtils.setLoginCache(mContext,"phone",mEditPhone.getText().toString().trim());
                        CacheUtils.setLoginCache(mContext,"password",mEditPassword.getText().toString().trim());
                        MyHelper.USER_HEAD_IMG = mUserBean.getuImg();
                        MyHelper.USER_NAME = mUserBean.getuName();
                        if(type == 1){
                            setResult(1);
                        }
                        AppManager.getAppManager().finishActivity();
                        break;
                    case 0:
                        MyToast.showToast(mContext, "用户名或密码错误");
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_login;
    }
}
