package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserMsgAdapter extends RecyclerView.Adapter<UserMsgAdapter.msgViewHolder> {

    private Context context;

    public UserMsgAdapter(Context context){
        this.context = context;
    }
    @Override
    public msgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(msgViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class msgViewHolder extends RecyclerView.ViewHolder {

        public msgViewHolder(View itemView) {
            super(itemView);
        }
    }
}
