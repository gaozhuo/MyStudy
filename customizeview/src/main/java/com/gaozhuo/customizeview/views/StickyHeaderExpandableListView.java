package com.gaozhuo.customizeview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 固定头部的ExpandableListView
 *
 * @author gaozhuo
 * @date 2016/8/22
 */
public class StickyHeaderExpandableListView extends ExpandableListView {
    public StickyHeaderExpandableListView(Context context) {
        super(context);
    }

    public StickyHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
