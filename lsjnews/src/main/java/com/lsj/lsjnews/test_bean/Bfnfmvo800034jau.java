/**
  * Copyright 2016 aTool.org 
  */
package com.lsj.lsjnews.test_bean;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2016-03-02 23:5:9
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Bfnfmvo800034jau {

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

}