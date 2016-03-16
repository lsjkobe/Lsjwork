package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;

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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs;
    }
}
