package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.interfaces.OnEmojiOnclick;

/**
 * Created by lsj on 2016/4/1.
 */
public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.emojiViewHolder>{
    private Context context;
    private int[] emojis;
    public EmojiAdapter(Context context, int[] emojis){
        this.context = context;
        this.emojis = emojis;
    }
    @Override
    public emojiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_emoji,parent,false);
        emojiViewHolder holder = new emojiViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(emojiViewHolder holder, final int position) {
        Glide.with(context).load(emojis[position]).into(holder.mImgEmoji);
        holder.mImgEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToast.showToast(context,position+":"+holder.getAdapterPosition());
                onEmojiOnclick.getPosition(position);
            }
        });
    }

    OnEmojiOnclick onEmojiOnclick;
    public void setOnEmojiOnclick(OnEmojiOnclick onEmojiOnclick){
        this.onEmojiOnclick = onEmojiOnclick;
    }
    @Override
    public int getItemCount() {
        return emojis.length;
    }

    public class emojiViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgEmoji;
        public emojiViewHolder(View itemView) {
            super(itemView);
            mImgEmoji = (ImageView) itemView.findViewById(R.id.img_item_layout_emoji);
        }
    }
}
