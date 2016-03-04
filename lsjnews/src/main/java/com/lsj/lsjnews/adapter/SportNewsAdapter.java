package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.NewsApi;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class SportNewsAdapter extends RecyclerView.Adapter<SportNewsAdapter.SportViewHolder> {

    private Context context;
    private List<NewsApi.Contentlist> datas;
    public SportNewsAdapter(Context context, List<NewsApi.Contentlist> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public SportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sport_news, parent, false);
        final SportViewHolder viewHolder = new SportViewHolder(view);
        view.setTag(viewHolder.getAdapterPosition());
        if(viewType == 0){
            viewHolder.mImgNews.setVisibility(View.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showNewsInfoWeb(context, datas.get(viewHolder.getAdapterPosition()).getLink());
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
    public void onBindViewHolder(SportViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position).getDesc());
        if(datas.get(position).getImageurls().size() != 0){
            ImageLoader.getInstance().displayImage(datas.get(position).getImageurls().get(0).getUrl(), holder.mImgNews);
        }

        if(position == datas.size()-1){
            if(mRefresh != null){
                mRefresh.Refresh();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int data_size = datas.get(position).getImageurls().size();
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

    class SportViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        ImageView mImgNews;
        public SportViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_news_msg_1);
            mImgNews = (ImageView) itemView.findViewById(R.id.img_news_header_1);
        }
    }
}
