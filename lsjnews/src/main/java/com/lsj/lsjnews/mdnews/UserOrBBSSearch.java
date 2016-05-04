package com.lsj.lsjnews.mdnews;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.userLists;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.view.MyViewPager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/4.
 */
public class UserOrBBSSearch extends MyBaseActivity implements View.OnClickListener{
    private TabLayout mTabMenu;
    private MyViewPager mViewPager;
    private RecyclerView mRecyclerUser,mRecyclerBBS;
    private List<View> mViewList;
    private List<String> mViewTitles;
    private searchAdapter mSearchAdapter;
    private int currentPosition = 0;
    private ImageView mImgSearch;
    private EditText mEditKeyWord;
    @Override
    protected void initView() {
        super.initView();
        mTabMenu = (TabLayout) findViewById(R.id.tabs_user_bbs_search);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager_user_bbs_search);
        mImgSearch = (ImageView) findViewById(R.id.img_user_bbs_search);
        mEditKeyWord = (EditText) findViewById(R.id.edit_user_bbs_search);

        mImgSearch.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        initPager();
    }

    private void initPager(){
        View mUserView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_bbs_detial_msg,null,false);
        mRecyclerUser = (RecyclerView) mUserView.findViewById(R.id.recycler_bbs_detail_msg);
        View mBBSView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_bbs_detial_msg,null,false);
        mRecyclerBBS = (RecyclerView) mBBSView.findViewById(R.id.recycler_bbs_detail_msg);
        mViewList = new ArrayList<>();
        mViewList.add(mRecyclerUser);
        mViewList.add(mRecyclerBBS);
        mViewTitles = new ArrayList<>();
        mViewTitles.add("用户");
        mViewTitles.add("圈子");
        mSearchAdapter = new searchAdapter(mViewList);
        mViewPager.setAdapter(mSearchAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new searchOnPagerChangeListener());
        initTabLayout();
        initRecycler();
    }
    private void initRecycler() {
        LinearLayoutManager mForwardLinearLayoutManager = new LinearLayoutManager(mContext);
        mForwardLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager mCommentLinearLayoutManager = new LinearLayoutManager(mContext);
        mCommentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager mStarLinearLayoutManager = new LinearLayoutManager(mContext);
        mCommentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void initTabLayout() {
        mTabMenu.setupWithViewPager(mViewPager);
        mTabMenu.setScrollPosition(0,0,true);
        mTabMenu.setTabMode(TabLayout.MODE_FIXED);
        mTabMenu.setTabGravity(TabLayout.GRAVITY_CENTER);
    }

    private void getSearchUser(){
        RequestParams params = new RequestParams(Conts.GET_SEARCH_USER);
        params.addBodyParameter("keyWord",mEditKeyWord.getText().toString());
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                userLists mUserLists = JSON.parseObject(s,userLists.class);
                if(mUserLists.getResultCode() == 0){
                    MyToast.showToast(mContext,"用户不存在");
                }else{
                    MyToast.showToast(mContext,s);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_user_bbs_search:
                if(mEditKeyWord.getText().toString().trim().length() == 0 || mEditKeyWord.getText() == null){
                    MyToast.showToast(mContext,"输入搜索词");
                }else{
                    if(currentPosition == 0){
                        getSearchUser();
                    }
                }

                break;
        }
    }

    private class searchAdapter extends PagerAdapter {

        private List<View> mViewList;

        public searchAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }
        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mViewTitles.get(position);
        }
    }
    private class searchOnPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
//            MyToast.showToast(mContext,""+currentPosition);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs_search;
    }
}
