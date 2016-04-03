package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.utils.PxDipUnti;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnRefresh;
import com.lsj.lsjnews.utils.CircleImageView;
import com.lsj.lsjnews.utils.EmojiParser;
import com.lsj.lsjnews.utils.RecycleSpaceItemDecoration;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserMsgAdapter extends RecyclerView.Adapter<UserMsgAdapter.msgViewHolder>{

    private final int ITEM_SPACE = 0;
    private Context context;
    private List<bbsBean.Lists> datas = new ArrayList<>();
    private imgAdapter mImgAdapter;
    private int lineCount = 3;
    private int [] is_star ;
    private int [] is_source; //0原创 1其它

    public UserMsgAdapter(Context context, List<bbsBean.Lists> datas){
        this.context = context;
        this.datas = datas;
        changeNewConfig();
    }
    public void changeNewConfig(){
        is_star = new int[datas.size()];
        is_source = new int[datas.size()];
    }
    @Override
    public msgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0 || viewType == 3){
            lineCount = 1;
        }else if(viewType == 1 || viewType == 4){
            lineCount = 2;
        }else if(viewType == 2 || viewType == 5){
            lineCount = 3;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_bbs_msg, parent, false);
        final msgViewHolder viewHolder = new msgViewHolder(view);
        if(viewType == 0 || viewType == 1 || viewType ==2){
            viewHolder.mCardSource.setVisibility(View.GONE);
            viewHolder.mRecyleImgs.setVisibility(View.VISIBLE);
        }else{
            viewHolder.mCardSource.setVisibility(View.VISIBLE);
            viewHolder.mRecyleImgs.setVisibility(View.GONE);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final msgViewHolder holder, final int position) {
        if(is_star[holder.getAdapterPosition()] == 1){
            holder.mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_select);
        }else{
            holder.mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_default);
        }
        holder.mImgBtnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sid ;
                if(is_source[position] == 1){
                    sid = datas.get(position).getSid();
                }else{
                    sid = datas.get(position).getMid();
                }
                UiHelper.showUserForward(context,sid);
            }
        });
        holder.mImgBtnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(Conts.GET_USER_CLICK_STAR);
                params.addBodyParameter("mid", String.valueOf(datas.get(holder.getAdapterPosition()).getMid()));
                x.http().get(params, new NewCommonCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        baseBean bean = JSON.parseObject(s, baseBean.class);
                        switch (bean.getResultCode()){
                            case 1:
                                holder.mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_select);
                                is_star[holder.getAdapterPosition()] = 1;
                                break;
                            case -1:
                                holder.mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_default);
                                is_star[holder.getAdapterPosition()] = 0;
                                break;
                            case 0:
                                MyToast.showToast(context,"赞失败");
                                break;
                        }

                    }
                });
            }
        });
        if(datas.get(holder.getAdapterPosition()).getLocation() != null && datas.get(holder.getAdapterPosition()).getLocation().length() != 0){
            holder.mLayLocation.setVisibility(View.VISIBLE);
            holder.mTxtLocation.setText(datas.get(holder.getAdapterPosition()).getLocation());
        }else{
            holder.mLayLocation.setVisibility(View.GONE);
        }
        Glide.with(context).load(datas.get(position).getuHeadImg()).into(holder.mImgHead);
        holder.mName.setText(datas.get(position).getuName());
        holder.mDate.setText(datas.get(position).getDate());
