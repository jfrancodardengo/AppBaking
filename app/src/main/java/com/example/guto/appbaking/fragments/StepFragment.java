package com.example.guto.appbaking.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {
    public static final String STEP_PARCELABLE = "parcelableStep";
    public static final String STEP_POSITION = "selectedStepPosition";

    @BindView(R.id.stepRecycler)
    public RecyclerView stepRecycler;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance() {
        return new StepFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        DetailActivity detailActivity = (DetailActivity) getActivity();
        RecipeModel recipeModel = detailActivity.getRecipeParcelable();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stepRecycler.setLayoutManager(linearLayoutManager);
        List<StepsModel> stepsModels = recipeModel.getSteps();

        stepRecycler.setAdapter(new StepAdapter(getActivity(), stepsModels, new StepAdapter.DialogFragmentListener() {
            @Override
            public void onStepItemClicked(int selectedPosition, List<StepsModel> stepList) {
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(STEP_POSITION, selectedPosition);
                bundle.putParcelableArrayList(STEP_PARCELABLE, (ArrayList<? extends Parcelable>) stepList);
                stepDetailFragment.setArguments(bundle);
                stepDetailFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                stepDetailFragment.show(getFragmentManager(), "Step Detail Dialog");
            }
        }));
        return rootView;
    }

}
