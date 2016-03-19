package com.lsj.lsjnews.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lsj on 2016/3/19.
 * recycleview的间距
 */
public class RecycleSpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public RecycleSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
//
//        // Add top margin only for the first item to avoid double space between items
//        if(parent.getChildPosition(view) == 0)
        outRect.top = space;
    }
}