package com.example.lsj.test;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lsj.httplibrary.base.BaseActivity;
import com.example.lsj.httplibrary.callback.ApiResponse;
import com.example.lsj.httplibrary.callback.BaseCallBack;
import com.example.lsj.httplibrary.callback.FailMessg;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.utils.SystemBarTintManager;

import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TextView mTxtTTest;

    @Override
    protected void initData() {
        showTopView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }


        baseManager.getRightImage().setImageResource(R.drawable.ic_img_back);
        baseManager.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showToast(mContext, "nihao");
            }
        });
        baseManager.setTitle("niahoya");
        mTxtTTest = (TextView) findViewById(R.id.txt_test);
        RequestParams mParams = new RequestParams("http://www.my00.cn/linglingwang/index.php/Mobile/Type/getType");
//        mParams.addBodyParameter("phone", "13726226699");
//        mParams.addBodyParameter("password", "111111");
        baseManager.http2Post(mParams, ApiResponse.class, new BaseCallBack() {
            @Override
            public void onSucces(Object result) {
                ApiResponse mApiResponse = (ApiResponse) result;
//                mTxtTTest.setText("-----" + mApiResponse.getResult());
                DbCookieStore instance = DbCookieStore.INSTANCE;
                List cookies = instance.getCookies();
                mTxtTTest.setText("-----" + cookies.toString());
            }

            @Override
            public void onResult(String result) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFail(Context context, FailMessg result) {

            }
        });
//        x.http().get(mParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                mTxtTTest.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//
//            }
//
//            @Override
//            public void onFinished() {
//                baseManager.hideProgressbar();
//            }
//        });
    }


    /**
     * 不适用的话，可以重写
     * 头部
     */
    protected  void initTop() {
        // TODO Auto-generated method stub
        //返回
        baseManager.getLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    private void setTranslucentStatus(boolean on) {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个颜色给系统栏
        tintManager.setTintColor(getResources().getColor(R.color.title_bg));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
