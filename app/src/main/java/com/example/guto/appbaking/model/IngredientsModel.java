package com.example.guto.appbaking.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GUTO on 28/12/2017.
 */

public class IngredientsModel implements Parcelable {
    public static final Creator<IngredientsModel> CREATOR = new Creator<IngredientsModel>() {
        @Override
        public IngredientsModel createFromParcel(Parcel in) {
            return new IngredientsModel(in);
        }

        @Override
        public IngredientsModel[] newArray(int size) {
            return new IngredientsModel[size];
        }
    };
    private int mQuantity;
    private String mMeasure;
    private String mIngredient;

    public IngredientsModel() {
    }

    public IngredientsModel(int mQuantity, String mMeasure, String mIngredient) {
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;
    }

    protected IngredientsModel(Parcel in) {
        mQuantity = in.readInt();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredient);
    }
}
