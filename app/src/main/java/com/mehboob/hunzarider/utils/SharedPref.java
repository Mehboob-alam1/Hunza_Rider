package com.mehboob.hunzarider.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private static final String MY_PHOTO_URI = "photo";
    private static final String NIC_FRONT = "nic_front";
    private static final String NIC_BACK = "nic_back";
    private static final String VEHICLE_PAPER = "vehicle_paper";
    private static final String DRIVING_LICENSE = "driving_license";
    private static final String USER_ID="user_id";

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PHOTO_URI, Context.MODE_PRIVATE);

    }

    public void saveMyPhotoUri(Uri photo){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(MY_PHOTO_URI,photo.toString());
        editor.apply();
    }
    public void saveUID(String  userId){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(USER_ID,userId);
        editor.apply();
    }
    public String fetchUserId(){

        return sharedPreferences.getString(USER_ID,"");
    }
    public String fetchMyPhoto(){

        return sharedPreferences.getString(MY_PHOTO_URI,"");
    }
    public void saveNicFrontUri(Uri photo){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(NIC_FRONT,photo.toString());
        editor.apply();
    }

    public String fetchNicFront(){

        return sharedPreferences.getString(NIC_FRONT,"");
    }

    public void saveNicBackUri(Uri photo){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(NIC_BACK,photo.toString());
        editor.apply();
    }

    public String fetchNicBack(){

        return sharedPreferences.getString(NIC_BACK,"");
    }

    public void saveVehiclePaperUri(Uri photo){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(VEHICLE_PAPER,photo.toString());
        editor.apply();
    }

    public String fetchVehiclePaper(){

        return sharedPreferences.getString(VEHICLE_PAPER,"");
    }
    public void saveDrivingLicenseUri(Uri photo){
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(DRIVING_LICENSE,photo.toString());
        editor.apply();
    }

    public String fetchDrivingLicense(){

        return sharedPreferences.getString(DRIVING_LICENSE,"");
    }


}
