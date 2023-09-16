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
    private static final String IS_ONLINE="online";
    private static final String PROFILE_COMPLETED_KEY = "ProfileCompleted";
    private static final String LOGGED_IN_KEY = "LoggedIn";
    private static final String VEHICLE_INFO_COMPLETED_KEY = "VehicleInfoCompleted";
    private static final String DOCUMENTS_COMPLETED_KEY = "DocumentsCompleted";
    private static final String PAYMENT_DETAILS_COMPLETED_KEY = "PaymentDetailsCompleted";
    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PHOTO_URI, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(NIC_FRONT, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(NIC_BACK, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(VEHICLE_PAPER, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(DRIVING_LICENSE, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(IS_ONLINE, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(PROFILE_COMPLETED_KEY, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(LOGGED_IN_KEY, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(VEHICLE_INFO_COMPLETED_KEY, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(DOCUMENTS_COMPLETED_KEY, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(PAYMENT_DETAILS_COMPLETED_KEY, Context.MODE_PRIVATE);

    }
public void saveIsOnline(boolean isOnline){
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(IS_ONLINE,isOnline);
        editor.apply();
}
public boolean isOnline(){
        return sharedPreferences.getBoolean(IS_ONLINE,false);
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
    public boolean isProfileCompleted() {
        return sharedPreferences.getBoolean(PROFILE_COMPLETED_KEY, false);
    }

    public void setProfileCompleted(boolean completed) {
        sharedPreferences.edit().putBoolean(PROFILE_COMPLETED_KEY, completed).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(LOGGED_IN_KEY, loggedIn).apply();
    }

    public boolean isVehicleInfoCompleted() {
        return sharedPreferences.getBoolean(VEHICLE_INFO_COMPLETED_KEY, false);
    }

    public void setVehicleInfoCompleted(boolean completed) {
        sharedPreferences.edit().putBoolean(VEHICLE_INFO_COMPLETED_KEY, completed).apply();
    }

    public boolean isDocumentsCompleted() {
        return sharedPreferences.getBoolean(DOCUMENTS_COMPLETED_KEY, false);
    }

    public void setDocumentsCompleted(boolean completed) {
        sharedPreferences.edit().putBoolean(DOCUMENTS_COMPLETED_KEY, completed).apply();
    }

    public boolean isPaymentDetailsCompleted() {
        return sharedPreferences.getBoolean(PAYMENT_DETAILS_COMPLETED_KEY, false);
    }

    public void setPaymentDetailsCompleted(boolean completed) {
        sharedPreferences.edit().putBoolean(PAYMENT_DETAILS_COMPLETED_KEY, completed).apply();
    }
}
