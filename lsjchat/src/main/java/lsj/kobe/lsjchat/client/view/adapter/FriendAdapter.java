package lsj.kobe.lsjchat.client.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.UserBean;
import lsj.kobe.lsjchat.comment.UiHelp;

/**
 * Created by lsj on 2016/5/26.
 */
public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<UserBean> userLists ;
    public FriendAdapter(Context context, List<UserBean> userLists){
        this.context = context;
        this.userLists = userLists;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_list,parent,false);
        friendViewHolder viewHolder = new friendViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final friendViewHolder viewHolder = (friendViewHolder) holder;
        if(userLists.get(position).getMsgCount() == 0){
            viewHolder.mTxtMsgCount.setVisibility(View.GONE);
        }else{
            viewHolder.mTxtMsgCount.setVisibility(View.VISIBLE);
            viewHolder.mTxtMsgCount.setText(""+userLists.get(position).getMsgCount());
        }
        viewHolder.mTxtUserFriendName.setText(userLists.get(position).getAccount());
        viewHolder.mLayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.mTxtMsgCount.setVisibility(View.GONE);
                userLists.get(position).setMsgCount(0);
                UiHelp.showChatActivity(context, userLists.get(position));
            }
        });
        Glide.with(context).load(userLists.get(position).getImg()).into(viewHolder.mImgUserHead);

    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }
    public class friendViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtUserFriendName;
        ImageView mImgUserHead;
        RelativeLayout mLayView;
        TextView mTxtMsgCount;
        public friendViewHolder(View itemView) {
            super(itemView);
            mTxtUserFriendName = (TextView) itemView.findViewById(R.id.txt_user_friend_name);
            mImgUserHead = (ImageView) itemView.findViewById(R.id.img_user_friend_head);
            mLayView = (RelativeLayout) itemView.findViewById(R.id.layout_user_friend_view);
            mTxtMsgCount = (TextView) itemView.findViewById(R.id.txt_msg_count);
        }
    }
}
