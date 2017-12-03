package example.android.bakingappudacity.ui.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.models.Step;
import example.android.bakingappudacity.network.retrofit.ApiClient;
import example.android.bakingappudacity.network.retrofit.ApiInterface;
import example.android.bakingappudacity.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.bakingappudacity.utility.Utility.isNetworkConnected;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (isNetworkConnected(context)) {
            ApiInterface apiService = ApiClient.getClient();
            Call<List<Recipe>> call = apiService.getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
                    if (response.body() != null) {
                        Log.d("recipes size: ", (response.body().size() + toString()));
                        List<Recipe> mRecipes = response.body();
                        int selectedRecipe = Utility.getSelectedRecipeIndex(context);
                        views.setTextViewText(R.id.recipe_name_text_view_widget, mRecipes.get(selectedRecipe).getRecipeName());
                        StringBuilder steps = new StringBuilder();
                        for (Step step : mRecipes.get(selectedRecipe).getSteps()) {
                            steps.append(" - ").append(step.getShortDescription()).append("\n");
                        }
                        final AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
                        final ComponentName componentName = new ComponentName(context, BakingAppWidget.class);
                        widgetManager.updateAppWidget(componentName, views);
                    } else {
                        Log.d("Get Recipes : ", "Response is null");
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.d("onFailure :", t.toString());
                }
            });
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}