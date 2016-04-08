package com.lsj.lsjnews.mdnews;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.utils.RecycleSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lsj on 2016/3/21.
 */
public class UserSelectPhoto extends MyBaseActivity{
    private final int PHOTO_COUNT = 9;
    private final int LOAD_FINISH = 1;
    private List<String> mPhotoLists = new ArrayList<>();
    private int [] is_select ;
    private RecyclerView mRecyclePhoto;
    private Toolbar mToolbar;
    private int allCount = 0;
    private TextView mTxtCount;
    @Override
    protected void initView() {
        super.initView();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_select_photo);
        mRecyclePhoto = (RecyclerView) findViewById(R.id.recycler_select_photo);
        mTxtCount = (TextView) findViewById(R.id.txt_select_photo_count);
    }

    @Override
    protected void initData() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclePhoto.setLayoutManager(mGridLayoutManager);
        int spacingInPixels = 2;
        mRecyclePhoto.addItemDecoration(new RecycleSpaceItemDecoration(spacingInPixels));
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setTitle("选择图片");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
        getImages();
    }
    private void getImages() {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            MyToast.showToast(mContext, "暂无外部存储");
            return ;
        }

        //baseManager.showProgressbar();

        new Thread(new Runnable() {

            @Override
            public void run() {
                ContentResolver mContentResolver = mContext.getContentResolver();
//                Uri mUri = Uri.parse("content://media/external/images/media");
//                Uri mImageUri = null;
                Cursor cursor = mContentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String data = cursor.getString(cursor
                            .getColumnIndex(MediaStore.MediaColumns.DATA));
                    MyLogger.showLogWithLineNum(3,"==============imgsrc:"+data);
                    mPhotoLists.add(data);
                    cursor.moveToNext();
                }
                mHandler.sendEmptyMessage(LOAD_FINISH);
            }
        }).start();
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == LOAD_FINISH){
                if(mPhotoLists != null){
                    mRecyclePhoto.setAdapter(new selectPhotoAdapter(mPhotoLists));
                }

            }
        }
    };

    public void setToolbarCenterCount(int count){
        mTxtCount.setText(count+"/"+PHOTO_COUNT);
    }
    private class selectPhotoAdapter extends RecyclerView.Adapter<selectPhotoAdapter.photoViewHolder>{
        private List<String> datas;
        public selectPhotoAdapter(List<String> datas){
            this.datas = datas;
            //data倒序
            Collections.reverse(datas);
            is_select = new int[datas.size()];
        }
        @Override
        public photoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_photo,null);
            final photoViewHolder viewHolder = new photoViewHolder(view);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final photoViewHolder holder, final int position) {
            Glide.with(mContext).load(datas.get(position)).into(holder.mImgPhoto);
            //checkbox  复用问题
            if (isHasSelect()) {
                holder.mCheckBox.setChecked((is_select[holder.getAdapterPosition()] == 1) ? true : false);
            } else {
                holder.mCheckBox.setChecked(false);
            }
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        allCount++;
                        if(allCount > PHOTO_COUNT){
                            allCount--;
                            holder.mCheckBox.setChecked(false);
                            MyToast.showToast(mContext,"选择不能超过"+PHOTO_COUNT+"张图片");
                            for(int i=0; i<is_select.length; i++){
                                MyLogger.showLogWithLineNum(3,"----------------:"+is_select[i]);
                                setToolbarCenterCount(allCount);
                            }
                        }else{
                            is_select[holder.getAdapterPosition()] = 1;
                            setToolbarCenterCount(allCount);
                        }
                    }else{
                        allCount--;
                        is_select[holder.getAdapterPosition()] = 0;
                    }

                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class photoViewHolder extends RecyclerView.ViewHolder{
            CheckBox mCheckBox;
            ImageView mImgPhoto;

            public photoViewHolder(View itemView) {
                super(itemView);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.check_photo_select);
                mImgPhoto = (ImageView) itemView.findViewById(R.id.img_select_photo_item);
                ViewGroup.LayoutParams mParams = mImgPhoto.getLayoutParams();
                mParams.width = LPhone.getScreenWidth(mContext) / 3;
                mParams.height = mParams.width;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_photo, menu);
        return true;
    }
    private ArrayList<String> mImgLists = new ArrayList<>();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_select_photo){
            setImgList();
            if(mImgLists.size() == 0){
                MyToast.showToast(mContext,"没有选择图片");
            }else{
                Intent mIntent = getIntent();
                mIntent.putStringArrayListExtra("imgList",mImgLists);
                setResult(1,mIntent);
                AppManager.getAppManager().finishActivity();
            }

        }

        return super.onOptionsItemSelected(item);
    }
    private void setImgList(){
        for(int i=0; i<is_select.length; i++){
            if(is_select[i] == 1){
                mImgLists.add(mPhotoLists.get(i));
            }
        }
    }
    private boolean isHasSelect(){
        for(int i=0; i<is_select.length; i++){
            if(is_select[i] == 1){
                return true;
            }
        }
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_select_photo;
    }
}
