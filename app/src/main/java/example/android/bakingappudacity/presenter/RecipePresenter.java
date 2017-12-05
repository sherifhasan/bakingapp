package example.android.bakingappudacity.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.network.retrofit.ApiClient;
import example.android.bakingappudacity.network.retrofit.ApiInterface;
import example.android.bakingappudacity.ui.activities.RecipesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.bakingappudacity.utility.Utility.isNetworkConnected;

/**
 * Created by sheri on 11/14/2017.
 */

public class RecipePresenter {


    private List<Recipe> mRecipes;
    private Throwable mError;
    private RecipesActivity mView;
    private Context mContext;

    public RecipePresenter(Context context) {
        this.mContext = context;
        getRecipesFromApi(context);
    }

    private void getRecipesFromApi(Context context) {
        if (isNetworkConnected(context)) {
            ApiInterface apiService = ApiClient.getClient();
            Call<List<Recipe>> call = apiService.getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                    if (response.body() != null) {
                        Log.d("recipes size: ", (response.body().size() + toString()));
                        mRecipes = response.body();
                        publish();
                    } else {
                        Log.d("Get Recipes : ", "Response is null");
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.d("onFailure :", t.toString());
                    mError = t;
                    publish();
                }
            });
        }
    }

    public void onTakeView(RecipesActivity view) {
        this.mView = view;
        publish();
    }

    private void publish() {
        if (mView != null) {
            if (mRecipes != null)
                mView.onRecipesNext(mRecipes);
            else if (mError != null)
                mView.onRecipesError(mError);
        }
    }
}