package com.lsj.lsjnews.mdnews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.utils.CircleImageView;
import com.lsj.lsjnews.utils.ImageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/5/19.
 */
public class UserUpdateMsg extends MyBaseActivity implements View.OnClickListener{
    public static final int NONE = 0;
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private CircleImageView mImgHead;
    private EditText mEditNickName, mEditStatement, mEditQQ;
    private Button mBtnSave;
    @Override
    protected void initView() {
        super.initView();
        mImgHead = (CircleImageView) findViewById(R.id.img_user_update_msg_head);
        mEditNickName = (EditText) findViewById(R.id.edit_user_update_msg_nickname);
        mEditStatement = (EditText) findViewById(R.id.edit_user_update_msg_statement);
        mEditQQ = (EditText) findViewById(R.id.edit_user_update_msg_qq);
        mBtnSave = (Button) findViewById(R.id.btn_user_update_msg_save);
        mImgHead.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.img_user_update_msg_head:
                intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTOZOOM);
                break;
            case R.id.btn_user_update_msg_save:
                break;
        }
    }
    private void saveUserMsg(){

    }

    //选择图片后的处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        if (data == null)
            return;

        // 读取相册缩放图片+
        if (requestCode == PHOTOZOOM) {
            cropImage(data.getData(), 1, 1, ImageUtils.GET_IMAGE_FROM_PHONE);
        }

        if (requestCode == ImageUtils.CROP_IMAGE) {
            if (ImageUtils.cropImageUri != null) {
                //	String path = ImageUtils.getRealPathFromURI(this,ImageUtils.cropImageUri);
                String path = ImageUtils.cropImageUri.getEncodedPath();

                File file = new File(path);
//                try {
//                    postPicToNet(file);
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                Glide.with(mContext).load(file).into(mImgHead);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropImage(Uri srcUri, int aspectX, int aspectY, int type) {
        ImageUtils.cropImageUri = ImageUtils.createImagePathUri();
        if(ImageUtils.cropImageUri == null){
            return;
        }
        String path = null;
        switch (type) {
            case ImageUtils.GET_IMAGE_BY_CAMERA:
                path = srcUri.getEncodedPath();// 获取相片的保存路径
                break;
            case ImageUtils.GET_IMAGE_FROM_PHONE:
                path = ImageUtils.getRealPathFromURI(mContext, srcUri);
                break;
            default:
                break;
        }

        path = ImageUtils.rotaingPic(path);
        MyLogger.showLogWithLineNum(3, path);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
        intent.putExtra("crop", "true");

        ////////////////////////////////////////////////////////////////
        // 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
        ////////////////////////////////////////////////////////////////
        // 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
        ////////////////////////////////////////////////////////////////
        // 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
        ////////////////////////////////////////////////////////////////
        // 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
        //	会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
        //  不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
        ////////////////////////////////////////////////////////////////
		/*
		// aspectX aspectY 是裁剪框宽高的比例
		intent.putExtra("aspectX", screenWidth);
		intent.putExtra("aspectY", height);
		// outputX outputY 是裁剪后生成图片的宽高
//       以下两个值，设置之后会按照两个值生成一个Bitmap, 两个值就是这个bitmap的横向和纵向的像素值，
//       如果裁剪的图像和这个像素值不符合，那么空白部分以黑色填充。
		intent.putExtra("outputX", screenWidth);
		intent.putExtra("outputY", height);
		*/
        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
        // return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.cropImageUri);
        intent.putExtra("return-data", false);

        startActivityForResult(intent, ImageUtils.CROP_IMAGE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_update_msg;
    }
}
