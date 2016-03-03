package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.NewCallBack;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.bean.LsjNewsList;
import com.lsj.lsjnews.bean.NewsApi;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.HttpHelper;
import com.lsj.lsjnews.http.MyApi;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class NetNewsListAdapter extends RecyclerView.Adapter<NetNewsListAdapter.NewsViewHolder> {

    private Context context;
    private List<LsjNewsBean> datas;
    public NetNewsListAdapter(Context context, List<LsjNewsBean> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sport_news, parent, false);
        final NewsViewHolder viewHolder = new NewsViewHolder(view);
        view.setTag(viewHolder.getAdapterPosition());
        if(viewType == 0){
            viewHolder.mImgNews.setVisibility(View.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datas.get(viewHolder.getAdapterPosition()).getPostid() != null){
                    UiHelper.showNewsInfoThis(context, datas.get(viewHolder.getAdapterPosition()).getPostid());
                }else{
                    MyToast.showToast(context, "新闻出现异常");
                }
            }

        });
        return viewHolder;
//        switch (viewType){
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//        }
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position).getTitle());
        if(datas.get(position).getImgsrc() != null){
            ImageLoader.getInstance().displayImage(datas.get(position).getImgsrc(), holder.mImgNews);
        }

        if(position == datas.size()-1){
            if(mRefresh != null){
                mRefresh.Refresh();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int data_size = 0;
//        if(datas.size() != 0){
//            data_size = datas.get(position).getImgextra().size();
//        }

        switch (data_size){
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 2;
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
