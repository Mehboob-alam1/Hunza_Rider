package com.mehboob.hunzarider.utils;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public class Utils {


    public static void showSnackBar(Activity activity,String message) {
        Snackbar snackbar = Snackbar.make(
               activity.findViewById(android.R.id.content), message
                ,
                Snackbar.LENGTH_SHORT
        );
        snackbar.show();
    }
}
