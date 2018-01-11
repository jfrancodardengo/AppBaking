package com.example.guto.appbaking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by GUTO on 28/12/2017.
 */

public class RecipeModel implements Parcelable {
    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };
    private int mIdRecipe;
    private String mNameRecipe;
    private List<IngredientsModel> mIngredients = null;
    private List<StepsModel> mSteps = null;
    private int mServings;
    private String mImage;

    public RecipeModel() {
    }

    public RecipeModel(int mIdRecipe, String mNameRecipe, List<IngredientsModel> mIngredients, List<StepsModel> mSteps, int mServings, String mImage) {
        this.mIdRecipe = mIdRecipe;
        this.mNameRecipe = mNameRecipe;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImage = mImage;
    }

    protected RecipeModel(Parcel in) {
        mIdRecipe = in.readInt();
        mNameRecipe = in.readString();
        mIngredients = in.createTypedArrayList(IngredientsModel.CREATOR);
        mSteps = in.createTypedArrayList(StepsModel.CREATOR);
        mServings = in.readInt();
        mImage = in.readString();
    }

    public int getIdRecipe() {
        return mIdRecipe;
    }

    public String getName() {
        return mNameRecipe;
    }

    public int getServings() {
        return mServings;
    }

    public String getImage() {
        return mImage;
    }

    public List<IngredientsModel> getIngredients() {
        return mIngredients;
    }

    public List<StepsModel> getSteps() {
        return mSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIdRecipe);
        parcel.writeString(mNameRecipe);
        parcel.writeTypedList(mIngredients);
        parcel.writeTypedList(mSteps);
        parcel.writeInt(mServings);
        parcel.writeString(mImage);
    }
}
