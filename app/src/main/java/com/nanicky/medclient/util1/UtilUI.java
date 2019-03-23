package com.nanicky.medclient.util1;

import android.content.res.Resources;

public class UtilUI {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
