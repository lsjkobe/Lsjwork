package com.lsj.lsjnews.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lsj on 2016/2/29.
 */
public class NewsApi implements Serializable{

    private int showapi_res_code;

    private String showapi_res_error;

    private Showapi_res_body showapi_res_body;

    public Showapi_res_body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Showapi_res_body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public static class Sentiment_tag {
        private String count;

        private String dim;

        private String id;

        private int isbooked;

        private String ishot;

        private String name;

        private String type;

        public void setCount(String count){
            this.count = count;
        }
        public String getCount(){
            return this.count;
        }
        public void setDim(String dim){
            this.dim = dim;
        }
        public String getDim(){
            return this.dim;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setIsbooked(int isbooked){
            this.isbooked = isbooked;
        }
        public int getIsbooked(){
            return this.isbooked;
        }
        public void setIshot(String ishot){
            this.ishot = ishot;
        }
        public String getIshot(){
            return this.ishot;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }

    }

    public static class Own {
        private String name;

        private int type;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setType(int type){
            this.type = type;
        }
        public int getType(){
            return this.type;
        }

    }
    public static class Tags {
        private List<Own> own ;
        private List<String> tag ;
        public List<Own> getOwn() {
            return own;
        }
        public void setOwn(List<Own> own) {
            this.own = own;
        }

        public List<String> getTag() {
            return tag;
        }

        public void setTag(List<String> tag) {
            this.tag = tag;
        }
    }
    public static class Imageurls {
        private int height;

        private String url;

        private int width;

        public void setHeight(int height){
            this.height = height;
        }
        public int getHeight(){
            return this.height;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
        public void setWidth(int width){
            this.width = width;
        }
        public int getWidth(){
            return this.width;
        }

    }
    public static class Contentlist {
        private String channelId;

        private String channelName;

        private String desc;

        private List<Imageurls> imageurls ;

        private String link;

        private String long_desc;

        private String nid;

        private String pubDate;

        private int sentiment_display;

        private Sentiment_tag sentiment_tag;

        private String source;

        private Tags tags;

        private String title;

        public void setChannelId(String channelId){
            this.channelId = channelId;
        }
        public String getChannelId(){
            return this.channelId;
        }
        public void setChannelName(String channelName){
            this.channelName = channelName;
        }
        public String getChannelName(){
            return this.channelName;
        }
        public void setDesc(String desc){
            this.desc = desc;
        }
        public String getDesc(){
            return this.desc;
        }
        public void setImageurls(List<Imageurls> imageurls){
            this.imageurls = imageurls;
        }
        public List<Imageurls> getImageurls(){
            return this.imageurls;
        }
        public void setLink(String link){
            this.link = link;
        }
        public String getLink(){
            return this.link;
        }
        public void setLong_desc(String long_desc){
            this.long_desc = long_desc;
        }
        public String getLong_desc(){
            return this.long_desc;
        }
        public void setNid(String nid){
            this.nid = nid;
        }
        public String getNid(){
            return this.nid;
        }
        public void setPubDate(String pubDate){
            this.pubDate = pubDate;
        }
        public String getPubDate(){
            return this.pubDate;
        }
        public void setSentiment_display(int sentiment_display){
            this.sentiment_display = sentiment_display;
        }
        public int getSentiment_display(){
            return this.sentiment_display;
        }
        public void setSentiment_tag(Sentiment_tag sentiment_tag){
            this.sentiment_tag = sentiment_tag;
        }
        public Sentiment_tag getSentiment_tag(){
            return this.sentiment_tag;
        }
        public void setSource(String source){
            this.source = source;
        }
        public String getSource(){
            return this.source;
        }
        public void setTags(Tags tags){
            this.tags = tags;
        }
        public Tags getTags(){
            return this.tags;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }

    }
    public static class Pagebean {
        private int allNum;

        private int allPages;

        private List<Contentlist> contentlist ;

        private int currentPage;

        private int maxResult;

        public void setAllNum(int allNum){
            this.allNum = allNum;
        }
        public int getAllNum(){
            return this.allNum;
        }
        public void setAllPages(int allPages){
            this.allPages = allPages;
        }
        public int getAllPages(){
            return this.allPages;
        }
        public void setContentlist(List<Contentlist> contentlist){
            this.contentlist = contentlist;
        }
        public List<Contentlist> getContentlist(){
            return this.contentlist;
        }
        public void setCurrentPage(int currentPage){
            this.currentPage = currentPage;
        }
        public int getCurrentPage(){
            return this.currentPage;
        }
        public void setMaxResult(int maxResult){
            this.maxResult = maxResult;
        }
        public int getMaxResult(){
            return this.maxResult;
        }

    }

    public static class Showapi_res_body {
        private Pagebean pagebean;

        private int ret_code;

        public void setPagebean(Pagebean pagebean){
            this.pagebean = pagebean;
        }
        public Pagebean getPagebean(){
            return this.pagebean;
        }
        public void setRet_code(int ret_code){
            this.ret_code = ret_code;
        }
        public int getRet_code(){
            return this.ret_code;
        }

    }

}
