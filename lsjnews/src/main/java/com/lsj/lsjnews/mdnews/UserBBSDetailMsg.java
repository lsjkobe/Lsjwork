package com.lsj.lsjnews.mdnews;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.lsj.lsjnews.bean.mdnewsBean.bbsDetailMsgBean;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.utils.EmojiParser;
import com.lsj.lsjnews.view.MyNestedScrollView;
import com.lsj.lsjnews.view.MyViewPager;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/4.
 */
public class UserBBSDetailMsg extends MyBaseActivity implements View.OnClickListener{
    private CardView mCardBBSDetailMsg;
    private TabLayout mTabBBSDetailMsg;
    private TabLayout mTabBBSDetailMsgTop;//当mTabBBSDetailMsg划出屏幕显示，功能类似于滑到头部固定
    private MyViewPager mViewPager;
    private List<View> mViewList;
    private List<String> mViewTitles;
    private detailMsgAdapter mViewpagerAdapter;
    private recyclerAdapter mRecyclerForwardAdapter,mRecyclerCommentAdapter,mRecyclerStarAdapter;
    private RecyclerView mRecyclerForward,mRecyclerComment,mRecyclerStar;
    private MyNestedScrollView mNested;
    private bbsDetailMsgBean.allDatas mAllDatas;
    private int currentPosition = 1; //当前viewpager
    private int key; //0点击转发后的微博进， 1点击转发微博源微博进
    private bbsBean.Lists bean;
    private ImageView mImgUserHead;
    private TextView mTxtUserName,mTxtDate,mTxtContent;
    private RecyclerView mRecyleImgs;
    private LinearLayout mLayLocation;
    private TextView mTxtLocation;
    private ImageView mImgBtnStar,mImgBtnForward, mImgBtnComment;
    //转发时显示
    private CardView mCardSource;
    private TextView mTxtSourceContent;
    private TextView mTxtSourceUserName;
    private RecyclerView mSourceRecyleImgs;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        bean = (bbsBean.Lists) getIntent().getSerializableExtra("detailMsg");
        key = getIntent().getIntExtra("key",0);
        if(key == 1){
            bean.setContent(bean.getSourceContent());
            bean.setuName(bean.getsName());
            bean.setmType(0);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mCardBBSDetailMsg = (CardView) findViewById(R.id.card_bbs_detail_msg);
        mTabBBSDetailMsg = (TabLayout) findViewById(R.id.tabs_bbs_detail_msg_menu);
        mTabBBSDetailMsgTop = (TabLayout) findViewById(R.id.tabs_bbs_detail_msg_menu_top);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager_user_bbs_detail_nsg);
        mNested = (MyNestedScrollView) findViewById(R.id.nestedscrollsiew);

        mImgUserHead = (ImageView) findViewById(R.id.img_user_bbs_msg_head);
        mTxtUserName = (TextView) findViewById(R.id.txt_user_bbs_msg_username);
        mTxtDate = (TextView) findViewById(R.id.txt_user_bbs_msg_date);
        mTxtContent = (TextView) findViewById(R.id.txt_user_bbs_content);
        mRecyleImgs = (RecyclerView) findViewById(R.id.recyle_user_bbs_img);
        mLayLocation = (LinearLayout) findViewById(R.id.lay_item_location);
        mTxtLocation = (TextView) findViewById(R.id.txt_item_location);
        mImgBtnStar = (ImageView) findViewById(R.id.img_btn_msg_star);
        mImgBtnForward = (ImageView) findViewById(R.id.img_btn_msg_forward);
        mImgBtnComment = (ImageView) findViewById(R.id.img_btn_msg_comment);
        mCardSource = (CardView) findViewById(R.id.card_source_bbs_msg);
        mTxtSourceContent = (TextView) findViewById(R.id.txt_source_bbs_content);
        mTxtSourceUserName = (TextView) findViewById(R.id.txt_source_bbs_user_name);
        mSourceRecyleImgs = (RecyclerView) findViewById(R.id.recyle_user_source_bbs_img);

