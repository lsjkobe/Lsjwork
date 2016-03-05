package com.lsj.lsjnews.bean;

import java.util.List;

/**
 * Created by lsj on 2016/3/3.
 */
public class LsjNewsBean {
    private String postid;

    private String digest;

    private String docid;

    private String title;

    private List<Imgextra> imgextra ;

    private String imgsrc;

    private String ptime;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<Imgextra> imgextra) {
        this.imgextra = imgextra;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
    public static class Imgextra {
        private String imgsrc;

        public void setImgsrc(String imgsrc){
            this.imgsrc = imgsrc;
        }
        public String getImgsrc(){
            return this.imgsrc;
        }

    }
}
