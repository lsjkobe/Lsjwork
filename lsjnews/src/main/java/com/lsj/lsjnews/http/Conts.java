package com.lsj.lsjnews.http;

/**
 * Created by Administrator on 2016/2/27.
 */
public class Conts {

    public final static String BAIDU_LOCATION_API = "http://api.map.baidu.com/geocoder?output=json&location=";
    public final static String BAIDU_LOCATION_KEY = "&key=nOUeWMWpZPfHYGkoHzGp63hL";

    public final static String API_HTTPS_NEWS = "http://apis.baidu.com/txapi/social/social";
    public final static String API_HTTPS_NEWS_1 = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";

    public final static String HTTP_IP = "http://182.254.145.222";
    public final static String HTTP_HOST = HTTP_IP+"/lsj/mdnews/user/";
    //判断是否登录
    public final static String isLogin = HTTP_HOST+"userIsLogin.php";
    //发表微博
    public final static String POST_WRITE_BBS = HTTP_HOST+"insertBBSData.php";
}
