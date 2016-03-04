package com.lsj.lsjnews.common;

import com.alibaba.fastjson.JSON;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.bean.news_type_bean.FootBallNewsList;
import com.lsj.lsjnews.bean.news_type_bean.HeadLineNewsList;
import com.lsj.lsjnews.bean.news_type_bean.LsjNewsList;
import com.lsj.lsjnews.bean.news_type_bean.NBANewsList;
import com.lsj.lsjnews.bean.news_type_bean.SocialNewsList;

import java.util.List;

/**
 * Created by Le on 2016/3/4.
 */
public class mainHelper {
    private static NBANewsList nbaNewsList;
    private static LsjNewsList mNewsList;
    private static SocialNewsList socialNewsList;
    private static HeadLineNewsList headLineNewsList;
    private static FootBallNewsList footBallNewsList;
    public static List<LsjNewsBean> getNewsDataByType(int type, String str){
        switch(type){
            case MyHelper.NBA_News_Type:
                nbaNewsList = JSON.parseObject(str, NBANewsList.class);
                if(nbaNewsList.getT1348649145984() != null){
                    return nbaNewsList.getT1348649145984();
                }else{
                    return null;
                }
            case MyHelper.Sport_News_Type:
                mNewsList = JSON.parseObject(str, LsjNewsList.class);
                if(mNewsList.getT1348649079062() != null){
                    return mNewsList.getT1348649079062();
                }else{
                    return null;
                }
            case MyHelper.Social_News_Type:
                socialNewsList = JSON.parseObject(str, SocialNewsList.class);
                if(socialNewsList.getT1348648037603() != null){
                    return socialNewsList.getT1348648037603();
                }else{
                    return null;
                }
            case MyHelper.HeadLine_news_Type:
                headLineNewsList = JSON.parseObject(str, HeadLineNewsList.class);
                if(headLineNewsList.getT1348647909107() != null){
                    return headLineNewsList.getT1348647909107();
                }else{
                    return null;
                }
            case MyHelper.FootBall_News_Type:
                footBallNewsList = JSON.parseObject(str, FootBallNewsList.class);
                if(footBallNewsList.getT1399700447917() != null){
                    return footBallNewsList.getT1399700447917();
                }else{
                    return null;
                }
            default:
                return  null;
        }
    }
}
