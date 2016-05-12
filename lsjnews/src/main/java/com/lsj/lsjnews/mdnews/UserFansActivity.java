package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.userLists;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.utils.CircleImageView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/5/11.
 */
public class UserFansActivity extends MyBaseActivity{
    private RecyclerView mRecyclerFans;
    private List<userLists.searchUser> fansLists = new ArrayList<>();
    private userFansAdapter fansAdapter;
    private String key = "0";
    private TextView mTxtToolbarTitle;
    private Toolbar mToolbar;
    @Override
    protected void initGetIntent() {
        super.initGetIntent();
        key = getIntent().getStringExtra("key");
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerFans = (RecyclerView) findViewById(R.id.recycler_user_main_fans_or_relation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_fans_relation_top);
        mTxtToolbarTitle = (TextView) findViewById(R.id.txt_toolbar_center_username);
    }

    @Override
    protected void initData() {
        mToolbar.setBackgroundResource(R.color.colorPrimary);
        if(key.equals("0")){
            mTxtToolbarTitle.setText("粉丝");
        }else{
            mTxtToolbarTitle.setText("关注");
        }
        initRecycler();
        getFans();
    }
    private void initRecycler(){
        LinearLayoutManager mUserLinearLayoutManager = new LinearLayoutManager(mContext);
        mUserLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerFans.setLayoutManager(mUserLinearLayoutManager);
    }
    private void getFans(){
        RequestParams params = new RequestParams(Conts.GET_USER_FANS);
        params.addBodyParameter("key",key);
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                userLists mUserLists = JSON.parseObject(s,userLists.class);
                MyLogger.showLogWithLineNum(3,"-------------"+s);
                if(mUserLists.getResultCode() == 0){
                    MyToast.showToast(mContext,"没有好友关注");
                }else{
                    if(mUserLists.getUserLists() != null){
                        fansLists.clear();
                        fansLists.addAll(mUserLists.getUserLists());
                        initOrRefresh();
                    }
                }

            }
        });
    }
    private void initOrRefresh(){
        if(fansAdapter == null){
            fansAdapter = new userFansAdapter(fansLists);
            mRecyclerFans.setAdapter(fansAdapter);
        }else{
            fansAdapter.notifyDataSetChanged();
        }
    }
    private class userFansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<userLists.searchUser> datas;
        public userFansAdapter(List<userLists.searchUser> datas){
            this.datas = datas;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_user,parent,false);
            fansViewHolder viewHolder = new fansViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final fansViewHolder viewHolder = (fansViewHolder) holder;
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
        public class fansViewHolder extends RecyclerView.ViewHolder{
            private TextView mTxtName;
            private TextView mTxtFansCount;
            private CircleImageView mImgUserHead;
            private Button mBtnRelation;
            private RelativeLayout mRelativeLayout;
            public fansViewHolder(View itemView) {
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
        return R.layout.activity_user_main_fans_relation;
    }
}
