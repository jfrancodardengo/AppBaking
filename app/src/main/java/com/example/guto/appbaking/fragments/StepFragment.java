package com.example.guto.appbaking.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.StepAdapter;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.model.StepsModel;
import com.example.guto.appbaking.ui.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {
    @BindView(R.id.stepRecycler)
    RecyclerView stepRecycler;
    private DetailActivity detailActivity;
    View rootView;
    LinearLayoutManager linearLayoutManager;
    RecipeModel recipeModel;
    List<StepsModel> stepsModels;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance() {
        return new StepFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this,rootView);
        detailActivity = (DetailActivity) getActivity();
        recipeModel = detailActivity.getRecipeParcelable();
        linearLayoutManager = new LinearLayoutManager(getContext());
        stepRecycler.setLayoutManager(linearLayoutManager);
        stepsModels = recipeModel.getSteps();
        stepRecycler.setAdapter(new StepAdapter(getContext(),stepsModels));
        return rootView;
    }

}
