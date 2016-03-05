package com.lsj.lsjnews.utils;


import com.example.lsj.httplibrary.base.BaseFragment;

import java.util.Stack;

public class LsjFragmentManager {

	private static Stack<BaseFragment> activityStack;
	private static LsjFragmentManager instance;

	private LsjFragmentManager() {
	}

	/**
	 * 单一实例
	 */
	public static LsjFragmentManager getAppManager() {
		if (instance == null) {
			instance = new LsjFragmentManager();
		}
		return instance;
	}


	public void addFragment(BaseFragment fragment) {
		if (activityStack == null) {
			activityStack = new Stack<BaseFragment>();
		}
		activityStack.add(fragment);
	}

	public BaseFragment getFragmentByIndex(int index) {
		if(activityStack.get(index) != null){
			return activityStack.get(index);
		}
		return null;
	}
	public void finishAllFragment() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).onDestroy();
			}
		}
		activityStack.clear();
	}

}