package com.pfisterfarm.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pfisterfarm.bakingapp.R;

import java.util.Iterator;
import java.util.Set;


public class BakingAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Set ingredientSet;
    Iterator iter;

    private String INGREDIENT_SET = "ingredient_set";

    public BakingAppRemoteViewsFactory(Context appContext, Intent intent) {
        mContext = appContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.contains(INGREDIENT_SET)) {
            ingredientSet = prefs.getStringSet(INGREDIENT_SET, null);
            iter = ingredientSet.iterator();
        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientSet == null ? 0 : ingredientSet.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredientSet == null) {
            return null;
        }
        RemoteViews rViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_line_item);
        if (iter.hasNext()) {
            rViews.setTextViewText(R.id.line_item, (String) iter.next());
        }
        return rViews;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