//        holder.mContent.setText(datas.get(position).getContent());
        holder.mContent.setText(EmojiParser.getInstance(context).replace(datas.get(position).getContent()));
        if(datas.get(position).getImglists() != null && datas.get(position).getImglists().size()!=0){
                initRecyle(holder,position);
        }
        if(is_source[position] == 1){
            if(datas.get(position).getSourceContent() != null){
                holder.mTxtSourceContent.setText(EmojiParser.getInstance(context).replace(datas.get(position).getSourceContent()));
            }
            holder.mTxtSourceUserName.setText("@"+datas.get(position).getsName());
        }
        if(position == datas.size() - 1){
            mOnRefresh.Refresh();
        }
    }

    private void initRecyle(msgViewHolder holder, int position) {
            mImgAdapter = new imgAdapter(context,datas.get(position).getImglists());
        //如果是原创加载mRemRecyleImgs  其它加载mSourceRecyleImgs
        if(is_source[position] == 0) {
            holder.mRecyleImgs.setAdapter(mImgAdapter);
        }else {
            holder.mSourceRecyleImgs.setAdapter(mImgAdapter);
        }
    }

    //return 0原创没有图片 1原创少于4图片 2原创大于5张图片
    // 3 4 5
    @Override
    public int getItemViewType(int position) {
        if(datas.get(position).getIs_star() == 1){
            is_star[position] = 1;
        }
        if(datas.get(position).getmType() == 0){
            if(datas.get(position).getImglists().size() == 0){
                return 0;
            }else if(datas.get(position).getImglists().size() <= 4){
                return 1;
            }else{
                return 2;
            }
        }else{
            is_source[position] = 1;
            if(datas.get(position).getImglists().size() == 0){
                return 3;
            }else if(datas.get(position).getImglists().size() <= 4){
                return 4;
            }else{
                return 5;
            }
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //滑到底部加载接口
    private OnRefresh mOnRefresh ;
    public void setOnRefresh(OnRefresh mOnRefresh){
        this.mOnRefresh = mOnRefresh;
    }
    public class msgViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImgHead;
        TextView mName;
        TextView mDate ;
        TextView mContent;
        RecyclerView mRecyleImgs;
        LinearLayout mLayLocation;
        TextView mTxtLocation;
        ImageView mImgBtnStar,mImgBtnForward;
        //转发是显示
        CardView mCardSource;
        TextView mTxtSourceContent;
        TextView mTxtSourceUserName;
        RecyclerView mSourceRecyleImgs;
        public msgViewHolder(View itemView) {
            super(itemView);
            mImgHead = (CircleImageView) itemView.findViewById(R.id.img_user_bbs_msg_head);
            mName = (TextView) itemView.findViewById(R.id.txt_user_bbs_msg_username);
            mDate = (TextView) itemView.findViewById(R.id.txt_user_bbs_msg_date);
            mContent = (TextView) itemView.findViewById(R.id.txt_user_bbs_content);
            mRecyleImgs = (RecyclerView) itemView.findViewById(R.id.recyle_user_bbs_img);
            mLayLocation = (LinearLayout) itemView.findViewById(R.id.lay_item_location);
            mTxtLocation = (TextView) itemView.findViewById(R.id.txt_item_location);
            mImgBtnStar = (ImageView) itemView.findViewById(R.id.img_btn_msg_star);
            mImgBtnForward = (ImageView) itemView.findViewById(R.id.img_btn_msg_forward);
            mCardSource = (CardView) itemView.findViewById(R.id.card_source_bbs_msg);
            mTxtSourceContent = (TextView) itemView.findViewById(R.id.txt_source_bbs_content);
            mTxtSourceUserName = (TextView) itemView.findViewById(R.id.txt_source_bbs_user_name);
            mSourceRecyleImgs = (RecyclerView) itemView.findViewById(R.id.recyle_user_source_bbs_img);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(context,lineCount);
            mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mRecyleImgs.setLayoutManager(mGridLayoutManager);
            GridLayoutManager mGridLayoutManagerSource = new GridLayoutManager(context,lineCount);
            mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mSourceRecyleImgs.setLayoutManager(mGridLayoutManagerSource);
            int spacingInPixels = ITEM_SPACE;
            mRecyleImgs.addItemDecoration(new RecycleSpaceItemDecoration(spacingInPixels));
        }
    }

    private class imgAdapter extends RecyclerView.Adapter<imgAdapter.imgViewHolder>{

        private Context mContext;
        private List<bbsBean.Lists.imglists> mImgLists = new ArrayList<>();
        public imgAdapter(Context mContext,List<bbsBean.Lists.imglists> mImgLists){
            this.mContext = mContext;
            this.mImgLists = mImgLists;

        }

        @Override
        public imgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_user_bbs_item_imgs,parent,false);
            imgViewHolder viewHolder1 = new imgViewHolder(view1);
            return viewHolder1;
        }

        @Override
        public void onBindViewHolder(imgViewHolder holder, int position) {
            Glide.with(mContext).load(mImgLists.get(position).getImgsrc()).into(holder.mImgItem);
        }

        @Override
        public int getItemCount() {
            return mImgLists.size();
        }

        public class imgViewHolder extends RecyclerView.ViewHolder{
            ImageView mImgItem;
            public imgViewHolder(View itemView) {
                super(itemView);
                mImgItem = (ImageView) itemView.findViewById(R.id.img_user_bbs_item);
                ViewGroup.LayoutParams mParams = mImgItem.getLayoutParams();
                //PxDipUnti.dip2px(mContext,20)为recyler的左右magin
                mParams.width = (LPhone.getScreenWidth(mContext) - PxDipUnti.dip2px(mContext,20) - ITEM_SPACE*2)/3;
                mParams.height = mParams.width;
            }
        }
    }
}
