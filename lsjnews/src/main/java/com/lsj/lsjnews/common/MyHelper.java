package com.lsj.lsjnews.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyHelper {
    //登录时保存的数据
    public static  int USER_ID =-1;
    public static  String USER_HEAD_IMG = "";
    public static  String USER_NAME = "";
    public static  int USER_FANS_COUNT = 0;
    public static  int USER_FROWARD_COUNT = 0;
    public static  int USER_RELEAS_COUNT = 0;
    public static  int USER_SEXY = 0;
    public static  String USER_STATE_CONTENT = "" ;
    //end

    public final static  String My_News_List = "MyNewsList";

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
    public final static String[] OTHER_USER_MAIN_LIST = new String[]{
            "圈子",
            "相册"
    };
}
