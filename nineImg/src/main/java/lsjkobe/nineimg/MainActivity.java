package lsjkobe.nineimg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends Activity {

    private Button mBtnNine;
    private Context context = this;
    public static final int NONE = 0;
    public static final int GETFROMPHONE = 1;
    public static final int GETFROMCAMERA = 2;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mBtnNine = (Button) findViewById(R.id.btn_nine_img_show);
    }

    protected void initData() {
//        mBtnNine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(context, NineImgShow.class);
//                startActivity(mIntent);
//            }
//        });
        mBtnNine.setOnClickListener(new MyOnClickListener());
    }
    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_nine_img_show:
                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);
                    startActivityForResult(intent, GETFROMPHONE);
                    break;

            }
        }

    }

    //选择图片后的处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null)
            return;
        // 读取相册缩放图片+
        if (requestCode == GETFROMPHONE) {
            cropImage(data.getData(), 1, 1, ImageUtils.GET_IMAGE_FROM_PHONE);
        }

        if (requestCode == ImageUtils.CROP_IMAGE) {
            if (ImageUtils.cropImageUri != null) {
                //	String path = ImageUtils.getRealPathFromURI(this,ImageUtils.cropImageUri);
                String path = ImageUtils.cropImageUri.getEncodedPath();
                try {
                    Bitmap mNewUserPic = MediaStore.Images.Media.getBitmap(context.getContentResolver(), ImageUtils.cropImageUri);
                    Intent mIntent = new Intent(context, NineImgShow.class);
                    mIntent.putExtra("bitmap",path);
                    startActivity(mIntent);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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
                path = ImageUtils.getRealPathFromURI(context, srcUri);
                break;
            default:
                break;
        }

        path = ImageUtils.rotaingPic(path);
//        MyLogger.showLogWithLineNum(3, path);
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
}
