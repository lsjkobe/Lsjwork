package com.lsj.lsjnews.fragment;

import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.utils.LsjFragmentManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsj on 2016/3/4.
 */
public class FragmentFactory {
    private static Map<Integer,NetNewsFragment> mFagmentMap = new HashMap<>();
    public static NetNewsFragment createFragment(int index){
        NetNewsFragment baseFragment = null;
            switch (index){
                case MyHelper.HeadLine_news_Type:
                    baseFragment = NetNewsFragment.newInstance(MyHelper.HeadLine_news_Type);
                    break;
                case MyHelper.Sport_News_Type:
                    baseFragment = NetNewsFragment.newInstance(MyHelper.Sport_News_Type);
                    break;
                case MyHelper.Social_News_Type:
                    baseFragment = NetNewsFragment.newInstance(MyHelper.Social_News_Type);
                    break;
                case MyHelper.NBA_News_Type:
                    baseFragment = NetNewsFragment.newInstance(MyHelper.NBA_News_Type);
                    break;
                case MyHelper.FootBall_News_Type:
                    baseFragment = NetNewsFragment.newInstance(MyHelper.FootBall_News_Type);
                    break;
            }

        return baseFragment;
    }
    public static void clearFragmentFactory(){
        mFagmentMap.clear();
    }

    public static NetNewsFragment getFragment(int index) {
        return mFagmentMap.get(index);
    }
}
