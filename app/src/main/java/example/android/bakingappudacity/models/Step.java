package example.android.bakingappudacity.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import paperparcel.PaperParcel;

/**
 * Created by sheri on 11/9/2017.
 */
@PaperParcel
public class Step implements Parcelable {
    public static final Creator<Step> CREATOR = PaperParcelStep.CREATOR;

    @SerializedName("id")
    String mStepId;
    @SerializedName("shortDescription")
    String mShortDescription;
    @SerializedName("description")
    String mDescription;
    @SerializedName("videoURL")
    String mVideoURL;
    @SerializedName("thumbnailURL")
    String mThumbnailURL;

    public Step(String mStepId, String mShortDescription, String mDescription, String mVideoURL, String mThumbnailURL) {
        this.mStepId = mStepId;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mVideoURL = mVideoURL;
        this.mThumbnailURL = mThumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        PaperParcelStep.writeToParcel(this, parcel, i);
    }

    public String getStepId() {
        return mStepId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }
}
