package com.example.guto.appbaking.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.guto.appbaking.adapters.RecipeListAdapter;

/**
 * Created by GUTO on 17/01/2018.
 */

public class IngredientsWidgetService extends IntentService {
    public static final String ACTION_SEE_INGREDIENTS =
            "com.example.android.mygarden.action.see_ingredients";

    public IngredientsWidgetService() {
        super("");
    }

    public static void startActionIngredients(Context context) {
        Intent intent = new Intent(context, IngredientsWidgetService.class);
        intent.setAction(ACTION_SEE_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int sample;

        if (intent != null) {
            final String action = intent.getAction();

            if (action.equals(ACTION_SEE_INGREDIENTS)) {
                try {
                    sample = s.getInt(RecipeListAdapter.RECIPE_ID, -1);
                    Log.d("Check Service", "Running");
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
                    //Now update all widgets
                    BakingWidget.updateBakingWidgets(this, appWidgetManager, sample, appWidgetIds);
                } catch (Exception e) {
                }
            }
        }
    }
}
