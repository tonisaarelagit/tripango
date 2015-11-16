package com.tripango;

import android.app.Application;

import com.tripango.utils.LocalStorage;
import com.tripango.utils.UIUtils;

/**
 * Tripango Application Class
 * <p/>
 * Created by Toni Saarela on 11/16/2015.
 */
public class TripangoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Convenience method to instantiate singleton helper classes.
     */
    private void initSingletons() {
        LocalStorage.init(this);
        UIUtils.init(this);
    }
}
