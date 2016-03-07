package lsjkobe.nineimg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/3/6.
 */
public class NineImgShow extends Activity implements View.OnClickListener{
    private List<Bitmap> imageBitmap = new ArrayList<>();
    private RecyclerView mRecyImg;
    private Context context = this;
    private String mBitmapPath;
    private int screenWidth;
    private int mWidth;
    private CardView mCardSave, mCardCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_img_show);
        mBitmapPath = getIntent().getStringExtra("bitmap");
        initView();
        initData();
    }


    protected void initView() {
        mRecyImg = (RecyclerView) findViewById(R.id.recy_nine_img_show);
        mCardSave = (CardView) findViewById(R.id.card_save_img);
        mCardCancel = (CardView) findViewById(R.id.card_cancel);
        mCardSave.setOnClickListener(this);
        mCardCancel.setOnClickListener(this);
    }
    protected void initData() {
        initRecycler();
        imageBitmap = ImageUtil.getImageList(getResources(),mBitmapPath);
        mRecyImg.setAdapter(new NineImgAdapter(context, imageBitmap));
        screenWidth = LPhone.getScreenWidth(context);
        mWidth = screenWidth/3;
        ViewGroup.LayoutParams params = mRecyImg.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth;
    }
    private void initRecycler() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyImg.setLayoutManager(mGridLayoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_save_img:
                ImageFileUtil.createDirectory(ImageFileUtil.SDPATH);
                for(int i = 0; i<9; i++){
                    ImageFileUtil.saveMyBitmap(imageBitmap.get(i), "nine_img_"+i);
                }
                /**
                 * 这样就保存好了，可是有的时候明明保存下来了，为什么进入相册时查看不到呢？
                 * 反正我是遇到这样的问题的，原来我们在保存成功后，还要发一个系统广播通知手机有图片更新，广播如下：
                  */
                ImageFileUtil.deleteFile(mBitmapPath);
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(ImageFileUtil.createDirectory(ImageFileUtil.SDPATH));
                intent.setData(uri);
                context.sendBroadcast(intent);
                finish();
                break;
            case R.id.card_cancel:
                finish();
                break;
        }
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
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = mWidth;
            params.height = mWidth;
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

}
