package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.utils.PxDipUnti;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import com.lsj.lsjnews.common.MyHelper;
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
 * Created by lsj on 2016/3/16.
 */
public class UserMsgAdapter extends RecyclerView.Adapter<UserMsgAdapter.msgViewHolder>{

    public static final int ITEM_SPACE = 0;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showBBSDetailMsg(context,datas.get(viewHolder.getAdapterPosition()), 0);
            }
        });

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
        holder.mImgHead.setOnClickListener(new mOnClickListener(holder,position));
        holder.mName.setOnClickListener(new mOnClickListener(holder,position));
        holder.mTxtSourceUserName.setOnClickListener(new mOnClickListener(holder,position));
        holder.mImgBBSMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBBSMoreWindow(holder, position);
            }
        });
        //转发
        holder.mImgBtnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showUserForward(context,datas.get(position));
            }
        });
        //评论
        holder.mImgBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showComment(context,datas.get(position).getMid());
            }
        });
        //点赞
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
                                datas.get(position).setIs_star(1);
                                is_star[holder.getAdapterPosition()] = 1;
                                datas.get(position).setmStar(datas.get(position).getmStar()+1);
                                notifyDataSetChanged();

                                break;
                            case -1:
                                holder.mImgBtnStar.setBackgroundResource(R.mipmap.ic_star_default);
                                datas.get(position).setIs_star(0);
                                is_star[holder.getAdapterPosition()] = 0;
                                datas.get(position).setmStar(datas.get(position).getmStar()-1);
                                notifyDataSetChanged();

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

        holder.mTxtForward.setText(""+datas.get(position).getmForwardCount());
        holder.mTxtComment.setText(""+datas.get(position).getmCommentCount());
        holder.mTxtStar.setText(""+datas.get(position).getmStar());
        if(datas.get(position).getImglists() != null && datas.get(position).getImglists().size()!=0){
                initRecyle(holder,position);
        }
        if(is_source[position] == 1){
            //进入转发源圈子
            holder.mCardSource.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UiHelper.showBBSDetailMsg(context,datas.get(position), 1);
                }
            });
            if(datas.get(position).getSourceContent() != null){
                holder.mTxtSourceContent.setText(EmojiParser.getInstance(context).replace(datas.get(position).getSourceContent()));
            }
            holder.mTxtSourceUserName.setText("@"+datas.get(position).getsName());
        }
        if(position == datas.size() - 1){
            mOnRefresh.Refresh();
        }
    }

    private void showBBSMoreWindow(msgViewHolder viewHolder, int position){
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_bbs_main_more_menu, null);
        CardView mCardCollect = (CardView) contentView.findViewById(R.id.card_bbs_main_more_menu_collect);
        CardView mCardReport = (CardView) contentView.findViewById(R.id.card_bbs_main_more_menu_report);
        PopupWindow popWnd = new PopupWindow (contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCardCollect.setOnClickListener(new myOnclickListener(popWnd, position));
        mCardReport.setOnClickListener(new myOnclickListener(popWnd, position));
        popWnd.setAnimationStyle(R.style.popwin_anim_style);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        popWnd.showAsDropDown(viewHolder.mImgBBSMore,-30,0);
//        }
    }
    private class myOnclickListener implements View.OnClickListener {
        PopupWindow popWnd;
        int position;
        public myOnclickListener(PopupWindow popWnd, int position){
            this.popWnd = popWnd;
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_bbs_main_more_menu_collect:
                    //收藏
                    RequestParams params = new RequestParams(Conts.POST_BBS_COLLECT);
                    params.addBodyParameter("mid",String.valueOf(datas.get(position).getMid()));
                    x.http().post(params, new NewCommonCallBack() {
                        @Override
                        public void onSuccess(String s) {
                            baseBean bean = JSON.parseObject(s, baseBean.class);
                            MyLogger.showLogWithLineNum(3,"--------------:"+s);
                            if(bean.getResultCode() == 1){
                                MyToast.showToast(context,"成功");
                            }else{
                                MyToast.showToast(context,"失败");
                            }
                        }
                    });
                    popWnd.dismiss();
                    break;
                case R.id.card_bbs_main_more_menu_report:
                    //举报
                    popWnd.dismiss();
                    break;
            }
        }
    }
    //用户界面跳转监听
    private class mOnClickListener implements View.OnClickListener{
        msgViewHolder holder;
        int position;
        public mOnClickListener(msgViewHolder holder, int position){
            this.holder = holder;
            this.position = position;
        }
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.img_user_bbs_msg_head:
                    UiHelper.showOtherUserMain(context,datas.get(position).getUid());
                    break;
                case R.id.txt_user_bbs_msg_username:
                    UiHelper.showOtherUserMain(context,datas.get(position).getUid());
                    break;
                case R.id.txt_source_bbs_user_name:
                    UiHelper.showOtherUserMain(context,datas.get(position).getSuid());
                    break;
                case R.id.card_bbs_main_more_menu_collect:
                    UiHelper.showOtherUserMain(context,datas.get(position).getSuid());
                    break;
                case R.id.card_bbs_main_more_menu_report:
                    UiHelper.showOtherUserMain(context,datas.get(position).getSuid());
                    break;
            }
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
        ImageView mImgBtnStar,mImgBtnForward,mImgBtnComment;
        TextView mTxtForward,mTxtComment,mTxtStar;
        ImageView mImgBBSMore;
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
            mImgBtnComment = (ImageView) itemView.findViewById(R.id.img_btn_msg_comment);
            mCardSource = (CardView) itemView.findViewById(R.id.card_source_bbs_msg);
            mTxtSourceContent = (TextView) itemView.findViewById(R.id.txt_source_bbs_content);
            mTxtSourceUserName = (TextView) itemView.findViewById(R.id.txt_source_bbs_user_name);
            mSourceRecyleImgs = (RecyclerView) itemView.findViewById(R.id.recyle_user_source_bbs_img);
            mTxtForward = (TextView) itemView.findViewById(R.id.txt_btn_msg_forward_count);
            mTxtComment = (TextView) itemView.findViewById(R.id.txt_btn_msg_comment_count);
            mTxtStar = (TextView) itemView.findViewById(R.id.txt_btn_msg_star_count);
            mImgBBSMore = (ImageView) itemView.findViewById(R.id.img_user_bbs_msg_more);
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

    public static class imgAdapter extends RecyclerView.Adapter<imgAdapter.imgViewHolder>{

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
