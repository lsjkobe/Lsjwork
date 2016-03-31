package com.lsj.lsjnews.bean.mdnewsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/19.
 */
public class bbsBean extends baseBean{
    private List<Lists> lists ;

    public void setLists(List<Lists> lists){
        this.lists = lists;
    }
    public List<Lists> getLists(){
        return this.lists;
    }
    public static class Lists {
        private int uid;
        private int mid;
        private String uHeadImg;
        private String uName;
        private String content;
        private String date;
        private List<imglists> imglists ;
        private String location;
        private int is_star;

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

        public static class imglists{
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
