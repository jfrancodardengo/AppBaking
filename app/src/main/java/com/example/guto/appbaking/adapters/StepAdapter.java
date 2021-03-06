package com.example.guto.appbaking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.model.StepsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GUTO on 09/01/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private Context context;
    private final List<StepsModel> stepsModels;
    private final DialogFragmentListener dialogFragmentListener;

    public StepAdapter(Context context, List<StepsModel> stepsModels, DialogFragmentListener dialogFragmentListener) {
        this.context = context;
        this.stepsModels = stepsModels;
        this.dialogFragmentListener = dialogFragmentListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new StepViewHolder(LayoutInflater.from(context).inflate(R.layout.step_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder holder, int position) {
        holder.textIdStep.setText(String.valueOf(stepsModels.get(position).getIdStep()));
        holder.textShortDescription.setText(stepsModels.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsModels.size();
    }

    public interface DialogFragmentListener {
        void onStepItemClicked(int selectedPosition, List<StepsModel> stepList);
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_idStep)
        TextView textIdStep;
        @BindView(R.id.text_shortDescription)
        TextView textShortDescription;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogFragmentListener.onStepItemClicked(getAdapterPosition(), stepsModels);
                }
            });
        }
    }
}
