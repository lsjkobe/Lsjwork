package com.lsj.lsjnews.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Le on 2016/3/2.
 */
public class LsjNewsDetail implements Serializable{
    private static final long serialVersionUID = -7060210544600464481L;

    /**
     * docid
     */
    private String docid;
    /**
     * title
     */
    private String title;
    /**
     * source
     */
    private String source;
    /**
     * body
     */
    private String body;
    /**
     * ptime
     */
    private String ptime;
    /**
     * cover
     */
    private String cover;
    /**
     * url_mp4
     */
    private String url_mp4;
    /**
     * 图片列表
     */
    private List<String> imgList;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl_mp4() {
        return url_mp4;
    }

    public void setUrl_mp4(String url_mp4) {
        this.url_mp4 = url_mp4;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
