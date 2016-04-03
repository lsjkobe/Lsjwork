package com.lsj.lsjnews.bean.mdnewsBean;

/**
 * Created by Administrator on 2016/3/20.
 */
public class userBean extends baseBean {
    private int uid;
    private String uName;
    private String uImg;
    private int uFansCount;
    private int uFollowCount;
    private int uReleasCount;
    private int uSexy;
    private String uStateContent;

    public String getuStateContent() {
        return uStateContent;
    }

    public void setuStateContent(String uStateContent) {
        this.uStateContent = uStateContent;
    }

    public int getuFansCount() {
        return uFansCount;
    }

    public void setuFansCount(int uFansCount) {
        this.uFansCount = uFansCount;
    }

    public int getuFollowCount() {
        return uFollowCount;
    }

    public void setuFollowCount(int uFollowCount) {
        this.uFollowCount = uFollowCount;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getuReleasCount() {
        return uReleasCount;
    }

    public void setuReleasCount(int uReleasCount) {
        this.uReleasCount = uReleasCount;
    }

    public int getuSexy() {
        return uSexy;
    }

    public void setuSexy(int uSexy) {
        this.uSexy = uSexy;
    }
}
