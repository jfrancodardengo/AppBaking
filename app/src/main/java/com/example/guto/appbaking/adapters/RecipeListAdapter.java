package com.example.guto.appbaking.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guto.appbaking.IngredientsWidgetService;
import com.example.guto.appbaking.R;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GUTO on 09/01/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
    //constante que vai armazenar o parcelable da intenet
    public static final String RECIPE_PARCELABLE = "recipe_parcelable";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_NAME = "recipe_name";

    private Context context;
    private List<RecipeModel> recipeModels;
    //armazena as imagens
    private List<Integer> mRecipeDrawables = imagesCardView();
    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private List<Integer> imagesCardView() {
        mRecipeDrawables = new ArrayList<>();
        mRecipeDrawables.add(R.drawable.nutella_pie);
        mRecipeDrawables.add(R.drawable.brownies);
        mRecipeDrawables.add(R.drawable.yellow_cake);
        mRecipeDrawables.add(R.drawable.cheesecake);
        return mRecipeDrawables;
    }

    public RecipeListAdapter(Context context,List<RecipeModel> recipeModelList) {
        this.context = context;
        this.recipeModels = recipeModelList;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item,parent,false);
        return new RecipeListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        holder.textRecipeName.setText(recipeModels.get(position).getName());
        Picasso.with(context)
                .load(mRecipeDrawables.get(position))
                .into(holder.imageRecipe);
        holder.textServings.setText(String.valueOf(recipeModels.get(position).getServings()));
    }

    @Override
    public int getItemCount() {
        if (recipeModels != null) {
            return recipeModels.size();
        } else {
            return 0;
        }
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_recipe)
        ImageView imageRecipe;
        @BindView(R.id.text_recipe_name)
        TextView textRecipeName;
        @BindView(R.id.text_servings)
        TextView textServings;
        @BindView(R.id.button_action)
        Button buttonAction;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = s.edit();
                    editor.putInt(RECIPE_ID,getAdapterPosition());
                    editor.putString(RECIPE_NAME,recipeModels.get(getAdapterPosition()).getName());
                    editor.apply();

                    Intent newI = new Intent(context, IngredientsWidgetService.class);
                    newI.setAction(IngredientsWidgetService.ACTION_SEE_INGREDIENTS);
                    context.startService(newI);

                    if(getAdapterPosition() != RecyclerView.NO_POSITION){
                        RecipeModel recipeModel = recipeModels.get(getAdapterPosition());
                        Intent intent = new Intent(context,DetailActivity.class);
                        intent.putExtra(RECIPE_PARCELABLE,recipeModel);
                        context.startActivity(intent);
                        Log.v(TAG,"Posição selecionada: " + recipeModel);
                    }
                }
            });

        }
    }
}
