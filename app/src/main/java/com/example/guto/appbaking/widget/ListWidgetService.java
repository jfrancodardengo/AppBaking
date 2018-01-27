package com.example.guto.appbaking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.RecipeListAdapter;
import com.example.guto.appbaking.model.IngredientsModel;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.ui.MainActivity;
import com.example.guto.appbaking.utils.JsonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUTO on 17/01/2018.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<IngredientsModel> allIngredients;
    private final Context mContext;

    public ListRemoteViewsFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int recipeId = intent.getIntExtra(RecipeListAdapter.RECIPE_ID, -1);
        Log.d("Widget Check Factory", String.valueOf(recipeId));
    }

    private void getIngredients() {
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());

        String response = s.getString(MainActivity.WHOLE_RESPONSE, "");
        List<RecipeModel> allRecipies = new ArrayList<>();
        allRecipies = JsonService.getJsonRecipe(response);
        allIngredients = new ArrayList<>();
        allIngredients = allRecipies.get(s.getInt(RecipeListAdapter.RECIPE_ID, -1)).getIngredients();
        Log.d("Widget ", "Checks");

    }

    @Override
    public void onCreate() {
        getIngredients();
        Log.d("Constructor ", " Widget ");
    }

    @Override
    public void onDataSetChanged() {
        getIngredients();
        Log.d("Constructor ", " Widget ");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return allIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget);

        if (allIngredients.size() != 0) {
            String name = allIngredients.get(i).getIngredient();
            String description = allIngredients.get(i).getQuantity() + " " + allIngredients.get(i).getMeasure() + " de ";

            remoteViews.setTextViewText(R.id.ingredientWidgetDetails, description + name);
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
