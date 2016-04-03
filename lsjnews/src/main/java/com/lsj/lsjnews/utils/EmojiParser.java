package com.lsj.lsjnews.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.lsj.lsjnews.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/1.
 */
public class EmojiParser {

    private static EmojiParser instance;
    public static EmojiParser getInstance(Context context){
        if(instance == null){
            //线程安全
            synchronized (EmojiParser.class){
                if(instance == null){
                    instance = new EmojiParser(context);
                }
            }
        }
        return instance;
    }
    public static  final int[]DEFAULT_EMOJI_RES_IDS ={
            R.mipmap.smiles_00,R.mipmap.smiles_01,R.mipmap.smiles_02,R.mipmap.smiles_03,R.mipmap.smiles_04,R.mipmap.smiles_05,R.mipmap.smiles_06,R.mipmap.smiles_07,R.mipmap.smiles_08,
            R.mipmap.smiles_09,R.mipmap.smiles_10,R.mipmap.smiles_11,R.mipmap.smiles_12,R.mipmap.smiles_13,R.mipmap.smiles_14,R.mipmap.smiles_15,R.mipmap.smiles_16,R.mipmap.smiles_17,
            R.mipmap.smiles_18,R.mipmap.smiles_19,R.mipmap.smiles_20,R.mipmap.smiles_21,R.mipmap.smiles_22,R.mipmap.smiles_23,R.mipmap.smiles_24,R.mipmap.smiles_25,R.mipmap.smiles_26,
            R.mipmap.smiles_27,R.mipmap.smiles_28,R.mipmap.smiles_29,R.mipmap.smiles_30,R.mipmap.smiles_31,R.mipmap.smiles_32,R.mipmap.smiles_33,R.mipmap.smiles_34,R.mipmap.smiles_35,
            R.mipmap.smiles_36,R.mipmap.smiles_37,R.mipmap.smiles_38,R.mipmap.smiles_39,R.mipmap.smiles_40,R.mipmap.smiles_41,R.mipmap.smiles_42,R.mipmap.smiles_43,R.mipmap.smiles_44
    };
    public static final String[] strEmojis = {
            "[smiles_00]","[smiles_01]","[smiles_02]","[smiles_03]","[smiles_04]","[smiles_05]","[smiles_06]","[smiles_07]","[smiles_08]",
            "[smiles_9]","[smiles_10]","[smiles_11]","[smiles_12]","[smiles_13]","[smiles_14]","[smiles_15]","[smiles_16]","[smiles_17]",
            "[smiles_18]","[smiles_19]","[smiles_20]","[smiles_21]","[smiles_22]","[smiles_23]","[smiles_24]","[smiles_25]","[smiles_26]",
            "[smiles_27]","[smiles_28]","[smiles_29]","[smiles_30]","[smiles_31]","[smiles_32]","[smiles_33]","[smiles_34]","[smiles_35]",
            "[smiles_36]","[smiles_37]","[smiles_38]","[smiles_39]","[smiles_40]","[smiles_41]","[smiles_42]","[smiles_43]","[smiles_44]"
    };

    private Context context;
    private String[] mSmileyTexts;
    private Pattern mPattern;
    private HashMap<String, Integer> mSmileyToRes;
    public EmojiParser(Context mcontext) {
        super();
        this.context = mcontext;
        this.mSmileyTexts =  strEmojis;
        this.mPattern = buildPattern();
        this.mSmileyToRes = buildSmileyToRes();
    }

    private HashMap<String,Integer> buildSmileyToRes() {
        HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(mSmileyTexts.length);
        for(int i=0;i<mSmileyTexts.length;i++){
            smileyToRes.put(mSmileyTexts[i], DEFAULT_EMOJI_RES_IDS[i]);
        }
        return smileyToRes;
    }

    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);
        patternString.append('(');
        for (String s : mSmileyTexts) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1, patternString.length(), ")");

        return Pattern.compile(patternString.toString());
    }
    //根据文本替换成图片(textview)
    public  CharSequence replace(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            int resId = mSmileyToRes.get(matcher.group());
            Drawable drawable = context.getResources().getDrawable(resId);
            drawable.setBounds(0,0,(int)(drawable.getIntrinsicWidth()*1.5),(int)(drawable.getIntrinsicHeight()*1.5));
            ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
//            builder.setSpan(new ImageSpan(context, resId),matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(span,matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
    public CharSequence edittextReplace(int position,float textSize){
        SpannableString spannableString = new SpannableString(strEmojis[position]);
        Drawable drawable = context.getResources().getDrawable(DEFAULT_EMOJI_RES_IDS[position]);
        drawable.setBounds(0,0,(int)(textSize*1.3),(int)(textSize*1.3));
        ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span,0,strEmojis[position].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
