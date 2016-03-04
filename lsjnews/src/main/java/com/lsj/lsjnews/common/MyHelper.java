package com.lsj.lsjnews.common;

import com.lsj.lsjnews.http.MyApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyHelper {
    public final static String NEWS_API_KEYS = "9877658e3bf15280c371ded239997299";

    public final static String NEWS_API_KEYS_JUHE = "12b0c8bea1ed31bfbad8d464513ba149";

    public final static String apiName = "BFNFMVO800034JAU";

    //新闻的类型
    public static final int HeadLine_news_Type = 0;
    public static final int Sport_News_Type = 1;
    public static final int Social_News_Type = 2;
    public static final int NBA_News_Type = 3;
    public static final int FootBall_News_Type = 4;
    public static final int News_Type_Count = 5;
    public final static Map<Integer, String> mTypeMap = new HashMap<>();
    public final static String[] News_Name_List = new String[]{
            "头条",
            "体育",
            "社会",
            "NBA",
            "足球"
    };
}
