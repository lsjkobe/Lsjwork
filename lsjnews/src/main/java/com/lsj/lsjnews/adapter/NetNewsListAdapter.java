package com.lsj.lsjnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.bean.LsjNewsBean;
import com.lsj.lsjnews.common.UiHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;

/**
 * Created by lsj on 2016/3/1.
 */
public class NetNewsListAdapter extends RecycleViewBaseAdapter<LsjNewsBean> {

    public NetNewsListAdapter(Context context, List<LsjNewsBean> datas, boolean is_show_anim) {
        super(context, datas,is_show_anim);
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_sport_news;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getItemLayoutId(viewType), parent, false);
        final NewsViewHolder viewHolder = new NewsViewHolder(view);
        if(viewType == 0){
            viewHolder.mImgNews.setVisibility(View.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datas.get(viewHolder.getAdapterPosition()).getPostid() != null){
                    UiHelper.showNewsInfoThis(context, datas.get(viewHolder.getAdapterPosition()).getPostid(),datas.get(viewHolder.getAdapterPosition()).getImgsrc(),v);
//                    UiHelper.showTest(context);
                }else{
                    MyToast.showToast(context, "新闻出现异常");
                }
            }

        });
        return viewHolder;
//        switch (viewType){
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//        }
    }

    @Override
    public int getItemViewType(int position) {

        if(datas.get(position).getImgsrc() == null){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public void initData(NewsViewHolder holder, int position, LsjNewsBean item) {
        holder.mTextView.setText(item.getTitle());
        holder.mTxtDate.setText(item.getPtime());
        ImageLoader.getInstance().displayImage(item.getImgsrc(),holder.mImgNews);
        if(item.getTAG() != null){
            if(item.getTAG().equals("视频")){
                holder.mTxtVideoTags.setText(item.getTAG());
            }
        }else{
            holder.mTxtVideoTags.setText("");
        }

    }
}
