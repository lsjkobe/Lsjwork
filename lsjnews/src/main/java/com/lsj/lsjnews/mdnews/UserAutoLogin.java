package com.lsj.lsjnews.mdnews;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.base.BaseManager;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.CacheUtils;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.userBean;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;

import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/3/24.
 */
public class UserAutoLogin {
    private static UserAutoLogin instance = null;
    public static UserAutoLogin getInstance(){
        if(instance == null){
            instance = new UserAutoLogin();
        }
        return instance;
    }

    public void autoLogin(final Context context){
        RequestParams params = new RequestParams(Conts.POST_USER_LOGIN);
        params.addBodyParameter("phone", CacheUtils.getLoginCache(context,"phone"));
        params.addBodyParameter("password", MD5.md5(CacheUtils.getLoginCache(context,"password")));
        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                userBean mUserBean = JSON.parseObject(s,userBean.class);
                switch (mUserBean.getResultCode()){
                    case 1:
//                        MyToast.showToast(context, "登录成功");
                        MyHelper.USER_ID = mUserBean.getUid();
                        MyHelper.USER_HEAD_IMG = mUserBean.getuImg();
                        MyHelper.USER_NAME = mUserBean.getuName();
                        MyHelper.USER_SEXY = mUserBean.getuSexy();
                        MyHelper.USER_STATE_CONTENT = mUserBean.getuStateContent();
                        MyHelper.USER_FANS_COUNT = mUserBean.getuFansCount();
                        MyHelper.USER_RELEAS_COUNT = mUserBean.getuReleasCount();
                        MyHelper.USER_FROWARD_COUNT = mUserBean.getuFollowCount();
                        break;
                    case 0:
//                        MyToast.showToast(context, "发生错误");
                        break;
                }
            }
        });
    }
}
