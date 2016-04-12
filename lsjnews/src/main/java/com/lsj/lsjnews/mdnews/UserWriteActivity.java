package com.lsj.lsjnews.mdnews;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.AppManager;
import com.example.lsj.httplibrary.utils.LPhone;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.EmojiAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.baseBean;
import com.lsj.lsjnews.bean.mdnewsBean.locationBean;
import com.lsj.lsjnews.common.UiHelper;
import com.lsj.lsjnews.http.Conts;
import com.lsj.lsjnews.interfaces.OnEmojiOnclick;
import com.lsj.lsjnews.utils.EmojiParser;
import com.lsj.lsjnews.view.LsjLoadingView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/21.
 */
public class UserWriteActivity extends MyBaseActivity implements View.OnClickListener{
    private EditText mEditWriteContent;
    private ImageView mImgSelect, mImgLocation, mImgRelease, mImgEmoji;
    private RecyclerView mRecycleImages;
    private RecyclerView mRecycleEmoji;
    private EmojiAdapter mEmojiAdapter;
    private TextView mTxtLocation;
    private LinearLayout mLayLocation;
    private LsjLoadingView mLoading;
    private Toolbar mToolbar;
    ArrayList<String> mImgLists = new ArrayList<>();
    private LocationManager locationManager;
    private myLocationListener locationListener;

