package com.js.shipper.util;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.js.shipper.App;

/**
 * Created by chen on 20/03/2018.
 */

public class UIUtil {


    public static Toast toast;


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(float dipValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void toast(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (toast == null) {
                toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }


    /**
     * 屏幕宽度（像素）
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        return metric.widthPixels;
    }


    public static int getScreenHeight() {
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        return metric.heightPixels;
    }
}
