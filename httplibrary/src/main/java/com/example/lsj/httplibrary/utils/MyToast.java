package com.example.lsj.httplibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsj.httplibrary.R;

/**
 *
 * 自定义Toast
 * @author Le
 *
 */
public class MyToast {

	private static Toast mToast;
	/**
	 * 
	 * @param context
	 * @param redId imgview的背景
	 * @param content toast的内容
	 */
	public static void showToast(Context context,int redId,String content){
		View view = LayoutInflater.from(context).inflate(R.layout.my_toast, null);
		ImageView img = (ImageView)view.findViewById(R.id.img_my_tosat);
		TextView txtContent = (TextView)view.findViewById(R.id.txt_my_tosat_content);
		img.setBackgroundResource(redId);
		txtContent.setText(content);
        mToast = new Toast(context);
    	//设置Toast的位置 
    	mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    	mToast.setDuration(Toast.LENGTH_SHORT);
    	//让Toast显示为我们自定义的样子 
    	mToast.setView(view); 
		mToast.show();
	}

	public static void showToast(Context context,String content){
		
		try{
			mToast = new Toast(context);
			mToast.makeText(context, content, Toast.LENGTH_SHORT).show();
		}catch (Exception ex) {

		}

		
	}
	
	
	public static void showLongToast(Context context, String content) {
		try{
		    mToast = new Toast(context);
		    mToast.makeText(context, content, Toast.LENGTH_LONG).show();
		}catch (Exception ex) {

		}
	}
}
