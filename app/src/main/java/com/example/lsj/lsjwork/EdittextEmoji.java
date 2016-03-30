package com.example.lsj.lsjwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/30.
 */
public class EdittextEmoji extends Activity {
    EditText mEditEmoji;
    TextView mTxtEmoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext_test);
        mEditEmoji = (EditText) findViewById(R.id.edit_emoji);
        mTxtEmoji = (TextView) findViewById(R.id.txt_emoji);
    }
    public void showEdittext(View view){
        Toast.makeText(this,"---"+mEditEmoji.getText().toString(),Toast.LENGTH_LONG).show();
        mTxtEmoji.setText(mEditEmoji.getText().toString());
    }
}
