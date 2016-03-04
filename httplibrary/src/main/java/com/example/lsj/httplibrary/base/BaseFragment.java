package com.example.lsj.httplibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.lsj.httplibrary.utils.AppManager;

/**
 *
 */
public abstract class BaseFragment extends Fragment {


	public boolean isLoadSuccess = false;
	protected Context mContext;
	public BaseManager baseManager;
	private View contentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext=getActivity();
		
		baseManager=new BaseManager(mContext);
		
		contentView = baseManager.initBaseView(getLayoutId());

		initGetIntent();

		initTop();

		initView();

		initData();
	
		return contentView;
	}

	protected View getContentView(){
		return contentView;
	}
	
	protected View findViewById(int viewId){
		return contentView.findViewById(viewId);
	}
	
	/**
	 * 显示头部
	 * @param isShow
	 */
	protected  void showTopView(boolean isShow) {
		// TODO Auto-generated method stub
		baseManager.showTopView(isShow);
	}

	
	/**
	 * 界面传值初始化
	 */
	protected  void initGetIntent() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 不适用的话，可以重写
	 * 头部
	 */
	protected  void initTop() {
		// TODO Auto-generated method stub
		//返回
		baseManager.getLeftImage().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AppManager.getAppManager().finishActivity();
			}
		});
	}
		

	/**
	 * 初始化加载栏
	 */
	protected  void initView() {
		
	}

	/**
	 * 初始化数据
	 */
	protected abstract void initData();
	
	/**
	 * 用于加载网络抓取的数据
	 */
	public void baseLoadData(){};
	
	/**
	 * 这个页面的布局
	 * @return	id
	 */
	protected abstract int getLayoutId() ;
	
	/**
	 * 刷新页面
	 */
	public void refresh() {
		
	}

}
