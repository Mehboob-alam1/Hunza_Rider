package com.mehboob.hunzarider.constants;

import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class Constants {
    public static final String RIDER ="Riders";
    public static final String RIDER_PROFILE ="Profiles";
    public static final String USER_ID = FirebaseAuth.getInstance().getUid();
    public static final String VEHICLES = "Vehicles";
    public static final String DOCUMENTS="documents";
    public static final String BANK_DETAILS="BankDetails";
public static final String LOCATION="location";
public static final String AVAILABILITY="available";
    public static final String TOPIC="/topics/hunzabykea";
    public static final String BASE_URL="https://fcm.googleapis.com";
    public static final String SERVER_KEY="AAAAGriD1uw:APA91bHV7PTVFTXFaCXBlgRrT8Lr8-G79rMZWb1aVDBCpphUykRKNNV73JH0nK8jEfsMqpzKRJ0rlxyS5-nAPkKHJKmoJ8wiMMElQRRM34TLJN4rv3WzmRvAtFk_J2aOsbP4f1_JEATu";
    public static final String CONTENT_TYPE="application/json";
    public static final String RIDER_COMPLETED_RIDES="RiderCompletedRides";

    public static final String RIDER_CANCELLED_RIDES="RiderCancelledRides";

    public static final LatLng BOUND_CORNER_NW = new LatLng(35.92508749263842, 74.24960570785369);
    public static final LatLng BOUND_CORNER_NW1 = new LatLng(35.930513220855474, 74.25887085720592);
    public static final LatLng BOUND_CORNER_NW2 = new LatLng(35.91255523701654, 74.25839299237633);
    public static final LatLng BOUND_CORNER_NW3 = new LatLng(35.91255523701654, 74.25839299237633);
    public static final LatLng BOUND_CORNER_NW4 = new LatLng(35.89683568394537, 74.34760199463189);
    public static final LatLng BOUND_CORNER_NW5 = new LatLng(35.880280356363436, 74.3915938530735);
    public static final LatLng BOUND_CORNER_NW6 = new LatLng(35.880280356363436, 74.38193988487843);
    public static final LatLng BOUND_CORNER_NW7 = new LatLng(35.91041039039034, 74.29760226531613);
    public static final LatLng BOUND_CORNER_SE = new LatLng(35.91987449159756, 74.38817935549248);




    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String MAPBOX_ACCESS_TOKEN = "sk.eyJ1IjoiYmFpZ3VsbGFoNDQiLCJhIjoiY2xobTg3cWl4MDQzcTNkbmc4M29jamV1eCJ9.UaBdqZyxWMLD_R3-liX4ZQ";
}
