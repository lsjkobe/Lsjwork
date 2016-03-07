package lsjkobe.nineimg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;

public class ImageFileUtil {

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/nine_app/img";

	public static void saveMyBitmap(Bitmap mBitmap, String bitName) {
		saveMyBitmap(mBitmap, bitName,SDPATH);
	}
	
	
	public static void saveMyBitmap(Bitmap mBitmap, String bitName ,String filePath) {
		
		//判断sdcard是否存在?
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//			MyLogger.showLogWithLineNum(3, "sdcard不可用!");
			return ;
		};
		
		File f = new File(filePath, bitName + ".jpg");
		if (!fileIsExists(filePath)) {
			File dir = new File(filePath);
			dir.mkdirs();
		}
		
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			MyLogger.showLogWithLineNum(3, "在保存图片时出错：" + e.toString());
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void initCacheFile() {
		if (!ImageFileUtil.fileIsExists(ImageFileUtil.SDPATH)) {
			File dir = new File(ImageFileUtil.SDPATH);
			dir.mkdirs();
		}
		
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			//System.out.println("createSDDir:" + dir.getAbsolutePath());
			//System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static  void deleteFile(String filePath){
		File file = new File(filePath);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			}
		}
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}
	
	public static File createDirectory(String directoryName) {
		File newPath = null;
		if (!directoryName.equals("")) {
			newPath = new File(directoryName);
			if(!newPath.exists()){
				newPath.mkdirs();
			}
		} 
		return newPath;
	}

}