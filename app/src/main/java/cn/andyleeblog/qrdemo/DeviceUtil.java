package cn.andyleeblog.qrdemo;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * device util
 * Created by andy on 17-5-2.
 */

public class DeviceUtil {

    public static int getScreenWidth(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    //该方法获取高度  包括 状态栏   但是不包括底部虚拟按键
    public static int getScreenHeight(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }
}
