package example.android.bakingappudacity.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public class Utility {

    public static final String BASE_IMAGE_URL = "";
    private static final int IMAGE_SIZE = 120;


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static int getSpanCount(Context context) {
        int spanCount;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        spanCount = widthInDP / IMAGE_SIZE;

        return spanCount;
    }

}