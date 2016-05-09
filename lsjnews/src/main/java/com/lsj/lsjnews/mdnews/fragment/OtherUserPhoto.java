package com.lsj.lsjnews.mdnews.fragment;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.base.BaseFragment;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.OtherUserMsgAdapter;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.photoLists;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.mdnews.OtherUserMain;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/13.
 */
public class OtherUserPhoto extends BaseFragment{
    private RecyclerView mRecyclerView;
    private photoAdapter mAdapter;
    private LsjLoadingView mLoading;
    private List<photoLists.photoBean> photoLists = new ArrayList<>();
    private photoLists.photoBean nullBean = new photoLists.photoBean();
    private TextView mTxtNoData;
    private int page = 1;
    private int pageCount=1;
    private int uid;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        showTopView(false);
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_other_user_main);
        mLoading = (LsjLoadingView) findViewById(R.id.loading_other_user_main_bbs);
        mTxtNoData = (TextView) findViewById(R.id.txt_other_user_main_no_data);
//        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        getPhotoData();
        initRecycler();
    }

    private void initRecycler() {
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(mGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? mGridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    private void getPhotoData(){
        RequestParams params = new RequestParams(Conts.GET_USER_BBS_PHOTO);
        params.addBodyParameter("uid",String.valueOf(uid));
        params.addBodyParameter("page","1");
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3,s);
                photoLists mPhotoBean = JSON.parseObject(s, photoLists.class);
                switch(mPhotoBean.getResultCode()){
                    case 1:
                        pageCount = mPhotoBean.getPageCount();
                        if(page == 1){
                            photoLists.clear();
                            photoLists.add(nullBean);
                        }
                        photoLists.addAll(mPhotoBean.getPhotoLists());
                        initOrRefresh();
                        break;
                    case 0:
                        photoLists.add(nullBean);
                        initOrRefresh();
                        mTxtNoData.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
            }
        });

    }
    private void initOrRefresh(){

        if(mAdapter == null){
            mAdapter = new photoAdapter(photoLists);
            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.setOnRefresh(new OnRefresh() {
//                @Override
//                public void Refresh() {
//                    page++;
//                    if(page > pageCount){
//                        MyToast.showToast(mContext,"没有数据了");
//
//                    }else{
//                        mLoading.setVisibility(View.VISIBLE);
//                        mLoading.startLoadingAnim();
//                        getBBSData();
//                    }
//                }
//            });
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class photoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<photoLists.photoBean> lists;
        public photoAdapter(List<photoLists.photoBean> lists){
            this.lists = lists;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == 0){
                View view = LayoutInflater.from(mContext).inflate(R.layout.tab_bbs_msg_menu, parent, false);
                OtherUserMsgAdapter.headViewHolder viewHolder = new OtherUserMsgAdapter.headViewHolder(view);
                viewHolder.mTablayout.setupWithViewPager(OtherUserMain.mViewPager);
                viewHolder.mTablayout.setTabMode(TabLayout.MODE_FIXED);
                viewHolder.mTablayout.setScrollPosition(0,0,true);
                viewHolder.mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
                return viewHolder;
            }else{
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_photo,parent,false);
                photoHolder holder = new photoHolder(view);
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(position != 0){
                photoHolder viewHolder = (photoHolder) holder;
                Glide.with(mContext).load(lists.get(position).getImgSrc()).into(viewHolder.mImgPhoto);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                //头部
                return 0;
            }else{
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }
        public class photoHolder extends RecyclerView.ViewHolder {
            ImageView mImgPhoto;
            public photoHolder(View itemView) {
                super(itemView);
                mImgPhoto = (ImageView) itemView.findViewById(R.id.img_show_photo_item);
                ViewGroup.LayoutParams mParams = mImgPhoto.getLayoutParams();
                mParams.width = LPhone.getScreenWidth(mContext) / 3;
                mParams.height = mParams.width;
            }
        }
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_user_bbs;
    }
}
