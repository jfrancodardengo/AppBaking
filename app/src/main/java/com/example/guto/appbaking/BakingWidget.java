package com.example.guto.appbaking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.guto.appbaking.adapters.RecipeListAdapter;
import com.example.guto.appbaking.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {
    static RemoteViews views;
    public static final String REC_ID = "rec_id";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int sample,int appWidgetId) {

        views = new RemoteViews(context.getPackageName(), R.layout.ingredients_list);

        Intent widgetIntent = new Intent(context, ListWidgetService.class);

        if(PreferenceManager.getDefaultSharedPreferences(context).getInt(RecipeListAdapter.RECIPE_ID,-1)<0){
            views.setTextViewText(R.id.appwidget_text,"Nenhuma Receita Selecionada");
        }else if(PreferenceManager.getDefaultSharedPreferences(context).getInt(RecipeListAdapter.RECIPE_ID,-1)>=0){
            String recipeName = PreferenceManager.getDefaultSharedPreferences(context).getString(RecipeListAdapter.RECIPE_NAME,"");
            views.setTextViewText(R.id.appwidget_text,"Ingredientes de "+ recipeName);
            Log.d("Check Widget","Change");
            widgetIntent.putExtra(REC_ID,PreferenceManager.getDefaultSharedPreferences(context).getInt(RecipeListAdapter.RECIPE_ID,-1));

            //THIS SOLVED THE WIDGET MANUAL UPDATE
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_all_ingredients_view);
        }

        views.setRemoteAdapter(R.id.list_all_ingredients_view,widgetIntent);

//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//        views.setOnClickPendingIntent(R.id.ingredientWidgetDetails,pendingIntent);
//
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int sample = PreferenceManager.getDefaultSharedPreferences(context).getInt(RecipeListAdapter.RECIPE_ID,-1);
        // There may be multiple widgets active, so update all of them
        updateBakingWidgets(context,appWidgetManager,sample,appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int sample, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, sample, appWidgetId);
        }
    }
}

