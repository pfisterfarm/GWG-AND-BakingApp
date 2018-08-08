package com.pfisterfarm.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingAppRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}
