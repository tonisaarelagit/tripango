package com.tripango.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Helper class which interacts with Local Storage.
 * <p/>
 * Created by Toni Saarela on 11/16/2015.
 */
public class LocalStorage {

    private final SharedPreferences mSharedPreferences;
    private static LocalStorage sInstance;

    private LocalStorage(final Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static LocalStorage init(final Context context) {
        if (sInstance == null) {
            sInstance = new LocalStorage(context);
        }
        return sInstance;
    }

    public static LocalStorage getInstance() {
        return sInstance;
    }

    public void setFlagValue(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getFlagValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

}
