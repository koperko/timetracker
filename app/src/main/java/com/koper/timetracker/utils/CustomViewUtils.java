package com.koper.timetracker.utils;

import android.content.Context;

/**
 * Created by koper on 13.01.16.
 */
public class CustomViewUtils {
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }



}
