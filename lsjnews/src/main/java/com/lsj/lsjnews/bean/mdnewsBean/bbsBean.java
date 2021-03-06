package com.lsj.lsjnews.bean.mdnewsBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lsj on 2016/3/19.
 */
public class bbsBean extends baseBean{
    private List<Lists> lists ;
    private int pageCount;
    public void setLists(List<Lists> lists){
        this.lists = lists;
    }
    public List<Lists> getLists(){
        return this.lists;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public static class Lists implements Serializable {
        private int uid;
        private int mid;
        private String uHeadImg;
        private String uName;
        private String content;
        private String date;
        private List<imglists> imglists ;
        private String location;
        private int is_star;
        private int is_collect;
        private int s_is_star; //如果是转发，点进原圈子后用户是否赞
        private int mType;
        private String sourceContent;
        private String sName;
        private int suid; //原圈子用户id
        private int sid; // 原圈子id
        private int mCommentCount;
        private int mForwardCount;
        private int mStar;

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getmCommentCount() {
            return mCommentCount;
        }

        public void setmCommentCount(int mCommentCount) {
            this.mCommentCount = mCommentCount;
        }

        public int getmForwardCount() {
            return mForwardCount;
        }

        public void setmForwardCount(int mForwardCount) {
            this.mForwardCount = mForwardCount;
        }

        public int getmStar() {
            return mStar;
        }

        public void setmStar(int mStar) {
            this.mStar = mStar;
        }

        public int getS_is_star() {
            return s_is_star;
        }

        public void setS_is_star(int s_is_star) {
            this.s_is_star = s_is_star;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getSuid() {
            return suid;
        }

        public void setSuid(int suid) {
            this.suid = suid;
        }

        public String getsName() {
            return sName;
        }
        public void setsName(String sName) {
            this.sName = sName;
        }
        public String getSourceContent() {
            return sourceContent;
        }

        public void setSourceContent(String sourceContent) {
            this.sourceContent = sourceContent;
        }

        public int getmType() {
            return mType;
        }

        public void setmType(int mType) {
            this.mType = mType;
        }

        public int getIs_star() {
            return is_star;
        }

        public void setIs_star(int is_star) {
            this.is_star = is_star;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getuHeadImg() {
            return uHeadImg;
        }

        public void setuHeadImg(String uHeadImg) {
            this.uHeadImg = uHeadImg;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setDate(String date){
            this.date = date;
        }
        public String getDate(){
            return this.date;
        }
        public void setImglists(List<imglists> imglists){
            this.imglists = imglists;
        }
        public List<imglists> getImglists(){
            return this.imglists;
        }

        public static class imglists implements Serializable{
            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
