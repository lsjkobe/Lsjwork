/**
  * Copyright 2016 aTool.org 
  */
package com.lsj.lsjnews.test_bean;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2016-03-02 23:5:9
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Topiclist {

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