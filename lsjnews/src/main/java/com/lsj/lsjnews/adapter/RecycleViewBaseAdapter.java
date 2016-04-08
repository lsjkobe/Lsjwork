package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.interfaces.OnRefresh;

import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public abstract class RecycleViewBaseAdapter<T> extends RecyclerView.Adapter<RecycleViewBaseAdapter.NewsViewHolder> {
    protected Context context;
    protected List<T> datas;
    private boolean is_show_anim;
    public RecycleViewBaseAdapter(Context context, List<T> datas, boolean is_show_anim){
        this.context = context;
        this.datas = datas;
        this.is_show_anim = is_show_anim;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getItemLayoutId(viewType), parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewBaseAdapter.NewsViewHolder holder, int position) {
        initData(holder, position, datas.get(position));
        if(position == datas.size()-1){
            if(mRefresh != null){
                mRefresh.Refresh();
            }
        }
        if(is_show_anim){
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    public abstract int getItemLayoutId(int viewType);
    public abstract void initData(NewsViewHolder holder, int position, T item);
    private int lastPosition = -1;
    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //回收item动画效果，不做这处理会出现item错乱
    @Override
    public void onViewDetachedFromWindow(RecycleViewBaseAdapter.NewsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    //新闻列表滑到底部接口
    OnRefresh mRefresh;
    public void setOnRefresh(OnRefresh mRefresh){
        this.mRefresh = mRefresh;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        ImageView mImgNews;
        TextView mTxtDate;
        TextView mTxtVideoTags;
        public NewsViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_news_msg_1);
            mImgNews = (ImageView) itemView.findViewById(R.id.img_news_header_1);
            mTxtDate = (TextView) itemView.findViewById(R.id.txt_news_date_1);
            mTxtVideoTags = (TextView) itemView.findViewById(R.id.txt_news_video_tag);
        }
    }
}
