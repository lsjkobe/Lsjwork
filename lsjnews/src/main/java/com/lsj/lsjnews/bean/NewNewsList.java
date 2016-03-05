package com.lsj.lsjnews.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lsj on 2016/3/3.
 */

public class NewNewsList implements Serializable{
    private List<LsjNewsBean> MyNewsList;

    public List<LsjNewsBean> getMyNewsList() {
        return MyNewsList;
    }

    public void setMyNewsList(List<LsjNewsBean> myNewsList) {
        MyNewsList = myNewsList;
    }
}
