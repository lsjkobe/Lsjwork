package com.example.lsj.httplibrary.base;

import android.content.Context;
import android.view.View;

public  class BasePage implements BaseHelper.OnErrorListener {
	
	protected View contentView;
	protected Context mContext;
	public boolean isLoadSuccess=false;
	public BaseManager baseManager;
	public BasePage(Context mContext) {
		this.mContext = mContext;
		
		baseManager=new BaseManager(mContext);
		
		baseManager.setOnOnErrorListener(this);
		
		contentView = baseManager.initBaseView(getLayoutId());
		
		initTop();
		
		initView();
		
		initData();
	}
	
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	protected void initTop() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用于加载网络抓取的数据
	 */
	public void baseLoadData(Object... values){};

	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getContentView() {
		return contentView;
	}
		
	protected View findViewById(int viewId){
		return contentView.findViewById(viewId);
	}

	@Override
	public void onError(View v) {
		// TODO Auto-generated method stub
	}
	
}
