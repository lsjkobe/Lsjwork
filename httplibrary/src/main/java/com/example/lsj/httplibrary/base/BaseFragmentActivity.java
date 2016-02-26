package com.example.lsj.httplibrary.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.lsj.httplibrary.utils.AppManager;

/**   
* @Title: BaseActivity.java 
* @Package cn.dxb.app.base 
* @Description: TODO(用一句话描述该文件做什么) 
* @author xiaoluo 
* @date 2014年10月21日 下午4:44:44 
* @version V1.0   
*/
public abstract  class  BaseFragmentActivity extends FragmentActivity {
	
	protected Context mContext=this;
	public BaseManager baseManager;
	private View layoutView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		baseManager=new BaseManager(mContext);
		
		layoutView = baseManager.initBaseView(getLayoutId());
		
		AppManager.getAppManager().addActivity(this);
		
		setContentView(layoutView);
		
		initGetIntent();
		
		initTop();
		
		initView();
		
		initData();
		
	}
	
	@Override  
    public boolean onKeyUp(int keyCode, KeyEvent event){   
        if (keyCode == KeyEvent.KEYCODE_BACK && isDown)  
        {  
        	AppManager.getAppManager().finishActivity();
        }
        
        return false;  
    }
	
    private boolean isDown=false;
    
    //键盘谈起，按下返回键，不进来，其它都进来
    //处理当键盘弹起时，按下返回键，收回键盘，其它是finish
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		isDown=true;

		return super.onKeyDown(keyCode, event);
	}
	
	protected View getContentView(){
		return layoutView;
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
	
}
