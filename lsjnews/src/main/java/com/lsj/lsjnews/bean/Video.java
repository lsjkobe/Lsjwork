/**
  * Copyright 2016 aTool.org 
  */
package com.lsj.lsjnews.bean;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2016-03-02 18:52:37
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Video {

    private String commentid;
    private String ref;
    private String topicid;
    private String cover;
    private String alt;
    @JsonProperty("url_mp4")
    private String urlMp4;
    private String broadcast;
    private String videosource;
    private String commentboard;
    private String appurl;
    @JsonProperty("url_m3u8")
    private String urlM3u8;
    private String size;
    public void setCommentid(String commentid) {
         this.commentid = commentid;
     }
     public String getCommentid() {
         return commentid;
     }

    public void setRef(String ref) {
         this.ref = ref;
     }
     public String getRef() {
         return ref;
     }

    public void setTopicid(String topicid) {
         this.topicid = topicid;
     }
     public String getTopicid() {
         return topicid;
     }

    public void setCover(String cover) {
         this.cover = cover;
     }
     public String getCover() {
         return cover;
     }

    public void setAlt(String alt) {
         this.alt = alt;
     }
     public String getAlt() {
         return alt;
     }

    public void setUrlMp4(String urlMp4) {
         this.urlMp4 = urlMp4;
     }
     public String getUrlMp4() {
         return urlMp4;
     }

    public void setBroadcast(String broadcast) {
         this.broadcast = broadcast;
     }
     public String getBroadcast() {
         return broadcast;
     }

    public void setVideosource(String videosource) {
         this.videosource = videosource;
     }
     public String getVideosource() {
         return videosource;
     }

    public void setCommentboard(String commentboard) {
         this.commentboard = commentboard;
     }
     public String getCommentboard() {
         return commentboard;
     }

    public void setAppurl(String appurl) {
         this.appurl = appurl;
     }
     public String getAppurl() {
         return appurl;
     }

    public void setUrlM3u8(String urlM3u8) {
         this.urlM3u8 = urlM3u8;
     }
     public String getUrlM3u8() {
         return urlM3u8;
     }

    public void setSize(String size) {
         this.size = size;
     }
     public String getSize() {
         return size;
     }

}