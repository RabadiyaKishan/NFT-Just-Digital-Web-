package com.jdw.nftcreator.service;

import android.content.Context;

public class CommomPref {
    private static String adEffect = "adEffect";


    public static void setEffect(Context context, String intto) {
        try {
            context.getSharedPreferences(context.getPackageName(), 0).edit()
                    .putString(adEffect, intto).commit();
        } catch (Exception e) {

        }
    }

    public static String getEffect(Context context) {

        try {
            return context.getSharedPreferences(context.getPackageName(), 0)
                    .getString(adEffect, "null");
        } catch (Exception e) {

        }
        return "null";
    }

}