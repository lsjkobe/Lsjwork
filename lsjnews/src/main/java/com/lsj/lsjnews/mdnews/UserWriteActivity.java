package com.lsj.lsjnews.mdnews;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.common.UiHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class UserWriteActivity extends MyBaseActivity implements View.OnClickListener{
    private EditText mEditWriteContent;
    private ImageView mImgSelect;
    private RecyclerView mRecycleImages;
    @Override
    protected void initView() {
        super.initView();
        mEditWriteContent = (EditText) findViewById(R.id.edit_write_bbs_content);
        mImgSelect = (ImageView) findViewById(R.id.img_write_bbs_select);
        mRecycleImages = (RecyclerView) findViewById(R.id.recyvle_write_bbs_image);
        mImgSelect.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleImages.setLayoutManager(mLinearLayoutManager);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_write_bbs_select:
                UiHelper.showSelectPhoto(mContext);
                break;
        }
    }

    ArrayList<String> mImgLists = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            mImgLists = data.getStringArrayListExtra("imgList");
            if(mImgLists == null){
                mRecycleImages.setVisibility(View.GONE);
            }else{
                mRecycleImages.setVisibility(View.VISIBLE);
                mRecycleImages.setAdapter(new writePhotoAdapter(mImgLists));
            }

        }
    }

    private class writePhotoAdapter extends RecyclerView.Adapter<writePhotoAdapter.writeViewHolder>{
        private List<String> datas;
        public writePhotoAdapter(List<String> datas){
            this.datas = datas;
        }
        @Override
        public writeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo_write,null);
            final writeViewHolder viewHolder = new writeViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final writeViewHolder holder, final int position) {
            Glide.with(mContext).load(datas.get(position)).into(holder.mImgPhoto);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class writeViewHolder extends RecyclerView.ViewHolder{
            ImageView mImgPhoto;
            ImageView mImgDelete;
            public writeViewHolder(View itemView) {
                super(itemView);
                mImgDelete = (ImageView) itemView.findViewById(R.id.img_write_photo_delete);
                mImgPhoto = (ImageView) itemView.findViewById(R.id.img_write_photo_item);
                ViewGroup.LayoutParams mParams = mImgPhoto.getLayoutParams();
                mParams.width = LPhone.getScreenWidth(mContext) / 4;
                mParams.height = mParams.width;
                ViewGroup.LayoutParams mParams1 = mImgDelete.getLayoutParams();
                mParams1.width = mParams.width / 4;
                mParams1.height = mParams1.width;
            }
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_bbs;
    }
}
