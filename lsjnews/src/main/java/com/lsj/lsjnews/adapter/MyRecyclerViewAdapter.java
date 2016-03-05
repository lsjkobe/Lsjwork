package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.ApiNewsMsg;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<ApiNewsMsg.NewsBean> datas;
    private Context context;
    private List<Integer> lists;
    private int key = 0;

    public MyRecyclerViewAdapter(Context context, List<ApiNewsMsg.NewsBean> datas , int key) {
        this.datas = datas;
        this.context = context;
        getRandomHeights(datas.size());
        this.key = key;
    }

    private void getRandomHeights(int n) {
        lists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lists.add((int) (400 + Math.random() * 200));
        }
    }
    View view;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_social_news, parent, false);

        final MyViewHolder viewHolder = new MyViewHolder(view);
        view.setTag(viewHolder.getAdapterPosition());
        if(viewType == 0){
            viewHolder.mImgNews.setVisibility(View.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showNewsInfoWeb(context, datas.get(viewHolder.getAdapterPosition()).getUrl());
            }
        });
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        if(datas.get(position).getPicUrl().equals("") || datas.get(position).getPicUrl() == ""){
            return 0;
        }else{
            return 1;
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
//        if(key == 0){
//            params.height = PxDipUnti.dip2px(context,150f);
//        }else if(key == 1){
//            params.height = lists.get(position);//把随机的高度赋予item布局
//        }else{
//            params.width = LPhone.getScreenWidth(context);
//        }

        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(datas.get(position).getDescription());
        ImageLoader.getInstance().displayImage(datas.get(position).getPicUrl(), holder.mImgNews);
        if(position == datas.size()-1){
            if(mRefresh != null){
                mRefresh.Refresh();
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    //新闻列表滑到底部接口
    OnRefresh mRefresh;

    public void setOnRefresh(OnRefresh mRefresh){
        this.mRefresh = mRefresh;
    }


}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;
    ImageView mImgNews;
    public MyViewHolder(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_news_msg_1);
        mImgNews = (ImageView) itemView.findViewById(R.id.img_news_header_1);
    }
}
