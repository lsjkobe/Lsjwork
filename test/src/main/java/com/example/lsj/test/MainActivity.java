package com.example.lsj.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

//    private ImageView mImg1,mImg2,mImg3,mImg4,mImg5,mImg6,mImg7,mImg8,mImg9;

    private List<Bitmap> imageBitmap = new ArrayList<>();
    private RecyclerView mRecyImg;
    @Override
    protected void initView() {
        super.initView();
        mRecyImg = (RecyclerView) findViewById(R.id.recy_nine_img_show);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData() {
        showTopView(true);
        initRecycler();
//        imageBitmap = ImageUtil.getImageList(getResources());
//        Drawable drawable1 =new BitmapDrawable(imageBitmap.get(0));
//        mImg1.setBackground(drawable1);

    }

    private void initRecycler() {
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i = 0; i<9; i++){
            imageBitmap.get(i).recycle();
        }
        ImageUtil.cleanBitmap();
        System.gc();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
