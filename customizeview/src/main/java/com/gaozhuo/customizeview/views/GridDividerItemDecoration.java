package com.gaozhuo.customizeview.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * grid分割线
 *
 * @author gaozhuo
 * @date 2016/5/15
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;
    private final int mColumnCount;

    public GridDividerItemDecoration(int space, int columnCount) {
        this.mSpace = space;
        this.mColumnCount = columnCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if(position < mColumnCount){
            outRect.top = mSpace;
        }

        if(mColumnCount == 3){
            if(position % mColumnCount == 0){
                outRect.left = mSpace;
                outRect.right = mSpace /2;
            } else if(position % mColumnCount == 1){
                outRect.left = mSpace /2;
                outRect.right = mSpace /2;
            } else {
                outRect.left = mSpace /2;
                outRect.right = mSpace;
            }
        } else if(mColumnCount == 4){
            if(position % mColumnCount == 0){
                outRect.left = mSpace;
                outRect.right = mSpace /2;
            } else if(position % mColumnCount == 1){
                outRect.left = mSpace /2;
                outRect.right = mSpace /2;
            } else if(position % mColumnCount == 2){
                outRect.left = mSpace /2;
                outRect.right = mSpace /2;
            } else {
                outRect.left = mSpace /2;
                outRect.right = mSpace;
            }
        }

        outRect.bottom = mSpace;
    }
}

