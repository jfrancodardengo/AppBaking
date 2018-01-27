package com.example.guto.appbaking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.model.IngredientsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GUTO on 09/01/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private final List<IngredientsModel> ingredientsModels;

    public IngredientAdapter(List<IngredientsModel> ingredientsModels) {
        this.ingredientsModels = ingredientsModels;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.ingredient_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.textQuantity.setText(String.valueOf(ingredientsModels.get(position).getQuantity()));
        holder.textMeasure.setText(ingredientsModels.get(position).getMeasure());
        holder.textNameIngredient.setText(ingredientsModels.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredientsModels.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_quantity)
        TextView textQuantity;
        @BindView(R.id.text_measure)
        TextView textMeasure;
        @BindView(R.id.text_ingredient)
        TextView textNameIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
