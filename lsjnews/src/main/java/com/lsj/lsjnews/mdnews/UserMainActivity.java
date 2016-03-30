package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.CardView;
import android.view.View;
import com.example.lsj.httplibrary.utils.CacheUtils;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/3/25.
 */
public class UserMainActivity extends MyBaseActivity implements View.OnClickListener{
    private CardView mCardLogout;

    @Override
    protected void initView() {
        super.initView();
        mCardLogout = (CardView) findViewById(R.id.card_user_logout);
        mCardLogout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_user_logout:
                userLogout();
                break;
        }
    }

    private void userLogout() {
        RequestParams logoutParams = new RequestParams(Conts.POST_USER_LOGOUT);
        x.http().post(logoutParams, new NewCallBack() {
            @Override
            public void onSuccess(String s) {
                if(s.equals("1")){
                    MyToast.showToast(mContext,"注销成功");
                    MyHelper.USER_HEAD_IMG = "";
                    MyHelper.USER_NAME = "";
                    CacheUtils.setCacheClear(mContext);
                }
            }
        });
    }
}
