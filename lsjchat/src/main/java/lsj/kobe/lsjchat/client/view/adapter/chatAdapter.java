package lsj.kobe.lsjchat.client.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.view.LsjChat;
import lsj.kobe.lsjchat.comment.LsjHelp;

/**
 * Created by lsj on 2016/5/26.
 */
public class chatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<MessageBean> msgLists ;
    public chatAdapter(Context context, List<MessageBean> msgLists){
        this.context = context;
        this.msgLists = msgLists;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_to_msg,parent,false);
            chatRightViewHolder viewHolder = new chatRightViewHolder(view);

            return viewHolder;
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_from_msg,parent,false);
            chatLeftViewHolder viewHolder = new chatLeftViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(msgLists.get(position).getKey() == 0){
            chatRightViewHolder viewHolder = (chatRightViewHolder) holder;
            viewHolder.mTxtChatTo.setText(msgLists.get(position).getContent());
            viewHolder.mTxtDateTo.setText(msgLists.get(position).getSendTime());
            if(LsjHelp.USER_IMG != null || !LsjHelp.USER_IMG.equals("")){
                Glide.with(context).load(LsjHelp.USER_IMG).into(viewHolder.mImgHeadTo);
            }
        }else{
            chatLeftViewHolder viewHolder = (chatLeftViewHolder) holder;
            viewHolder.mTxtChatFrom.setText(msgLists.get(position).getContent());
            viewHolder.mTxtDateFrom.setText(msgLists.get(position).getSendTime());
            if(LsjHelp.USER_IMG != null || !LsjHelp.USER_IMG.equals("")){
                Glide.with(context).load(LsjChat.userImg).into(viewHolder.mImgHeadFrom);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        //0发送1接收
        if(msgLists.get(position).getKey() == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return msgLists.size();
    }
    public class chatRightViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtChatTo, mTxtDateTo;
        ImageView mImgHeadTo;
        public chatRightViewHolder(View itemView) {
            super(itemView);
            mTxtChatTo = (TextView) itemView.findViewById(R.id.txt_to_msg_info);
            mImgHeadTo = (ImageView) itemView.findViewById(R.id.img_user_send_head);
            mTxtDateTo = (TextView) itemView.findViewById(R.id.txt_to_msg_date);
        }
    }
    public class chatLeftViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtChatFrom,mTxtDateFrom;
        ImageView mImgHeadFrom;
        public chatLeftViewHolder(View itemView) {
            super(itemView);
            mTxtChatFrom = (TextView) itemView.findViewById(R.id.txt_from_msg_info);
            mImgHeadFrom = (ImageView) itemView.findViewById(R.id.img_user_recives_head);
            mTxtDateFrom = (TextView) itemView.findViewById(R.id.txt_form_msg_date);
        }
    }
}
