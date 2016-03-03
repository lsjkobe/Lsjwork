package com.lsj.lsjnews.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Le on 2016/3/2.
 */
public class LsjNewsDetail implements Serializable {

    private String title;
    private List<Video> video;
    private String body;
    private String source;
    private String ptime;
    private List<Img> img;

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
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

    public List<Img> getImg() {
        return img;
    }

    public void setImg(List<Img> img) {
        this.img = img;
    }

    public List<Video> getVideo() {
        return video;
    }
    public void setVideo(List<Video> video) {
        this.video = video;
    }
    public static class Video {
        private String cover;
        private String alt;
        private String url_mp4;
        private String videosource;
        public String getAlt() {
            return alt;
        }
        public void setAlt(String alt) {
            this.alt = alt;
        }
        public String getVideosource() {
            return videosource;
        }
        public void setVideosource(String videosource) {
            this.videosource = videosource;
        }
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
    }
    public static class Img {
        private String alt;
        private String src;
        public void setAlt(String alt) {
            this.alt = alt;
        }
        public String getAlt() {
            return alt;
        }
        public void setSrc(String src) {
            this.src = src;
        }
        public String getSrc() {
            return src;
        }

    }
}
