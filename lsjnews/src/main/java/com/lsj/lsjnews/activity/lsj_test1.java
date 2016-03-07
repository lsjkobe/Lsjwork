package com.lsj.lsjnews.activity;

import com.example.lsj.httplibrary.base.BaseActivity;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.view.LsjLoadingView;

/**
 * Created by lsj on 2016/3/7.
 */
public class lsj_test1 extends BaseActivity{
    LsjLoadingView mLoading;

    @Override
    protected void initView() {
        super.initView();
        mLoading = (LsjLoadingView) findViewById(R.id.loading);
    }

    @Override
    protected void initData() {
        showTopView(false);
        mLoading.startLoadingAnim();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.lsj_test1;
    }
}
