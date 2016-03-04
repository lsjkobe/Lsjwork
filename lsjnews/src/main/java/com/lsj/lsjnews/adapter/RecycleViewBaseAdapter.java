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

import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public abstract class RecycleViewBaseAdapter<T> extends RecyclerView.Adapter<RecycleViewBaseAdapter.NewsViewHolder> {
    protected Context context;
    protected List<T> datas;
    public RecycleViewBaseAdapter(Context context, List<T> datas){
        this.context = context;
        this.datas = datas;
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
        setAnimation(holder.itemView, position);
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
        public NewsViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_news_msg_1);
            mImgNews = (ImageView) itemView.findViewById(R.id.img_news_header_1);
        }
    }
}
