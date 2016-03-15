package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Le on 2016/3/15.
 */
public class UserRegisterActivity extends MyBaseActivity implements View.OnClickListener{
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_btn_user_register:
                userRegister();
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
    }
}