    private boolean is_emoji_open = false;
    @Override
    protected void initView() {
        super.initView();
        mEditWriteContent = (EditText) findViewById(R.id.edit_write_bbs_content);
        mImgSelect = (ImageView) findViewById(R.id.img_write_bbs_select);
        mRecycleImages = (RecyclerView) findViewById(R.id.recyvle_write_bbs_image);
        mRecycleEmoji = (RecyclerView) findViewById(R.id.recycler_emoji_select);
        mImgLocation = (ImageView) findViewById(R.id.img_write_bbs_location);
        mImgRelease = (ImageView) findViewById(R.id.img_write_bbs_release);
        mImgEmoji = (ImageView) findViewById(R.id.img_write_bbs_emoji);
        mLayLocation = (LinearLayout) findViewById(R.id.lay_location);
        mTxtLocation = (TextView) findViewById(R.id.txt_location);
        mLoading = (LsjLoadingView) findViewById(R.id.loading_write_bbs);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_write_bbs_main);
        mImgSelect.setOnClickListener(this);
        mImgLocation.setOnClickListener(this);
        mImgRelease.setOnClickListener(this);
        mImgEmoji.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initToolbar();
        initRecycler();
        mEditWriteContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    is_emoji_open = false;
                    mRecycleEmoji.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleImages.setLayoutManager(mLinearLayoutManager);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext,3);
        mGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecycleEmoji.setLayoutManager(mGridLayoutManager);
        mEmojiAdapter = new EmojiAdapter(mContext, EmojiParser.DEFAULT_EMOJI_RES_IDS);
        mEmojiAdapter.setOnEmojiOnclick(new OnEmojiOnclick() {
            @Override
            public void getPosition(int position) {
//                MyToast.showToast(mContext,position+"");
//                SpannableString spannableString = new SpannableString(EmojiParser.strEmojis[position]);
//                Drawable drawable = getResources().getDrawable(EmojiParser.DEFAULT_EMOJI_RES_IDS[position]);
//                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//                ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
//                spannableString.setSpan(span,0,EmojiParser.strEmojis[position].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                int currentPosition = mEditWriteContent.getSelectionStart();
                mEditWriteContent.getText().insert(currentPosition,EmojiParser.getInstance(mContext).edittextReplace(position,mEditWriteContent.getTextSize()));
//                Toast.makeText(mContext,""+mEditWriteContent.getText(),Toast.LENGTH_LONG).show();
            }
        });
        mRecycleEmoji.setAdapter(mEmojiAdapter);

    }


    private void initToolbar() {
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setTitle("发圈子");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    private boolean is_location = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_write_bbs_select:
                UiHelper.showSelectPhoto(mContext,9);
                break;
            case R.id.img_write_bbs_location:
//                myLocation.getInstance().getLocation(mContext);
                if(!is_location){
                    getLocation(mContext);
                }else{
                    //二次点击清空并隐藏
                    mLayLocation.setVisibility(View.GONE);
                    mTxtLocation.setText("");
                    is_location = false;
                }
                break;
            case R.id.img_write_bbs_release:
                if (mEditWriteContent.getText().length() == 0 && mEditWriteContent.getText().toString().trim().equals("")) {
                    MyToast.showToast(mContext, "内容不能为空");
                } else {
                    releaseBBS();
                }
                break;
            case R.id.img_write_bbs_emoji:
                if(is_emoji_open){
                    is_emoji_open = false;
                    mRecycleEmoji.setVisibility(View.GONE);
                }else{
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditWriteContent.clearFocus();
                    is_emoji_open = true;
                    mRecycleEmoji.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void releaseBBS() {
        RequestParams params = new RequestParams(Conts.POST_WRITE_BBS);
        params.addBodyParameter("content", mEditWriteContent.getText().toString());
        params.addBodyParameter("location",mTxtLocation.getText().toString());
        if (mImgLists != null && mImgLists.size() != 0) {
            for (int i = 0; i < mImgLists.size(); i++) {
                File imgFile = new File(mImgLists.get(i));
                params.addParameter("imgList[" + i + "]", imgFile);
                MyLogger.showLogWithLineNum(3, "------------------" + mImgLists.get(i));
            }
        } else {
            params.addParameter("imgList", null);
        }

        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                mLoading.setVisibility(View.VISIBLE);
                mLoading.startLoadingAnim();
            }

            @Override
            public void onLoading(long total, long current, boolean b) {
                MyLogger.showLogWithLineNum(3, "total:current:" + current);
            }

            @Override
            public void onSuccess(String s) {
                MyLogger.showLogWithLineNum(3, "---" + s);
                baseBean bean = JSON.parseObject(s, baseBean.class);
                switch (bean.getResultCode()) {
                    case 1:
                        MyToast.showToast(mContext, "发布成功");
                        setResult(1);
                        AppManager.getAppManager().finishActivity();
                        break;
                    case 0:
                        MyToast.showToast(mContext, "发布失败");
                        break;
                    case 400:
                        MyToast.showToast(mContext, "没登录");
                        break;
                    case 401:
                        //不是post
                        break;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                mLoading.setVisibility(View.GONE);
                mLoading.clearAnimation();
            }
        });
    }

    //根据经纬度获取地址名（百度接口）http://api.map.baidu.com/geocoder?output=json&location=纬度,经度&key=nOUeWMWpZPfHYGkoHzGp63hL
    private void getAddressName(Location mLocation) {
        RequestParams params = new RequestParams
                (Conts.BAIDU_LOCATION_API + mLocation.getLatitude() + "," + mLocation.getLongitude() + Conts.BAIDU_LOCATION_KEY);
        x.http().get(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {
                locationBean bean = JSON.parseObject(s, locationBean.class);
                if (bean.getStatus().equals("OK")) {
                    is_location = true;
                    mLayLocation.setVisibility(View.VISIBLE);
                    mTxtLocation.setText(bean.getResult().getFormatted_address());
                } else {
                    MyToast.showToast(mContext, bean.getStatus());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            mImgLists = data.getStringArrayListExtra("imgList");
            if (mImgLists == null) {
                mRecycleImages.setVisibility(View.GONE);
            } else {
                mRecycleImages.setVisibility(View.VISIBLE);
                mRecycleImages.setAdapter(new writePhotoAdapter(mImgLists));
            }

        }
    }

    private class writePhotoAdapter extends RecyclerView.Adapter<writePhotoAdapter.writeViewHolder> {
        private List<String> datas;

        public writePhotoAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public writeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo_write, null);
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

        public class writeViewHolder extends RecyclerView.ViewHolder {
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
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }

    public void openGPSSettings(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            MyToast.showToast(context, "GPS模块正常");
            return;
        }
        MyToast.showToast(context, "请开启GPS！");
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    //获取地理位置
    public void getLocation(final Context context) {
        openGPSSettings(context);
        // 获取位置管理服务

        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) context.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(new Criteria(), true); // 获取GPS信息
//        MyToast.showToast(context,"provider："+provider);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米

        locationListener = new myLocationListener();
        locationManager.requestLocationUpdates(provider, 10 * 1000, 100, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    private class myLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if(!is_location){
                if(location == null){
                    MyToast.showToast(mContext,"获取地理位置失败");
                }else{
                    getAddressName(location);
                }

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_bbs;
    }
}
