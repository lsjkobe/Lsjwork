package com.example.lsj.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lsj.httplibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public class NineImgShow extends BaseActivity{
    private List<Bitmap> imageBitmap = new ArrayList<>();
    private RecyclerView mRecyImg;

    @Override
    protected void initView() {
        super.initView();
        showTopView(false);
        mRecyImg = (RecyclerView) findViewById(R.id.recy_nine_img_show);
    }
    @Override
    protected void initData() {
        initRecycler();
        imageBitmap = ImageUtil.getImageList(getResources());
        mRecyImg.setAdapter(new NineImgAdapter(mContext, imageBitmap));
    }
    private void initRecycler() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyImg.setLayoutManager(mGridLayoutManager);

    }

    public class NineImgAdapter extends RecyclerView.Adapter<NineImgAdapter.myViewHolder>{

        private Context context;
        private List<Bitmap> datas;
        public NineImgAdapter(Context context, List<Bitmap> datas){
            this.context = context;
            this.datas = datas;
        }
        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_nine_img, parent, false);
            myViewHolder mViewHolder = new myViewHolder(view);
            return mViewHolder;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            Drawable drawable =new BitmapDrawable(datas.get(position));
            holder.mImgNine.setBackground(drawable);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            ImageView mImgNine ;
            public myViewHolder(View itemView) {
                super(itemView);
                mImgNine = (ImageView) itemView.findViewById(R.id.img_nine_img);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i = 0; i<9; i++){
            imageBitmap.get(i).recycle();
        }
        ImageUtil.cleanBitmap();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nine_img_show;
    }
}
