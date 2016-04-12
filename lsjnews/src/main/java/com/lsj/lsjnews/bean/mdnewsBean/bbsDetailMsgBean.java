package com.lsj.lsjnews.bean.mdnewsBean;

import java.util.List;

/**
 * Created by lsj on 2016/4/6.
 */
public class bbsDetailMsgBean extends baseBean{

    private allDatas allDataArray;

    public allDatas getAllDataArray() {
        return allDataArray;
    }

    public void setAllDataArray(allDatas allDataArray) {
        this.allDataArray = allDataArray;
    }

    public static class allDatas{
        private List<commentBean> commentLists;
        private List<starBean> starLists;
        private List<forwardBean> forwardLists;

        public List<forwardBean> getForwardLists() {
            return forwardLists;
        }

        public void setForwardLists(List<forwardBean> forwardLists) {
            this.forwardLists = forwardLists;
        }

        public List<commentBean> getCommentLists() {
            return commentLists;
        }

        public void setCommentLists(List<commentBean> commentLists) {
            this.commentLists = commentLists;
        }

        public List<starBean> getStarLists() {
            return starLists;
        }

        public void setStarLists(List<starBean> starLists) {
            this.starLists = starLists;
        }

        public static class commentBean{
            private int cid;
            private int uid;
            private int mid;
            private String cCreateDate;
            private String content;
            private int cIsImage;
            private String uHeadImg;
            private String uName;
            public String getuName() {
                return uName;
            }

            public void setuName(String uName) {
                this.uName = uName;
            }

            public String getuHeadImg() {
                return uHeadImg;
            }

            public void setuHeadImg(String uHeadImg) {
                this.uHeadImg = uHeadImg;
            }

            public String getcCreateDate() {
                return cCreateDate;
            }

            public void setcCreateDate(String cCreateDate) {
                this.cCreateDate = cCreateDate;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getcIsImage() {
                return cIsImage;
            }

            public void setcIsImage(int cIsImage) {
                this.cIsImage = cIsImage;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }
        }

        public static class starBean{
            private int sid;
            private int uid;
            private int mid;
            private String uHeadImg;
            private String uName;
            public String getuName() {
                return uName;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public void setuName(String uName) {
                this.uName = uName;
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
            public int getMid() {
                return mid;
            }
            public void setMid(int mid) {
                this.mid = mid;
            }
        }

        public static class forwardBean{
            private int uid;
            private String uName;
            private String uHeadImg;

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
        }
    }

}
