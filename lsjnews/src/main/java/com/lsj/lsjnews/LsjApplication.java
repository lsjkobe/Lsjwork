package com.lsj.lsjnews;

import android.app.Application;
import android.graphics.Bitmap;

import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.MyApi;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by Le on 2016/1/9.
 */
public class LsjApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initFragmentType();
        x.Ext.init(this);
        initImageLoader();

    }

    private void initFragmentType() {
        MyHelper.mTypeMap.put(MyHelper.HeadLine_news_Type, MyApi.HEADLINE_ID);
        MyHelper.mTypeMap.put(MyHelper.Sport_News_Type, MyApi.SPORTS_ID);
        MyHelper.mTypeMap.put(MyHelper.Social_News_Type, MyApi.SOCIETY_ID);
        MyHelper.mTypeMap.put(MyHelper.NBA_News_Type, MyApi.NBA_ID);
        MyHelper.mTypeMap.put(MyHelper.FootBall_News_Type, MyApi.FOOTBALL_ID);
    }

    private void initImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(com.example.lsj.httplibrary.R.drawable.loading)
                .showImageForEmptyUri(com.example.lsj.httplibrary.R.drawable.loading)
                        //.displayer(new FadeInBitmapDisplayer(500)) // 图片加载好后渐入的动画时间
                .build();
//        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
//                .threadPriority(Thread.NORM_PRIORITY - 2) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(this)) // default
//                .writeDebugLogs()
                .defaultDisplayImageOptions(defaultOptions) // default
                .build();
        ImageLoader.getInstance().init(config);
    }
}
