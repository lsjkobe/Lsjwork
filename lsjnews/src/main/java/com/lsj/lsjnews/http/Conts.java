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
    //获取微博
    public final static String GET_BBS = HTTP_HOST+"getBBSDate.php";
    //转发微博
    public final static String POST_FORWARD_BBS = HTTP_HOST+"insertForwardBBSData.php";
    //登录
    public final static String POST_USER_LOGIN = HTTP_HOST+"user_login.php";
    //退出登录
    public final static String POST_USER_LOGOUT = HTTP_HOST+"user_logout.php";
    //获取圈子评论
    public final static String GET_BBS_COMMENTS = HTTP_HOST+"getBBSComment.php";
    //圈子评论
    public final static String POST_BBS_COMMENTS = HTTP_HOST+"CommentBBS.php";

    //获取其它用户资料
    public final static String GET_OTHER_USER_MSG = HTTP_HOST+"getOtherUserMsg.php";
    //获取其它用户圈子
    public final static String GET_OTHER_USER_BBS = HTTP_HOST+"getOtherUserBBS.php";
    //关注或取消关注
    public final static String GET_FOLLOW_OR_CANCEL = HTTP_HOST+"clickFollowOrCancel.php";
    //获取圈子图片
    public final static String GET_USER_BBS_PHOTO = HTTP_HOST+"getUserBBSPhoto.php";
    //查找用户
    public final static String GET_SEARCH_USER = HTTP_HOST+"searchUser.php";
    //查找圈子
    public final static String GET_SEARCH_BBS = HTTP_HOST+"searchBBS.php";
    //点赞
    public final static String GET_USER_CLICK_STAR = HTTP_HOST+"clickStarBBS.php";
    //获取粉丝列表
    public final static String GET_USER_FANS = HTTP_HOST+"getUserFans.php";
    //获取我的收藏
    public final static String GET_USER_COLLECT_BBS = HTTP_HOST+"getUserCollectBBS.php";
    //收藏
    public final static String POST_BBS_COLLECT = HTTP_HOST+"BBSCollect.php";
    //忘记密码
    public final static String POST_FORGET_PASSWORD = HTTP_HOST+"user_forget_password.php";
}
