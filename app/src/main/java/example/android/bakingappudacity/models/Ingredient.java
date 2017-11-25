package example.android.bakingappudacity.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import paperparcel.PaperParcel;

/**
 * Created by sheri on 11/9/2017.
 */
@PaperParcel
public class Ingredient implements Parcelable {
    public static final Creator<Ingredient> CREATOR = PaperParcelIngredient.CREATOR;

    @SerializedName("quantity")
    String mQuantity;
    @SerializedName("measure")
    String mMeasure;
    @SerializedName("ingredient")
    String mIngredient;

    public Ingredient(String mQuantity, String mMeasure, String mIngredient) {
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelIngredient.writeToParcel(this, parcel, i);
    }

    public String getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }
}
