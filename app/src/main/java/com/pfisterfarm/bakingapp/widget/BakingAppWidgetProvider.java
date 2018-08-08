package com.pfisterfarm.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.pfisterfarm.bakingapp.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static final String RECIPE_NAME = "recipe_name";
    private static final String INGREDIENT_SET = "ingredient_set";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String widgetRecipeName;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(RECIPE_NAME)) {
            widgetRecipeName = prefs.getString(RECIPE_NAME,"No recipe selected");
        } else {
            widgetRecipeName = "No recipe selected";
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, BakingAppRemoteViewsService.class);
        views.setRemoteAdapter(R.id.recipe_ingredients, intent);

        views.setTextViewText(R.id.recipe_name, widgetRecipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

