package com.example.lsj.lsjwork;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsj.lsjwork.test.EmojiEdittext;

/**
 * Created by Administrator on 2016/3/30.
 */
public class EdittextEmoji extends Activity {
    EmojiEdittext mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext_test);
        mEdit = (EmojiEdittext) findViewById(R.id.edit_emoji_edit);
    }
    public void showEdittext(View view){
        SpannableString spannableString = new SpannableString("emoji");
        Drawable drawable = this.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth()/2,drawable.getIntrinsicHeight()/2);
        ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span,0,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int currentPosition = mEdit.getSelectionStart();
        mEdit.getText().insert(currentPosition,spannableString);
        Toast.makeText(this,""+mEdit.getText(),Toast.LENGTH_LONG).show();
    }
}
