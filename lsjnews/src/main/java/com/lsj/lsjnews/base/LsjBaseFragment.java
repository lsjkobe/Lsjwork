package com.lsj.lsjnews.base;

import com.example.lsj.httplibrary.base.BaseFragment;

/**
 * Created by Le on 2016/3/4.
 */
public abstract class LsjBaseFragment extends BaseFragment{
    boolean isVisible = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见
     */
    protected abstract void onVisible();

    /**
     * 不可见
     */
    protected void onInvisible(){

    }
}
