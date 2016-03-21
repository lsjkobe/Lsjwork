package com.lsj.lsjnews.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lsj on 2016/3/21.
 */
public class getPhoneImg {
    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    public static List<String> getImages(final Context context, final List<String> mLists) {
        final HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            MyToast.showToast(context, "暂无外部存储");
            return null;
        }

        //baseManager.showProgressbar();

        new Thread(new Runnable() {

            @Override
            public void run() {
                ContentResolver mContentResolver = context.getContentResolver();
                Uri mUri = Uri.parse("content://media/external/images/media");
                Uri mImageUri = null;
                Cursor cursor = mContentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String data = cursor.getString(cursor
                            .getColumnIndex(MediaStore.MediaColumns.DATA));
                    MyLogger.showLogWithLineNum(3,"==============imgsrc:"+data);
                    mLists.add(data);
                    cursor.moveToNext();
                }
            }
        }).start();
        return mLists;
    }
}
