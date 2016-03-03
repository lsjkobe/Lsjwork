/**
  * Copyright 2016 aTool.org 
  */
package com.lsj.lsjnews.test_bean;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2016-03-02 23:5:9
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class JsonRootBean implements Serializable{

    @JsonProperty("BFNFMVO800034JAU")
    private testBean testbean;
    public void setTestbean(testBean testbean) {
         this.testbean = testbean;
     }
     public testBean getTestbean() {
         return testbean;
     }

}