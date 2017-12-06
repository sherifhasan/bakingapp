package example.android.bakingappudacity.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

public class Utility {
    private static String SELECTED_RECIPE = "saved_recipe";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    public static int getSelectedRecipeIndex(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(SELECTED_RECIPE, 0);
    }

    public static void setSelectedRecipeIndex(Context context, int selectedRecipe) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_RECIPE, selectedRecipe);
        editor.apply();
    }
}