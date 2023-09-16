package com.mehboob.hunzarider.utils;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;

public class SnackbarHelper {

    private static SnackbarHelper instance;
    private WeakReference<View> currentViewReference;

    private SnackbarHelper() {}

    public static SnackbarHelper getInstance() {
        if (instance == null) {
            instance = new SnackbarHelper();
        }
        return instance;
    }

    public void setCurrentView(View view) {
        currentViewReference = new WeakReference<>(view);
    }

    public void showSnackbar(String message) {
        if (currentViewReference != null && currentViewReference.get() != null) {
            Snackbar.make(currentViewReference.get(), message, Snackbar.LENGTH_LONG).show();
        } else {
            Log.e("SnackbarHelper", "Current view reference is null or the view is null");
        }
    }
}
