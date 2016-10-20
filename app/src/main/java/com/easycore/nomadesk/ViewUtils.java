package com.easycore.nomadesk;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 21.08.16.
 */
public class ViewUtils {

    public static void gone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    public static void visible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void invisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    public static void visibleOrGone(boolean condition, View... views) {
        if (condition) {
            visible(views);
        } else {
            gone(views);
        }
    }

    public static void visibleOrInvisible(boolean visible, View... views) {
        if (visible) {
            visible(views);
        } else {
            invisible(views);
        }
    }

    /**
     * Checks visibility of array of Views.
     *
     * @param views Views
     * @return True if at least one of the View in array is visible
     */
    public static boolean isVisible(View... views) {
        for (View v : views) {
            if (View.VISIBLE == v.getVisibility()) return true;
        }
        return false;
    }

    /**
     * Checks visibility of array of Views.
     *
     * @param views Views
     * @return True if all Views in array are visible
     */
    public static boolean isAllVisible(View... views) {
        for (View v : views) {
            if (View.VISIBLE != v.getVisibility()) return false;
        }
        return true;
    }

    public static int convertDpToPixels(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int getDisplayWidthInDp(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = activity.getResources().getDisplayMetrics().density;
        return (int) (outMetrics.widthPixels / density);
    }

}
