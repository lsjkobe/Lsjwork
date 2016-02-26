package com.example.lsj.httplibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

/**
 * @Title: FileUtil.java
 * @Package cn.dxb.app.common
 * @Description: 文件工具类
 * @author xiaoluo
 * @date 2014年10月21日 下午4:02:27
 * @version V1.0
 */
public class FileUtil {

	// 图片文件夹
	public static String FolderImgName = "pic";

	/**
	 * 获取图片路径
	 * 
	 * @param imgName
	 * @return
	 */
	public static String getImagePath(String imgName) {
		createFolder(FolderImgName);
		String ImagePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/yao/" + FolderImgName + "/";
		return ImagePath + imgName;
	};

	/**
	 * 创建文件夹
	 * 
	 * @return
	 */
	public static File createFolder(String name) {
		File fileDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			fileDir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/yao/" + name + "/");

			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		return fileDir;
	}

	/***
	 * 创建用户本地图片文件
	 */
	public static File createApkFile(String name) {
		File fileDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File apkDir = createFolder("apk");
			if (apkDir != null) {
				fileDir = new File(apkDir + "/" + name);

				if (!fileDir.exists()) {
					try {
						fileDir.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return fileDir;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 判断apk文件是否存在
	 * 
	 * @param name
	 *            文件名
	 * @return
	 */
	public static boolean isApkExit(String name) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/my00/apk/" + name);
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断sd卡是否可用
	 * 
	 * @return
	 */
	public static boolean isSDcard() {
		return Environment.MEDIA_MOUNTED
				.equals(Environment.getExternalStorageState());
	}

	public static boolean CopyImage(String oldPath, String newPath) {
		
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			createFile(newPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1280];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				return true;
			}
			return false;
		} catch (Exception e) {
			MyLogger.showLogWithLineNum(3, "保存图片CopyImage出错");
			e.printStackTrace();
			return false;
		}
	}

    public static boolean createFile(String destFileName) {  
        File file = new File(destFileName);  
        if(file.exists()) {  
            return false;  
        }  
        if (destFileName.endsWith(File.separator)) {  
            return false;  
        }  
        //判断目标文件所在的目录是否存在  
        if(!file.getParentFile().exists()) {  
            //如果目标文件所在的目录不存在，则创建父目录  
            if(!file.getParentFile().mkdirs()) {  
                return false;  
            }  
        }  
        //创建目标文件  
        try {  
            if (file.createNewFile()) {  
                return true;  
            } else {  
                return false;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
    } 
	
}
