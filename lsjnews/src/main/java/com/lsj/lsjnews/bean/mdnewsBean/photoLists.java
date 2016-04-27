package com.lsj.lsjnews.bean.mdnewsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class photoLists extends baseBean{

    private List<photoBean> photoLists;
    private int pageCount;

    public List<photoBean> getPhotoLists() {
        return photoLists;
    }

    public void setPhotoLists(List<photoBean> photoLists) {
        this.photoLists = photoLists;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public static class photoBean{
        private String imgSrc;

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }
    }
}
