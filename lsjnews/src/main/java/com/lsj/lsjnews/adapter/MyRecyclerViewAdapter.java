package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.PxDipUnti;
import com.example.lsj.httplibrary.widget.RecyclerItemClickListener;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.ApiNewsMsg;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{
    private List<ApiNewsMsg.NewsBean> datas;
    private Context context;
    private List<Integer> lists;
    private int key = 0;
    private OnRecyclerViewIteListener mListener;

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
        view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        view.setOnClickListener(this);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
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

    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onItemClick(v, (String)v.getTag());
        }
    }

    OnRefresh mRefresh;
    public static interface OnRefresh{
        void Refresh();
    }
    public void setOnRefresh(OnRefresh mRefresh){
        this.mRefresh = mRefresh;
    }

    public static interface OnRecyclerViewIteListener{
        void onItemClick(View view , String data);
    }
    public void setOnRecyclerViewIteListener(OnRecyclerViewIteListener mListener){
        this.mListener = mListener;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;
    ImageView mImgNews;
    public MyViewHolder(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_news_msg);
        mImgNews = (ImageView) itemView.findViewById(R.id.img_news_header);
    }
}
