package com.mehboob.hunzarider.constants;

import com.google.firebase.auth.FirebaseAuth;

public class Constants {
    public static final String RIDER ="Riders";
    public static final String RIDER_PROFILE ="Profiles";
    public static final String USER_ID = FirebaseAuth.getInstance().getUid();
    public static final String VEHICLES = "Vehicles";
    public static final String DOCUMENTS="documents";
    public static final String BANK_DETAILS="BankDetails";
}
