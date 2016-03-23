package com.lsj.lsjnews.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.ExifInterface;
import android.os.Environment;

import com.example.lsj.httplibrary.utils.MyLogger;

/**
 * 图片压缩类
 * @author zwk
 */
public class PicureCompressUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory()+ "/formats/";
	private static final int oneM=1024*1024;
	
	/**
	 * 压缩图片保存到本地
	 * @return 压缩过后图片的File
	 */
	public static File compressFile(String pathString) {
		long mNewFilePath =System.currentTimeMillis(); 
		File file = new File(SDPATH,mNewFilePath + ".jpg");//目标路径
		PointF pf=getImgWidthHeight(pathString);		   //获取宽高
		float scale=pf.y/pf.x;	//宽高比例       高/宽>2:长图   
		MyLogger.showLogWithLineNum(3, "scale..." + scale);
		if(scale>2){//长图
			int finalSize=(int) (pf.y/15);//单位kb
			MyLogger.showLogWithLineNum(3, "finalSize..."+finalSize);
			Bitmap mBitmap=null;
			if(pf.x>440){//宽度大于440处理
				 mBitmap=getSmallBitmap(pathString,440,(int)pf.y);
			}else{
				 mBitmap=BitmapFactory.decodeFile(pathString);
			}
			
			if(finalSize>oneM){//最大限制1M
				finalSize=oneM;
			}
			compressImage(mBitmap, file, finalSize);//压缩处理
		}else if(scale<0.5){//宽图
			int finalSize=(int) (pf.x/15);//单位kb
			MyLogger.showLogWithLineNum(3, "finalSize..."+finalSize);
			Bitmap mBitmap=BitmapFactory.decodeFile(pathString);
			if(finalSize>oneM){//最大限制1M
				finalSize=oneM;
			}
			compressImage(mBitmap, file, finalSize);//压缩处理
		}
		else{//其它，普通
			Bitmap mBitmap = getSmallBitmap(pathString,1600, 900); 
			 //再对图片进行质量压缩 
			compressImage(mBitmap,file,100);
		}
		return file;
	}
	/**
	 * 旋转图片一定角度 rotaingImageView
	 *
	 * @return Bitmap
	 * @throws
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		return resizedBitmap;
	}
	/**
	 * 根据路径获得图片并压缩返回bitmap用于显示
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int w, int h) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 该值设为true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
			options.inJustDecodeBounds = true;// inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
			options.inPreferredConfig = Config.RGB_565;
			BitmapFactory.decodeFile(filePath, options);
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, w, h);
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;// 重新读入图片，注意这次要把options.inJustDecodeBounds
												// 设为 false
			Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);// BitmapFactory.decodeFile()按指定大小取得图片缩略图
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.reset();// 重置baos即清空baos
			// 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int degree = readPictureDegree(filePath);
			if (degree != 0){
				bitmap = rotaingImageView(degree, bitmap);
			}
			return bitmap;
		} catch (OutOfMemoryError e) {
			Bitmap bitmap = getSmallBitmap(filePath, w * 2 / 3, h * 2 / 3);
			return bitmap;
		}
	}
	/**
	 *
	 * 读取图片属性：旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;

	}
	/**
	 * 计算图片的缩放值 如果图片的原始高度或者宽度大与我们期望的宽度和高度，我们需要计算出缩放比例的数值。否则就不缩放。
	 * heightRatio是图片原始高度与压缩后高度的倍数， widthRatio是图片原始宽度与压缩后宽度的倍数。
	 * inSampleSize就是缩放值 ，取heightRatio与widthRatio中最小的值。
	 * inSampleSize为1表示宽度和高度不缩放，为2表示压缩后的宽度与高度为原来的1/2(图片为原1/4)。
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		
		MyLogger.showLogWithLineNum(3, "order:"+reqWidth+"   "+reqHeight);
		
		final int height = options.outHeight;
		final int width  = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height/ (float) reqHeight);
			final int widthRatio  = Math.round((float) width / (float) reqWidth);
			MyLogger.showLogWithLineNum(3,"heightRatio:"+heightRatio+"   widthRatio:"+widthRatio);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			MyLogger.showLogWithLineNum(3,"inSampleSize:"+inSampleSize);
		}

		return inSampleSize;
	}

	
	
	/**对图片进行压缩处理
	 * @param mBitmap	原bitmap
	 * @param file		保存文件
	 * @param finalSize 单位kb
	 */
	public static void compressImage(Bitmap mBitmap,File file,int finalSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		int size = baos.toByteArray().length / 1024;		//单位kb
		MyLogger.showLogWithLineNum(3, "all size..."+size);
		while (size > finalSize && options > 0) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			size = baos.toByteArray().length / 1024;
			MyLogger.showLogWithLineNum(3, "new size..."+size);
			MyLogger.showLogWithLineNum(3, "options..."+options);
			options -= 10;// 每次都减少10
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param filePath
	 * @return 高/宽
	 */
	private static PointF getImgWidthHeight(String filePath) {
		PointF pf=new PointF(0,0);//存放宽高
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;// inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
			options.inPreferredConfig = Config.RGB_565;
			BitmapFactory.decodeFile(filePath, options);
			options.inJustDecodeBounds = false;
			pf.x=options.outWidth;
			pf.y=options.outHeight;
			MyLogger.showLogWithLineNum(3, "img width:"+pf.x+"\n img height:"+pf.y);
			return pf;
		} catch (OutOfMemoryError o){
			return pf;
		}
	}

}
