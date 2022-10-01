package com.bugbd.wifiscane.MainActivity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class StatusSharedPreferences {

    private static StatusSharedPreferences statusSharedPreferences;
    private static Context context;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor myEdit;

    public static StatusSharedPreferences getStatusSharedPreferences(Context context) {
        if (statusSharedPreferences == null) {
            statusSharedPreferences = new StatusSharedPreferences();
        }
        context = context;
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        return statusSharedPreferences;
    }


    public boolean UserVisitStatus() {
        return sharedPreferences.getBoolean("isFirstTime", false);
    }

    public void insertFirstTime(boolean status) {
        myEdit.putBoolean("isFirstTime", status);
        myEdit.commit();
    }


}
