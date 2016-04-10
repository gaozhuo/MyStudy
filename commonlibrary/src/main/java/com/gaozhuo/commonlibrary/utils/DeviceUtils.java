package com.gaozhuo.commonlibrary.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by gaozhuo on 2016/4/4.
 */
public class DeviceUtils {

    /**
     * 获取屏幕尺寸（高度包括状态栏，不包括虚拟导航栏）
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    public static int getScreenHeight(Context context){
        Point point = getScreenSize(context);
        return point.y;
    }

    public static int getScreenWidth(Context context){
        Point point = getScreenSize(context);
        return point.x;
    }
}
