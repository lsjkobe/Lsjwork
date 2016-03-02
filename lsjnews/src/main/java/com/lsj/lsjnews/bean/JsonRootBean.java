/**
  * Copyright 2016 aTool.org 
  */
package com.lsj.lsjnews.bean;
import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2016-03-02 18:52:37
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class JsonRootBean implements Serializable{

    private String body;
    private List<String> users;
    @JsonProperty("replyCount")
    private int replycount;
    private List<String> ydbaike;
    private List<String> link;
    private List<String> votes;
    private List<Img> img;
    private String digest;
    @JsonProperty("topiclist_news")
    private List<TopiclistNews> topiclistNews;
    private String dkeys;
    private String ec;
    private List<Topiclist> topiclist;
    private String docid;
    private boolean picnews;
    private String title;
    private String tid;
    private List<Video> video;
    private String template;
    @JsonProperty("threadVote")
    private int threadvote;
    @JsonProperty("threadAgainst")
    private int threadagainst;
    @JsonProperty("boboList")
    private List<String> bobolist;
    @JsonProperty("replyBoard")
    private String replyboard;
    private String source;
    private String voicecomment;
    @JsonProperty("hasNext")
    private boolean hasnext;
    @JsonProperty("relative_sys")
    private List<String> relativeSys;
    private List<String> apps;
    private String ptime;
    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
    public List<String> getUsers() {
        return users;
    }

    public void setReplycount(int replycount) {
        this.replycount = replycount;
    }
    public int getReplycount() {
        return replycount;
    }

    public void setYdbaike(List<String> ydbaike) {
        this.ydbaike = ydbaike;
    }
    public List<String> getYdbaike() {
        return ydbaike;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }
    public List<String> getLink() {
        return link;
    }

    public void setVotes(List<String> votes) {
        this.votes = votes;
    }
    public List<String> getVotes() {
        return votes;
    }

    public void setImg(List<Img> img) {
        this.img = img;
    }
    public List<Img> getImg() {
        return img;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getDigest() {
        return digest;
    }

    public void setTopiclistNews(List<TopiclistNews> topiclistNews) {
        this.topiclistNews = topiclistNews;
    }
    public List<TopiclistNews> getTopiclistNews() {
        return topiclistNews;
    }

    public void setDkeys(String dkeys) {
        this.dkeys = dkeys;
    }
    public String getDkeys() {
        return dkeys;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }
    public String getEc() {
        return ec;
    }

    public void setTopiclist(List<Topiclist> topiclist) {
        this.topiclist = topiclist;
    }
    public List<Topiclist> getTopiclist() {
        return topiclist;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getDocid() {
        return docid;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }
    public boolean getPicnews() {
        return picnews;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
    public String getTid() {
        return tid;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }
    public List<Video> getVideo() {
        return video;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    public String getTemplate() {
        return template;
    }

    public void setThreadvote(int threadvote) {
        this.threadvote = threadvote;
    }
    public int getThreadvote() {
        return threadvote;
    }

    public void setThreadagainst(int threadagainst) {
        this.threadagainst = threadagainst;
    }
    public int getThreadagainst() {
        return threadagainst;
    }

    public void setBobolist(List<String> bobolist) {
        this.bobolist = bobolist;
    }
    public List<String> getBobolist() {
        return bobolist;
    }

    public void setReplyboard(String replyboard) {
        this.replyboard = replyboard;
    }
    public String getReplyboard() {
        return replyboard;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setVoicecomment(String voicecomment) {
        this.voicecomment = voicecomment;
    }
    public String getVoicecomment() {
        return voicecomment;
    }

    public void setHasnext(boolean hasnext) {
        this.hasnext = hasnext;
    }
    public boolean getHasnext() {
        return hasnext;
    }

    public void setRelativeSys(List<String> relativeSys) {
        this.relativeSys = relativeSys;
    }
    public List<String> getRelativeSys() {
        return relativeSys;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }
    public List<String> getApps() {
        return apps;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
    public String getPtime() {
        return ptime;
    }
    public static class Video {

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
    public static class Topiclist {

        @JsonProperty("hasCover")
        private boolean hascover;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String cid;
        public void setHascover(boolean hascover) {
            this.hascover = hascover;
        }
        public boolean getHascover() {
            return hascover;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }
        public String getSubnum() {
            return subnum;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
        public String getAlias() {
            return alias;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }
        public String getTname() {
            return tname;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }
        public String getEname() {
            return ename;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
        public String getTid() {
            return tid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
        public String getCid() {
            return cid;
        }

    }
    public static class TopiclistNews {

        @JsonProperty("hasCover")
        private boolean hascover;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String cid;
        public void setHascover(boolean hascover) {
            this.hascover = hascover;
        }
        public boolean getHascover() {
            return hascover;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }
        public String getSubnum() {
            return subnum;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
        public String getAlias() {
            return alias;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }
        public String getTname() {
            return tname;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }
        public String getEname() {
            return ename;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
        public String getTid() {
            return tid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
        public String getCid() {
            return cid;
        }

    }
    public static class Img {

        private String ref;
        private String pixel;
        private String alt;
        private String src;
        public void setRef(String ref) {
            this.ref = ref;
        }
        public String getRef() {
            return ref;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }
        public String getPixel() {
            return pixel;
        }

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