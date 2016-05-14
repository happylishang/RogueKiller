package com.snail.roguekiller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by personal on 16/5/10.
 */
public class PreferrenceHelper {

    public final String PREFERENCE = "preference";
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private PreferrenceHelper(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public static PreferrenceHelper sPreferrenceHelper = new PreferrenceHelper(AppProfile.getContext());

    private static void putInt(String key, int value) {
        SharedPreferences.Editor editor = sPreferrenceHelper.mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static int getInt(String key, int defaultValue) {
        return sPreferrenceHelper.mSharedPreferences.getInt(key, defaultValue);
    }

    private static void putString(String key, String value) {
        SharedPreferences.Editor editor = sPreferrenceHelper.mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getString(String key, String defaultValue) {
        return sPreferrenceHelper.mSharedPreferences.getString(key, defaultValue);
    }

    public static void putCurrentFilter(@NonNull String stage, int type) {
        putInt(stage, type);
    }

    public static int gettCurrentFilter(@NonNull String stage) {
        return getInt(stage, 0);
    }


}
