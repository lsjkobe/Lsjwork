package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.PxDipUnti;
import com.example.lsj.httplibrary.widget.RecyclerItemClickListener;
import com.lsj.lsjnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{
    private List<Integer> datas;
    private Context context;
    private List<Integer> lists;
    private int key = 0;
    private OnRecyclerViewIteListener mListener;

    public MyRecyclerViewAdapter(Context context, List<Integer> datas , int key) {
        this.datas = datas;
        this.context = context;
        getRandomHeights(datas);
        this.key = key;
    }



    private void getRandomHeights(List<Integer> datas) {
        lists = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
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
        view.setTag(position+"");
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if(key == 0){
            params.height = PxDipUnti.dip2px(context,150f);
        }else if(key == 1){
            params.height = lists.get(position);//把随机的高度赋予item布局
        }else{
            params.width = LPhone.getScreenWidth(context);
        }
        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(position+"");
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
    public static interface OnRecyclerViewIteListener{
        void onItemClick(View view , String data);
    }
    public void setOnRecyclerViewIteListener(OnRecyclerViewIteListener mListener){
        this.mListener = mListener;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;
    public MyViewHolder(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.item_tv);
    }
}
