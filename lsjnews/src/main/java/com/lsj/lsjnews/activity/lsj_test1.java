package com.lsj.lsjnews.activity;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.request.Request;
import com.example.lsj.httplibrary.base.BaseActivity;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.LsjBaseCallBack;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by lsj on 2016/3/7.
 */
public class lsj_test1 extends BaseActivity{

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        showTopView(false);
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/configs/mysql.func.php");
        params.addBodyParameter("username", "李上健");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
                MyToast.showToast(mContext,":"+s);
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
        return R.layout.lsj_test1;
    }
}
