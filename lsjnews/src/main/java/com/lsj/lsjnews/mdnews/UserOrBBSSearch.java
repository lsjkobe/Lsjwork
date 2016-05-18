package com.lsj.lsjnews.mdnews;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.UserMsgAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.bean.mdnewsBean.userLists;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.lsj.lsjnews.utils.CircleImageView;
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
    private ViewPager mViewPager;
    private RecyclerView mRecyclerUser,mRecyclerBBS;
    private List<View> mViewList;
    private List<String> mViewTitles;
    private searchAdapter mSearchAdapter;
    private int currentPosition = 0;
    private ImageView mImgSearch;
    private EditText mEditKeyWord;
    private userSearchAdapter userAdapter;
    private List<userLists.searchUser> userLists = new ArrayList<>();
    private UserMsgAdapter bbsAdapter;
    private List<bbsBean.Lists> bbsLists = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        mTabMenu = (TabLayout) findViewById(R.id.tabs_user_bbs_search);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_user_bbs_search);
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
        LinearLayoutManager mUserLinearLayoutManager = new LinearLayoutManager(mContext);
        mUserLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerUser.setLayoutManager(mUserLinearLayoutManager);
        LinearLayoutManager mBBSLinearLayoutManager = new LinearLayoutManager(mContext);
        mBBSLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerBBS.setLayoutManager(mBBSLinearLayoutManager);
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
                MyLogger.showLogWithLineNum(3,"-------------"+s);
                if(mUserLists.getResultCode() == 0){
                    MyToast.showToast(mContext,"用户不存在");
                }else{
                    if(mUserLists.getUserLists() != null){
                        userLists.clear();
                        userLists.addAll(mUserLists.getUserLists());
                        initOrRefreshUser();
                    }
                }

            }
        });
    }
    private void initOrRefreshUser(){
        if(userAdapter == null){
            userAdapter = new userSearchAdapter(userLists);
            mRecyclerUser.setAdapter(userAdapter);
        }else{
            userAdapter.notifyDataSetChanged();
        }
    }

    private void getSearchBBS() {
        RequestParams params = new RequestParams(Conts.GET_SEARCH_BBS);
        params.addBodyParameter("keyWord",mEditKeyWord.getText().toString());
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                bbsBean mBBSLists = JSON.parseObject(s,bbsBean.class);
                MyLogger.showLogWithLineNum(3,"-------------"+s);
                if(mBBSLists.getResultCode() == 0){
                    MyToast.showToast(mContext,"找不到圈子");
                }else{
                    if(mBBSLists.getLists() != null){
                        bbsLists.clear();
                        bbsLists.addAll(mBBSLists.getLists());
                        initOrRefreshBBS();
                    }
                }

            }
        });
    }
    private void initOrRefreshBBS(){
        if(bbsAdapter == null){
            bbsAdapter = new UserMsgAdapter(mContext, bbsLists);
            mRecyclerBBS.setAdapter(bbsAdapter);
            bbsAdapter.setOnRefresh(new OnRefresh() {
                @Override
                public void Refresh() {
                    MyToast.showToast(mContext,"到底了");
                }
            });
        }else{
            //数据长度改变时动态改变配置，防止数组下标越界
            bbsAdapter.changeNewConfig();
            bbsAdapter.notifyDataSetChanged();
        }
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
                    }else{
                        getSearchBBS();
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
    private class userSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<userLists.searchUser> datas;
        public userSearchAdapter(List<userLists.searchUser> datas){
            this.datas = datas;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_user,parent,false);
            userViewHolder viewHolder = new userViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final userViewHolder viewHolder = (userViewHolder) holder;
            viewHolder.mTxtName.setText(datas.get(position).getuName());
            viewHolder.mTxtFansCount.setText("粉丝:"+datas.get(position).getuFansCount());
            Glide.with(mContext).load(datas.get(position).getuImg()).into(viewHolder.mImgUserHead);

            switch(datas.get(position).getKey()){
                case -1:
                    viewHolder.mBtnRelation.setVisibility(View.GONE);
                    break;
                case 0:
                    viewHolder.mBtnRelation.setText("关注");
                    viewHolder.mBtnRelation.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    viewHolder.mBtnRelation.setText("已关注");
                    viewHolder.mBtnRelation.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    viewHolder.mBtnRelation.setText("相互关注");
                    viewHolder.mBtnRelation.setVisibility(View.VISIBLE);
                    break;
            }
            viewHolder.mBtnRelation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followOrCancel(position,viewHolder.mBtnRelation);
                }
            });
            viewHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiHelper.showOtherUserMain(mContext,datas.get(position).getUid());
                }
            });
        }

        private void followOrCancel(final int position, final Button btn){
            RequestParams params = new RequestParams(Conts.GET_FOLLOW_OR_CANCEL);
            params.addBodyParameter("uid",String.valueOf(datas.get(position).getUid()));
            params.addBodyParameter("key",String.valueOf(datas.get(position).getKey()));
            x.http().get(params, new NewCommonCallBack() {
                @Override
                public void onSuccess(String s) {
                    baseBean bean = JSON.parseObject(s,baseBean.class);
                    MyToast.showToast(mContext,""+bean.getResultCode());
                    switch (bean.getResultCode()){
                        case 1:
                            datas.get(position).setKey(1);
                            btn.setText("已关注");
                            break;
                        case 0:
                            btn.setText("关注");
                            datas.get(position).setKey(0);
                            break;
                        case 2:
                            btn.setText("相互关注");
                            datas.get(position).setKey(2);
                            break;
                        case -2:
                            MyToast.showToast(mContext,"重试");
                            break;
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }
        public class userViewHolder extends RecyclerView.ViewHolder{
            private TextView mTxtName;
            private TextView mTxtFansCount;
            private CircleImageView mImgUserHead;
            private Button mBtnRelation;
            private RelativeLayout mRelativeLayout;
            public userViewHolder(View itemView) {
                super(itemView);
                mTxtName = (TextView) itemView.findViewById(R.id.txt_search_user_name);
                mTxtFansCount = (TextView) itemView.findViewById(R.id.txt_search_user_fans_count);
                mImgUserHead = (CircleImageView) itemView.findViewById(R.id.img_search_user_head);
                mBtnRelation = (Button) itemView.findViewById(R.id.btn_search_user_relation);
                mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout_search_user);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs_search;
    }
}
