package com.calix.calixgigaspireapp.utils;

import android.content.Context;


public class NumberUtil {

    /*Init dialog instance*/
    private static final NumberUtil sNumberUtilInstance = new NumberUtil();


    public static NumberUtil getInstance() {
        return sNumberUtilInstance;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
