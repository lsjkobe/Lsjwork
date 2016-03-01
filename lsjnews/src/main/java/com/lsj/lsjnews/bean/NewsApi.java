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

//    public static class Sentiment_tag {
//        private String count;
//        private String dim;
//        private String id;
//        private int isbooked;
//        private String ishot;
//        private String name;
//        private String type;
//        public String getDim() {
//            return dim;
//        }
//        public void setDim(String dim) {
//            this.dim = dim;
//        }
//
//        public String getCount() {
//            return count;
//        }
//
//        public void setCount(String count) {
//            this.count = count;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public int getIsbooked() {
//            return isbooked;
//        }
//
//        public void setIsbooked(int isbooked) {
//            this.isbooked = isbooked;
//        }
//
//        public String getIshot() {
//            return ishot;
//        }
//
//        public void setIshot(String ishot) {
//            this.ishot = ishot;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//    }

//    public static class Own {
//        private String name;
//        private int type;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//    }
//    public static class Tag {
//    }
//    public static class Tags {
//        private List<Own> own ;
//        private List<Tag> tag ;
//
//        public List<Tag> getTag() {
//            return tag;
//        }
//
//        public void setTag(List<Tag> tag) {
//            this.tag = tag;
//        }
//
//        public List<Own> getOwn() {
//            return own;
//        }
//
//        public void setOwn(List<Own> own) {
//            this.own = own;
//        }
//    }
    public static class Imageurls {
        private int height;
        private String url;
        private int width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
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
//        private Sentiment_tag sentiment_tag;
        private String source;
//        private Tags tags;
        private String title;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<Imageurls> getImageurls() {
            return imageurls;
        }

        public void setImageurls(List<Imageurls> imageurls) {
            this.imageurls = imageurls;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getLong_desc() {
            return long_desc;
        }

        public void setLong_desc(String long_desc) {
            this.long_desc = long_desc;
        }

        public int getSentiment_display() {
            return sentiment_display;
        }

        public void setSentiment_display(int sentiment_display) {
            this.sentiment_display = sentiment_display;
        }

//        public Sentiment_tag getSentiment_tag() {
//            return sentiment_tag;
//        }
//
//        public void setSentiment_tag(Sentiment_tag sentiment_tag) {
//            this.sentiment_tag = sentiment_tag;
//        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

//        public Tags getTags() {
//            return tags;
//        }
//
//        public void setTags(Tags tags) {
//            this.tags = tags;
//        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    public static class Pagebean {
        private int allNum;
        private int allPages;
        private List<Contentlist> contentlist ;
        private int currentPage;
        private int maxResult;

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<Contentlist> getContentlist() {
            return contentlist;
        }

        public void setContentlist(List<Contentlist> contentlist) {
            this.contentlist = contentlist;
        }

        public int getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }
    }
    public static class Showapi_res_body {
        private Pagebean pagebean;
        private int ret_code;

        public Pagebean getPagebean() {
            return pagebean;
        }

        public void setPagebean(Pagebean pagebean) {
            this.pagebean = pagebean;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }
    }

}