        mImgBtnStar.setOnClickListener(this);
        mImgBtnForward.setOnClickListener(this);
        mImgBtnComment.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        mNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                MyLogger.showLogWithLineNum(3,"111:"+scrollY+":"+oldScrollY);
                if(scrollY > oldScrollY){
                    //15是由于cardview的高度造成的大概误差
                    if(scrollY >= mCardBBSDetailMsg.getHeight()+15){
                        mTabBBSDetailMsgTop.setVisibility(View.VISIBLE);
                    }
                }else{
                    if(scrollY <= mCardBBSDetailMsg.getHeight()+15){
                        mTabBBSDetailMsgTop.setVisibility(View.GONE);
                    }
                }
            }
        });
        initDetailMsg();
        initPager();
        getBBSComment();
    }

    private void initDetailMsg(){
        if(key == 0){
            if(bean.getIs_star() == 1){
                mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_select);
            }
        }else{
            if(bean.getS_is_star() == 1){
                mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_select);
            }
        }

        Glide.with(mContext).load(bean.getuHeadImg()).into(mImgUserHead);
        mTxtUserName.setText(bean.getuName());
        mTxtDate.setText(bean.getDate());
        mCardSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showBBSDetailMsg(mContext,bean, 1);
            }
        });
        mTxtContent.setText(EmojiParser.getInstance(mContext).replace(bean.getContent()));
        if(bean.getLocation() != null && bean.getLocation().length() != 0){
            mLayLocation.setVisibility(View.VISIBLE);
            mTxtLocation.setText(bean.getLocation());
        }else{
            mLayLocation.setVisibility(View.GONE);
        }
        initLineCount();
        if(bean.getmType() == 0){
            mCardSource.setVisibility(View.GONE);
            mRecyleImgs.setVisibility(View.VISIBLE);

        }else{
            mTxtSourceContent.setText(EmojiParser.getInstance(mContext).replace(bean.getSourceContent()));
            mTxtSourceUserName.setText("@"+bean.getsName());
            mCardSource.setVisibility(View.VISIBLE);
            mRecyleImgs.setVisibility(View.GONE);
        }
        if(bean.getImglists() != null && bean.getImglists().size()!=0){
            initDetailRecycleAdapter();
        }
    }
    int lineCount;
    private void initLineCount() {
        if(bean.getImglists().size() == 0){
            lineCount = 1;
        }else if(bean.getImglists().size() <= 4){
            lineCount = 2;
        }else{
            lineCount = 3;
        }
    }

    private void initDetailRecycleAdapter() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,lineCount);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyleImgs.setLayoutManager(mGridLayoutManager);
        GridLayoutManager mGridLayoutManagerSource = new GridLayoutManager(mContext,lineCount);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mSourceRecyleImgs.setLayoutManager(mGridLayoutManagerSource);
        UserMsgAdapter.imgAdapter mImgAdapter = new UserMsgAdapter.imgAdapter(mContext,bean.getImglists());
        if(bean.getmType() == 0){
            mRecyleImgs.setAdapter(mImgAdapter);
        }else{
            mSourceRecyleImgs.setAdapter(mImgAdapter);
        }
    }
    private void initPager(){
        View mForwardView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_bbs_detial_msg,null,false);
        mRecyclerForward = (RecyclerView) mForwardView.findViewById(R.id.recycler_bbs_detail_msg);
        View mCommentView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_bbs_detial_msg,null,false);
        mRecyclerComment = (RecyclerView) mCommentView.findViewById(R.id.recycler_bbs_detail_msg);
        View mStarView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_bbs_detial_msg,null,false);
        mRecyclerStar = (RecyclerView) mStarView.findViewById(R.id.recycler_bbs_detail_msg);
        mViewList = new ArrayList<>();
        mViewList.add(mForwardView);
        mViewList.add(mCommentView);
        mViewList.add(mStarView);
        mViewTitles = new ArrayList<>();
        mViewTitles.add("转发");
        mViewTitles.add("评论");
        mViewTitles.add("点赞");
        mViewpagerAdapter = new detailMsgAdapter(mViewList) ;
        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new detailOnPagerChangeListener());
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
        mRecyclerForward.setLayoutManager(mForwardLinearLayoutManager);
        mRecyclerComment.setLayoutManager(mCommentLinearLayoutManager);
        mRecyclerStar.setLayoutManager(mStarLinearLayoutManager);
    }

    private void initTabLayout() {
        mTabBBSDetailMsg.setupWithViewPager(mViewPager);
        mTabBBSDetailMsg.setScrollPosition(0,0,true);
        mTabBBSDetailMsg.setTabMode(TabLayout.MODE_FIXED);
        mTabBBSDetailMsg.setTabGravity(TabLayout.GRAVITY_FILL);

        mTabBBSDetailMsgTop.setupWithViewPager(mViewPager);
        mTabBBSDetailMsgTop.setScrollPosition(0,0,true);
        mTabBBSDetailMsgTop.setTabMode(TabLayout.MODE_FIXED);
        mTabBBSDetailMsgTop.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void getBBSComment(){
        RequestParams params = new RequestParams(Conts.GET_BBS_COMMENTS);
        if(key == 0){
            params.addBodyParameter("mid",String.valueOf(bean.getMid()));
        }else{
            params.addBodyParameter("mid",String.valueOf(bean.getSid()));
        }
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3,s);
                bbsDetailMsgBean bean = JSON.parseObject(s,bbsDetailMsgBean.class);
                switch (bean.getResultCode()){
                    case 1:
                        if(bean.getAllDataArray() != null){
                            mAllDatas = bean.getAllDataArray();
                            initOrRefresh();
                        }
                        break;
                    case 0:
                        break;
                }
            }
        });
    }

    private void initOrRefresh() {
        if (mRecyclerForwardAdapter == null) {
            mRecyclerForwardAdapter = new recyclerAdapter(mAllDatas, 0);
            mRecyclerForward.setAdapter(mRecyclerForwardAdapter);
        } else {
            if(currentPosition == 0){
                mRecyclerForwardAdapter.notifyDataSetChanged();
            }
        }
        if (mRecyclerStarAdapter == null) {
            mRecyclerStarAdapter = new recyclerAdapter(mAllDatas, 2);
            mRecyclerStar.setAdapter(mRecyclerStarAdapter);
        } else {
            if(currentPosition == 2){
                mRecyclerStarAdapter.notifyDataSetChanged();
            }
        }
        if (mRecyclerCommentAdapter == null) {
            mRecyclerCommentAdapter = new recyclerAdapter(mAllDatas, 1);
            mRecyclerComment.setAdapter(mRecyclerCommentAdapter);
        } else {
            if(currentPosition == 1){
                mRecyclerCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_btn_msg_star:
                RequestParams params = new RequestParams(Conts.GET_USER_CLICK_STAR);
                if (key ==0) {
                    params.addBodyParameter("mid", String.valueOf(bean.getMid()));
                }else{
                    params.addBodyParameter("mid", String.valueOf(bean.getSid()));
                }

                x.http().get(params, new NewCommonCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        baseBean bean = JSON.parseObject(s, baseBean.class);
                        switch (bean.getResultCode()) {
                            case 1:
                                mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_select);
                                break;
                            case -1:
                                mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_default);
                                break;
                            case 0:
                                MyToast.showToast(mContext, "赞失败");
                                break;
                        }

                    }
                });
                break;
            case R.id.img_btn_msg_forward:
