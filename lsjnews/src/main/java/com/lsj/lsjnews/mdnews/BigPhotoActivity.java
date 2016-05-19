package com.lsj.lsjnews.mdnews;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;

/**
 * Created by Administrator on 2016/5/19.
 */
public class BigPhotoActivity extends MyBaseActivity{
    private ImageView mImgBigPhoto;
    private String mStrImgPath;
    @Override
    protected void initView() {
        super.initView();
        mImgBigPhoto = (ImageView) findViewById(R.id.img_big_photo);
        mImgBigPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    @Override
    protected void initData() {
        mStrImgPath = getIntent().getStringExtra("path");
        Glide.with(mContext).load(mStrImgPath).into(mImgBigPhoto);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_photo;
    }
}
