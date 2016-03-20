package com.lsj.lsjnews.common;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.LPhone;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.bean.NewNewsList;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.utils.MyUtil;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by lsj on 2016/3/4.
 */
public class mainHelper {
    public static List<LsjNewsBean> getNewsData(String str){
        String jsonString = MyUtil.setJsonToNewJson(str);
        NewNewsList LsjNewsList = JSON.parseObject(jsonString, NewNewsList.class);
        if(LsjNewsList.getMyNewsList() != null){
            return LsjNewsList.getMyNewsList();
        }else{
            return null;
        }

    }

    /**
     * 动态修改tab的模式
     *
     * @param tabLayout
     */
    public static void dynamicSetTablayoutMode(TabLayout tabLayout) {
        int tabTotalWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0);
            tabTotalWidth += view.getMeasuredWidth();
        }
        if (tabTotalWidth <= LPhone.getScreenWidth(tabLayout.getContext())) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

}
