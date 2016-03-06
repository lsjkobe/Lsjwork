package lsjkobe.nineimg;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class ImageUtils {
	
	public static final int GET_IMAGE_BY_CAMERA = 5001;
	public static final int GET_IMAGE_FROM_PHONE = 5002;
	public static final int CROP_IMAGE = 5003;
	public static Uri imageUriFromCamera;
	public static Uri cropImageUri;

	/**
	 * 拍照
	 * @param activity
	 */
	public static void openCameraImage(final Activity activity) {
		ImageUtils.imageUriFromCamera = createImagePathUri();
		if(ImageUtils.imageUriFromCamera == null){
//			MyToast.showToast(activity, "imageUriFromCamera == null");
			return ;
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
		// 返回图片在onActivityResult中通过以下代码获取
		// Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtils.imageUriFromCamera);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);
	}

	/**
	 * 相册
	 * @param activity
	 */
	public static void openLocalImage(final Activity activity) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE);
	}
	/**
	 * 对图片进行裁剪
	 * @param activity
	 * @param srcUri
	 */
	public static void cropImage(Activity activity, Uri srcUri,int type) {
		cropImage(activity, srcUri,0,0,type);
	}



	public static void cropImage(Activity activity, Uri srcUri,int aspectX,int aspectY,int type) {
		ImageUtils.cropImageUri = createImagePathUri();
		if(ImageUtils.cropImageUri == null){
			return;
		}
		String path = null;
		switch (type) {
		case ImageUtils.GET_IMAGE_BY_CAMERA:
			path = srcUri.getEncodedPath();// 获取相片的保存路径
			break;
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			path = getRealPathFromURI(activity, srcUri);//获得手机相册的保存路径
			break;
		default:
			break;
		}

		path = ImageUtils.rotaingPic(path);
//		MyLogger.showLogWithLineNum(3, path);

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

		activity.startActivityForResult(intent, CROP_IMAGE);
	}

	/**
	 * 2015 5.22
	 * 有时间一定要修改。这个功能一直崩溃
	 * 创建一条图片地址uri,用于保存拍照后的照片
	 *
	 * @param context
	 * @return 图片的uri
	 *//*
	public static Uri createImagePathUri(Context context) {
		Uri imageFilePath = null;
		String status = Environment.getExternalStorageState();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		long time = System.currentTimeMillis();
		String imageName = timeFormatter.format(new Date(time));
		// ContentValues是我们希望这条记录被创建时包含的数据信息
		ContentValues values = new ContentValues(3);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
		values.put(MediaStore.Images.Media.DATE_TAKEN, time);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} else {
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
			MyToast.showToast(context, "请插入SD卡");
			return null;
		}
		Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
		return imageFilePath;
	}
	*/
	/**
	 * 改用保存到自定义的路径地址上(2015-5-25)
	 * 创建一条图片地址uri,用于保存拍照后的照片
	 * @return
	 */
	public static Uri createImagePathUri(){
		ImageFileUtil.initCacheFile();
		File dir = new File(ImageFileUtil.SDPATH);
		File localFile = new File(dir,TimeUtil.getTimeMillis()+".jpg");
		return Uri.fromFile(localFile);
	}

	/**
	 * 从数据库中取出图片的SD卡地址
	 * @param context
	 * @param contentUri
	 * @return
	 */
	public static String getRealPathFromURI(Context context,Uri contentUri) {
		try { 
			String[] proj = { Images.Media.DATA };
	        Cursor cursor = ((Activity) context).managedQuery(contentUri, proj, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
		}catch(Exception e){
			 e.printStackTrace();  
			return null;
		}
	}

	
	/**
	 * 有些图片的地址名搜索不出来,报错，先保留这个问题
	 * android.database.sqlite.SQLiteException: near "细细粒ߑĢ: syntax error (code 1): , while compiling: SELECT _id FROM images WHERE ((_data='/storage/emulated/0/Pictures/嘛勒佬'细细粒👄/20140628_222915.jpg'))
	 * 将String转换为url
	 * @param context
	 * @param path
	 * @return
	 */
	public static Uri getURIFromPath(Context context,String path){
		
		    if (path != null) {
		        path = Uri.decode(path);
		        ContentResolver cr = context.getContentResolver();
		        StringBuffer buff = new StringBuffer();
		        buff.append("(")
		                .append(Images.ImageColumns.DATA)
		                .append("=")
		                .append("'" + path + "'")
		                .append(")");
		        Cursor cur = cr.query(
		                Images.Media.EXTERNAL_CONTENT_URI,
		                new String[] { Images.ImageColumns._ID },
		                buff.toString(), null, null);
		        int index = 0;
		        for (cur.moveToFirst(); !cur.isAfterLast(); cur
		                .moveToNext()) {
		            index = cur.getColumnIndex(Images.ImageColumns._ID);
		            // set _id value
		            index = cur.getInt(index);
		        }
		        cur.close();
		        if (index == 0) {
		            //do nothing
		        } else {
		            Uri uri_temp = Uri
		                    .parse("content://media/external/images/media/"
		                            + index);
		            
		            if (uri_temp != null) {
		                return uri_temp;
		            }
		        }
		    }
			return null;
		
	}
	
	/**
	 * 将图片旋转到正常角度
	 */
	public static String rotaingPic(String path){
		int degree = PhotoUtil.readPictureDegree(path);
		if(degree != 0){			Bitmap bitmap = PhotoUtil.getimage(path);
			bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
			 try {
				 if (!ImageFileUtil.fileIsExists(ImageFileUtil.SDPATH)) {
						File dir = new File(ImageFileUtil.SDPATH);
						dir.mkdirs();
					}

				 File localFile1 = new File(ImageFileUtil.SDPATH, System.currentTimeMillis() + ".jpg");
				 BufferedOutputStream localBufferedOutputStream1 = new BufferedOutputStream(new FileOutputStream(localFile1));
				 bitmap.compress(Bitmap.CompressFormat.JPEG, 90, localBufferedOutputStream1);
				 localBufferedOutputStream1.flush();
				 localBufferedOutputStream1.close();
				 bitmap.recycle();
			     String str = localFile1.getAbsolutePath();
			     return str;
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
		}else
		   return path;
	}
	
}

