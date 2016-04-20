package cn.asheng.photoselect.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ViewUtil {
    public static String KEY_WIDTH="key_width";

    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int dip2px(Context context, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources()
                .getDisplayMetrics()) + 0.5f);
    }
}
