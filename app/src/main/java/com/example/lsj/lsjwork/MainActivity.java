package com.example.lsj.lsjwork;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.x;

import java.io.IOException;

import myhttp.callback.NewCallback;
import myhttp.parser.Parser;
import myhttp.parser.StringParser;

public class MainActivity extends AppCompatActivity {

    private String mString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        StringParser parser=new StringParser();
        Request mRequest = new Request.Builder().url("http://www.my00.cn/linglingwang/index.php/Mobile/Type/getType").build();
        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new NewCallback<String>(parser){
            @Override
            public void onResponse(String s) {
                mString = s;
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Toast.makeText(MainActivity.this, mString, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onbutton(View view){
        handler.sendEmptyMessage(1);
    }
}
