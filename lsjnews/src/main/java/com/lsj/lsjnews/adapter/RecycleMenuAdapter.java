package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lsj.lsjnews.R;
import com.lsj.lsjnews.utils.AnimUtil;

/**
 * Created by Administrator on 2016/3/8.
 */
public class RecycleMenuAdapter extends RecyclerView.Adapter<RecycleMenuAdapter.menuViewHolder>{

    private Context context;
    public RecycleMenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public menuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left_menu, parent, false);
        menuViewHolder menuViewHolder = new menuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(menuViewHolder holder, int position) {

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
    private int lastPosition = -1;

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation =  AnimUtil.getLeftInAnim(context);
            animation.setDuration(position*200);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    public class menuViewHolder extends RecyclerView.ViewHolder {

        public menuViewHolder(View itemView) {
            super(itemView);
        }
    }

}