//                UiHelper.showUserForward(mContext,bean); //错误，如果key==1 bean被改变
                UiHelper.showUserForward(mContext,(bbsBean.Lists) getIntent().getSerializableExtra("detailMsg"));
                break;
            case R.id.img_btn_msg_comment:
                if(key == 0){
                    UiHelper.showComment(mContext,bean.getMid());
                }else{
                    UiHelper.showComment(mContext, bean.getSid());
                }

                break;
        }
    }

    private class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerViewHolder>{

        private bbsDetailMsgBean.allDatas lists;
        private int key; //0 转发 1评论 2点赞
        public recyclerAdapter(bbsDetailMsgBean.allDatas lists, int key){
            this.lists = lists;
            this.key = key;
//            MyToast.showToast(mContext,lists.getCommentLists().get(0).getContent());
        }
        @Override
        public recyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_bbs_detail_msg,parent,false);
            recyclerViewHolder viewHolder = new recyclerViewHolder(view);
            if(key == 1){
                viewHolder.mTxtContent.setVisibility(View.VISIBLE);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(recyclerViewHolder holder, int position) {
            if(key == 1){
                bbsDetailMsgBean.allDatas.commentBean bean = lists.getCommentLists().get(position);
                holder.mTxtUserName.setText(bean.getuName());
                holder.mTxtDate.setText(bean.getcCreateDate());
                holder.mTxtContent.setText(EmojiParser.getInstance(mContext).replace(bean.getContent()));
                Glide.with(mContext).load(bean.getuHeadImg()).into(holder.mImgHead);
            }else if(key == 0){
                bbsDetailMsgBean.allDatas.forwardBean bean = lists.getForwardLists().get(position);
                holder.mTxtUserName.setText(bean.getuName());
                Glide.with(mContext).load(bean.getuHeadImg()).into(holder.mImgHead);
            }else{
                bbsDetailMsgBean.allDatas.starBean bean = lists.getStarLists().get(position);
                holder.mTxtUserName.setText(bean.getuName());
                Glide.with(mContext).load(bean.getuHeadImg()).into(holder.mImgHead);
            }

        }

        @Override
        public int getItemCount() {
            if(key == 1){
                return lists.getCommentLists().size();
            }else if(key == 0){
                return lists.getForwardLists().size();
            }else{
                return lists.getStarLists().size();
            }
        }

        public class recyclerViewHolder extends RecyclerView.ViewHolder {
            private TextView mTxtUserName;
            private TextView mTxtDate;
            private TextView mTxtContent;
            private ImageView mImgHead;
            public recyclerViewHolder(View itemView) {
                super(itemView);
                mTxtUserName = (TextView) itemView.findViewById(R.id.txt_user_bbs_detail_msg_username);
                mTxtDate = (TextView) itemView.findViewById(R.id.txt_user_bbs_detail_msg_date);
                mTxtContent = (TextView) itemView.findViewById(R.id.txt_user_bbs_detail_msg_content);
                mImgHead = (ImageView) itemView.findViewById(R.id.img_user_bbs_detail_msg_head);
            }
        }
    }
    private class detailOnPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    private class detailMsgAdapter extends PagerAdapter {

        private List<View> mViewList;

        public detailMsgAdapter(List<View> mViewList) {
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bbs_detail_msg;
    }
}
