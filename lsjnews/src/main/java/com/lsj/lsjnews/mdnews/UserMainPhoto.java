package com.lsj.lsjnews.mdnews;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.OtherUserMsgAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.photoLists;
import com.lsj.lsjnews.common.MyHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的相册
 * Created by lsj on 2016/5/18.
 */
public class UserMainPhoto extends MyBaseActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private myPhotoAdapter mAdapter;
    private LsjLoadingView mLoading;
    private List<photoLists.photoBean> photoLists = new ArrayList<>();
    private int page = 1;
    private int pageCount=1;
    @Override
    protected void initView() {
        super.initView();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_main_photo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_user_main_photo);
    }

    @Override
    protected void initData() {
        initToolbar();
        initRecycler();
        getPhotoData();
    }
    private void initToolbar() {
        mToolbar.setBackgroundResource(R.color.colorPrimary);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setTitle("我的相册");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
    private void initRecycler() {
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(mGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void getPhotoData(){
        RequestParams params = new RequestParams(Conts.GET_USER_BBS_PHOTO);
        params.addBodyParameter("uid",String.valueOf(MyHelper.USER_ID));
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
                        }
                        photoLists.addAll(mPhotoBean.getPhotoLists());
                        initOrRefresh();
                        break;
                    case 0:
                        initOrRefresh();
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
            mAdapter = new myPhotoAdapter(photoLists);
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

    private class myPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<photoLists.photoBean> lists;
        public myPhotoAdapter(List<photoLists.photoBean> lists){
            this.lists = lists;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_photo,parent,false);
            photoHolder holder = new photoHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            photoHolder viewHolder = (photoHolder) holder;
            Glide.with(mContext).load(lists.get(position).getImgSrc()).into(viewHolder.mImgPhoto);
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main_photo;
    }
}
