package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserBBSMain extends MyBaseActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyleMsg;

    @Override
    protected void initView() {
        super.initView();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_bbs);
        mRecyleMsg = (RecyclerView) findViewById(R.id.recyle_user_bbs);
    }

    @Override
    protected void initData() {
        getBBSData();
    }

    public void getBBSData() {
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs;
    }
}
