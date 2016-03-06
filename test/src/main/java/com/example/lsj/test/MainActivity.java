package com.example.lsj.test;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.lsj.httplibrary.base.BaseActivity;
public class MainActivity extends BaseActivity {

    private Button mBtnNine;
    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mBtnNine = (Button) findViewById(R.id.btn_nine_img_show);
    }

    @Override
    protected void initData() {
        showTopView(false);
        mBtnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, NineImgShow.class);
                startActivity(mIntent);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
