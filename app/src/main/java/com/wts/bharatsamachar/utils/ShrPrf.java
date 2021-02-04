package com.wts.bharatsamachar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ShrPrf {
    private final static String PREF_FILE = "com.pos";
    public static final String USER_ID = "userid";

    public static void setShrPrfStr(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setShrPrfInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    static void setShrPrfBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }



    public static String getShrPrfStr(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }


    public static int getShrPrfInt(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }


    public static boolean getShrdPrfBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }


    public static void logoutFromAll(Context ctx) {

		SharedPreferences settings;
		SharedPreferences.Editor editor;
		settings = ctx.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
		editor = settings.edit();
		editor.clear();
		editor.commit();
    }

}