package com.example.lsj.lsjwork.test;

/**
 * Created by Administrator on 2016/4/1.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class EmojiEdittext extends EditText {
    public EmojiEdittext(Context context) {
        super(context);
    }

    public EmojiEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void insertIcon(int id) {
        // SpannableString连续的字符串，长度不可变，同时可以附加一些object;可变的话使用SpannableStringBuilder，参考sdk文档
        SpannableStringBuilder ss = new SpannableStringBuilder(getText().toString()+ "[smile]");
        // 得到要显示图片的资源
        Drawable d = getResources().getDrawable(id);
        // 设置高度
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        // 跨度底部应与周围文本的基线对齐
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        // 附加图片
        ss.setSpan(span, getText().length(),
                getText().length() + "[smile]".length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(ss);
    }
}
