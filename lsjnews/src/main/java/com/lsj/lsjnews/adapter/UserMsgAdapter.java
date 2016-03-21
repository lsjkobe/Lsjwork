package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.PxDipUnti;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.utils.CircleImageView;
import com.lsj.lsjnews.utils.RecycleSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserMsgAdapter extends RecyclerView.Adapter<UserMsgAdapter.msgViewHolder> {

    private final int ITEM_SPACE = 2;
    private Context context;
    private List<bbsBean.Lists> datas = new ArrayList<>();
    private imgAdapter mImgAdapter;

    private int lineCount = 3;

    public UserMsgAdapter(Context context, List<bbsBean.Lists> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public msgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == -1){
            lineCount = 1;
        }else if(viewType == 0){
            lineCount = 2;
        }else if(viewType == 1){
            lineCount = 3;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_bbs_msg, parent, false);
        msgViewHolder viewHolder = new msgViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(msgViewHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getuHeadImg()).into(holder.mImgHead);
        holder.mName.setText(datas.get(position).getuName());
        holder.mDate.setText(datas.get(position).getDate());
        holder.mContent.setText(datas.get(position).getContent());
        if(datas.get(position).getImglists() != null && datas.get(position).getImglists().size()!=0){
            initRecyle(holder,position);
        }
    }

    private void initRecyle(msgViewHolder holder, int position) {
            mImgAdapter = new imgAdapter(context,datas.get(position).getImglists());
            holder.mRecyleImgs.setAdapter(mImgAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        if(datas.get(position).getImglists().size() == 0){
            return -1;
        }else if(datas.get(position).getImglists().size() <= 4){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class msgViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImgHead;
        TextView mName;
        TextView mDate ;
        TextView mContent;
        RecyclerView mRecyleImgs;
        public msgViewHolder(View itemView) {
            super(itemView);
            mImgHead = (CircleImageView) itemView.findViewById(R.id.img_user_bbs_msg_head);
            mName = (TextView) itemView.findViewById(R.id.txt_user_bbs_msg_username);
            mDate = (TextView) itemView.findViewById(R.id.txt_user_bbs_msg_date);
            mContent = (TextView) itemView.findViewById(R.id.txt_user_bbs_content);
            mRecyleImgs = (RecyclerView) itemView.findViewById(R.id.recyle_user_bbs_img);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context,lineCount);
            mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mRecyleImgs.setLayoutManager(mGridLayoutManager);
            int spacingInPixels = ITEM_SPACE;
            mRecyleImgs.addItemDecoration(new RecycleSpaceItemDecoration(spacingInPixels));
        }
    }

    private class imgAdapter extends RecyclerView.Adapter<imgAdapter.imgViewHolder>{

        private Context mContext;
        private List<bbsBean.Lists.imglists> mImgLists = new ArrayList<>();
        public imgAdapter(Context mContext,List<bbsBean.Lists.imglists> mImgLists){
            this.mContext = mContext;
            this.mImgLists = mImgLists;

        }

        @Override
        public imgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_user_bbs_item_imgs,parent,false);
            imgViewHolder viewHolder1 = new imgViewHolder(view1);
            return viewHolder1;
        }

        @Override
        public void onBindViewHolder(imgViewHolder holder, int position) {
            Glide.with(mContext).load(mImgLists.get(position).getImgsrc()).into(holder.mImgItem);
        }

        @Override
        public int getItemCount() {
            return mImgLists.size();
        }

        public class imgViewHolder extends RecyclerView.ViewHolder{
            ImageView mImgItem;
            public imgViewHolder(View itemView) {
                super(itemView);
                mImgItem = (ImageView) itemView.findViewById(R.id.img_user_bbs_item);
                ViewGroup.LayoutParams mParams = mImgItem.getLayoutParams();
                //PxDipUnti.dip2px(mContext,20)为recyler的左右magin
                mParams.width = (LPhone.getScreenWidth(mContext) - PxDipUnti.dip2px(mContext,20) - ITEM_SPACE*2)/3;
                mParams.height = mParams.width;
            }
        }
    }
}
