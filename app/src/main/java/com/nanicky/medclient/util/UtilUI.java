package com.nanicky.medclient.util;

import android.content.res.Resources;

public class UtilUI {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
