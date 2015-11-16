package com.tripango.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Helper class which implements all helper functions.
 * <p/>
 * Created by Toni Saarela on 11/16/2015.
 */
public class UIUtils {

    private static UIUtils sInstance;

    private Context context;

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new UIUtils(context);
        }
    }

    public static UIUtils getInstance() {
        return sInstance;
    }

    private UIUtils(Context context) {
        this.context = context;
    }

    public void showMessage(int resId) {
        try {
            showMessage(context.getString(resId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
