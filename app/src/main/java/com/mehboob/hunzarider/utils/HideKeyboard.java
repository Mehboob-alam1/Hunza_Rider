package com.mehboob.hunzarider.utils;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class HideKeyboard {


    public static void hideKeyboard(Activity activity){
        try {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
