package com.lsj.lsjnews.mdnews;

import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.locationBean;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.utils.myLocation;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lsj on 2016/3/21.
 */
public class UserWriteActivity extends MyBaseActivity implements View.OnClickListener{
    private EditText mEditWriteContent;
    private ImageView mImgSelect,mImgLocation,mImgRelease;
    private RecyclerView mRecycleImages;
    private TextView mTxtLocation;
    private LinearLayout mLayLocation;
    private Location mLocation;

    ArrayList<String> mImgLists = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        mEditWriteContent = (EditText) findViewById(R.id.edit_write_bbs_content);
        mImgSelect = (ImageView) findViewById(R.id.img_write_bbs_select);
        mRecycleImages = (RecyclerView) findViewById(R.id.recyvle_write_bbs_image);
        mImgLocation = (ImageView) findViewById(R.id.img_write_bbs_location);
        mImgRelease = (ImageView) findViewById(R.id.img_write_bbs_release);
        mLayLocation = (LinearLayout) findViewById(R.id.lay_location);
        mTxtLocation = (TextView) findViewById(R.id.txt_location);
        mImgSelect.setOnClickListener(this);
        mImgLocation.setOnClickListener(this);
        mImgRelease.setOnClickListener(this);
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
            case R.id.img_write_bbs_location:
                mLocation = myLocation.getInstance().getLocation(mContext);
                if(mLocation == null){
                    MyToast.showToast(mContext, "无法获取地理信息");
                }else{
                    getAddressName(mLocation);
                }
                break;
            case R.id.img_write_bbs_release:
                if(mEditWriteContent.getText().length() == 0 && mEditWriteContent.getText().toString().trim().equals("")){
                    MyToast.showToast(mContext,"内容不能为空");
                }else{
                    releaseBBS();
                }
                break;
        }
    }
    private void releaseBBS(){
        RequestParams params = new RequestParams(Conts.POST_WRITE_BBS);
        params.addBodyParameter("content",mEditWriteContent.getText().toString());
//        params.addBodyParameter("imgList",mImgLists);
        params.addParameter("imgList",mImgLists);
        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                MyToast.showToast(mContext,":"+s);
                switch (s){
                    case "1":
                        MyToast.showToast(mContext,"发布成功");
                        break;
                    case "0":
                        MyToast.showToast(mContext,"发布失败");
                        break;
                    case "400":
                        MyToast.showToast(mContext,"没登录");
                        break;
                    case "401":
                        //不是post
                        break;
                }
            }
        });
    }
    //根据经纬度获取地址名（百度接口）http://api.map.baidu.com/geocoder?output=json&location=纬度,经度&key=nOUeWMWpZPfHYGkoHzGp63hL
    private void getAddressName(Location mLocation){
        RequestParams params = new RequestParams
                (Conts.BAIDU_LOCATION_API+mLocation.getLatitude()+","+mLocation.getLongitude()+Conts.BAIDU_LOCATION_KEY);
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                locationBean bean = JSON.parseObject(s, locationBean.class);
                if(bean.getStatus().equals("OK")){
                    mLayLocation.setVisibility(View.VISIBLE);
                    mTxtLocation.setText(bean.getResult().getFormatted_address());
                }else{
                    MyToast.showToast(mContext,bean.getStatus());
                }
            }
        });
    }

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
