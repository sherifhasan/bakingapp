package example.android.bakingappudacity.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import paperparcel.PaperParcel;

/**
 * Created by sheri on 11/9/2017.
 */
@PaperParcel
public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = PaperParcelRecipe.CREATOR;

    @SerializedName("id")
    String mRecipeId;
    @SerializedName("name")
    String mRecipeName;
    @SerializedName("ingredients")
    List<Ingredient> mIngredients;
    @SerializedName("steps")
    List<Step> mSteps;
    @SerializedName("servings")
    String mServings;
    @SerializedName("image")
    String mRecipeImage;

    public Recipe(String mRecipeId, String mRecipeName, List<Ingredient> mIngredients, List<Step> mSteps, String mServings, String mRecipeImage) {
        this.mRecipeId = mRecipeId;
        this.mRecipeName = mRecipeName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mRecipeImage = mRecipeImage;
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public String getServings() {
        return mServings;
    }

    public String getRecipeImage() {
        return mRecipeImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelRecipe.writeToParcel(this, parcel, i);
    }
}
