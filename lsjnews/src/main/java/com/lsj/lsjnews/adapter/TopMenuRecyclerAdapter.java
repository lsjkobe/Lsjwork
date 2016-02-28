package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.utils.CircleImageView;

/**
 * Created by lsj on 2016/2/28.
 */
public class TopMenuRecyclerAdapter extends RecyclerView.Adapter<MenuViewHolder>{

    private Context context;
    public TopMenuRecyclerAdapter(Context context){
        this.context = context;
    }
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_news_menu,parent,false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
            holder.mImgItem.setImageResource(R.mipmap.ic_1);

    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
class MenuViewHolder extends RecyclerView.ViewHolder{

    CircleImageView mImgItem;
    public MenuViewHolder(View itemView) {
        super(itemView);
        mImgItem = (CircleImageView) itemView.findViewById(R.id.img_top_menu_item);
    }
}