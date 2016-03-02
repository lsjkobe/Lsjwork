
package com.lsj.lsjnews.utils;

import android.content.Context;

import com.lsj.lsjnews.bean.LsjNewsDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewDetailJson extends JsonPacket {

    public static NewDetailJson newDetailJson;

    public LsjNewsDetail mLsjNewsDetail;

    public NewDetailJson(Context context) {
        super(context);
    }

    public static NewDetailJson instance(Context context) {
        if (newDetailJson == null) {
            newDetailJson = new NewDetailJson(context);
        }
        return newDetailJson;
    }

    public LsjNewsDetail readJsonNewModles(String res, String newId) {
        try {
            if (res == null || res.equals("")) {
                return null;
            }
            JSONObject jsonObject = new JSONObject(res).getJSONObject(newId);
            mLsjNewsDetail = readNewModle(jsonObject);
        } catch (Exception e) {

        } finally {
            System.gc();
        }
        return mLsjNewsDetail;
    }

    /**
     *
     *
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public List<String> readImgList(JSONArray jsonArray) throws Exception {
        List<String> imgList = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            imgList.add(getString("src", jsonArray.getJSONObject(i)));
        }

        return imgList;
    }

    /**
     * ��ȡͼ���б�
     * 
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public LsjNewsDetail readNewModle(JSONObject jsonObject) throws Exception {
        LsjNewsDetail mLsjNewsDetail = null;

        String docid = "";
        String title = "";
        String source = "";
        String ptime = "";
        String body = "";
        String url_mp4 = "";
        String cover = "";

        docid = getString("docid", jsonObject);
        title = getString("title", jsonObject);
        source = getString("source", jsonObject);
        ptime = getString("ptime", jsonObject);
        body = getString("body", jsonObject);

        if (jsonObject.has("video")) {
            JSONObject jsonObje = jsonObject.getJSONArray("video").getJSONObject(0);
            url_mp4 = getString("url_mp4", jsonObje);
            cover = getString("cover", jsonObje);
        }

        JSONArray jsonArray = jsonObject.getJSONArray("img");

        List<String> imgList = readImgList(jsonArray);

        mLsjNewsDetail = new LsjNewsDetail();

        mLsjNewsDetail.setDocid(docid);
        mLsjNewsDetail.setImgList(imgList);
        mLsjNewsDetail.setPtime(ptime);
        mLsjNewsDetail.setSource(source);
        mLsjNewsDetail.setTitle(title);
        mLsjNewsDetail.setBody(body);
        mLsjNewsDetail.setUrl_mp4(url_mp4);
        mLsjNewsDetail.setCover(cover);

        return mLsjNewsDetail;
    }

}
