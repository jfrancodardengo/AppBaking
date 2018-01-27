package com.example.guto.appbaking.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.IngredientAdapter;
import com.example.guto.appbaking.model.IngredientsModel;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.ui.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {
    @BindView(R.id.ingredientRecycler)
    public RecyclerView ingredientRecycler;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance() {
        return new IngredientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, rootView);
        DetailActivity detailActivity = (DetailActivity) getActivity();
        RecipeModel recipeModel = detailActivity.getRecipeParcelable();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientRecycler.setLayoutManager(linearLayoutManager);
        List<IngredientsModel> ingredientsModels = recipeModel.getIngredients();
        ingredientRecycler.setAdapter(new IngredientAdapter(ingredientsModels));
        return rootView;
    }

}
