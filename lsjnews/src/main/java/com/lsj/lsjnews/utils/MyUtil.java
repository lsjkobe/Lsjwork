package com.lsj.lsjnews.utils;

import android.text.Html;

import com.alibaba.fastjson.JSON;
import com.lsj.lsjnews.bean.LsjNewsDetail;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Le on 2016/3/3.
 */
public class MyUtil {


    /**
     *通过jsonObject.keys().next().toString()获取到动态key的name
     * 再通过key的name获取数据
     * @param json 动态key的json
     * @return 返回动态key里边的json
     */
    public static String getStrFromJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            String a = jsonObject.get(jsonObject.keys().next().toString()).toString();
            return a;
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
