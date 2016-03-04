package com.lsj.lsjnews.utils;


import com.example.lsj.httplibrary.base.BaseFragment;

import java.util.Stack;

public class FragmentManager {

	private static Stack<BaseFragment> activityStack;
	private static FragmentManager instance;

	private FragmentManager() {
	}

	/**
	 * 单一实例
	 */
	public static FragmentManager getAppManager() {
		if (instance == null) {
			instance = new FragmentManager();
		}
		return instance;
	}


	public void addFragment(BaseFragment fragment) {
		if (activityStack == null) {
			activityStack = new Stack<BaseFragment>();
		}
		activityStack.add(fragment);
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