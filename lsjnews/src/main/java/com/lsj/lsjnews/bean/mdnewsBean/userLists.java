package com.lsj.lsjnews.bean.mdnewsBean;

import java.util.List;

/**
 * Created by lsj on 2016/5/4.
 */
public class userLists extends baseBean{
   private List<user> userLists;

    public List<user> getUserLists() {
        return userLists;
    }

    public void setUserLists(List<user> userLists) {
        this.userLists = userLists;
    }

    public static class user{
       private int uid;
       private String uName;
       private String uImg;
       private int uFansCount;

       public int getuFansCount() {
           return uFansCount;
       }

       public void setuFansCount(int uFansCount) {
           this.uFansCount = uFansCount;
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
   }
}
