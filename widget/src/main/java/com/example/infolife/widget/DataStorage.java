package com.example.infolife.widget;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by jsen on 2015/10/22.
 */
public class DataStorage {

    private static String KEY_MINUTE = "minute" ;

    public static void setInitMinute(Context context, int minute) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(KEY_MINUTE, minute).commit();
    }

    public static int getInitMinute(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_MINUTE, 0);
    }
}
