package com.lsj.lsjnews.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

import com.lsj.lsjnews.R;

public class DialogSelectUserPic extends PopupWindow {

	private Button mButPhoto;
	private Button mButCamera;
	private Context mContext;
	private View mMenuView;
	private Button mButCancel;

	public DialogSelectUserPic(Context context, OnClickListener listener) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dialog_user_pic_select, null);
		mContext = context;
		mButPhoto = (Button) mMenuView.findViewById(R.id.but_user_head_selete_photo);
		mButCamera = (Button) mMenuView.findViewById(R.id.but_user_head_selete_camera);
		mButCancel = (Button) mMenuView.findViewById(R.id.but_user_head_selete_cancel);

		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.popwin_anim_style);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);

		mButPhoto.setOnClickListener(listener);
		mButCamera.setOnClickListener(listener);
		mButCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
}
