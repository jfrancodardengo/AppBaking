package com.example.guto.appbaking.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GUTO on 28/12/2017.
 */

public class StepsModel implements Parcelable {
    public static final Creator<StepsModel> CREATOR = new Creator<StepsModel>() {
        @Override
        public StepsModel createFromParcel(Parcel in) {
            return new StepsModel(in);
        }

        @Override
        public StepsModel[] newArray(int size) {
            return new StepsModel[size];
        }
    };
    private int mIdStep;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public StepsModel() {
    }

    public StepsModel(int mIdStep, String mShortDescription, String mDescription, String mVideoUrl, String mThumbnailUrl) {
        this.mIdStep = mIdStep;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mVideoUrl = mVideoUrl;
        this.mThumbnailUrl = mThumbnailUrl;
    }

    protected StepsModel(Parcel in) {
        mIdStep = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public int getIdStep() {
        return mIdStep;
    }

    public void setIdStep(int mIdStep) {
        this.mIdStep = mIdStep;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIdStep);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
