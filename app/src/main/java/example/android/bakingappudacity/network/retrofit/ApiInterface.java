package example.android.bakingappudacity.network.retrofit;

import java.util.List;

import example.android.bakingappudacity.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}
