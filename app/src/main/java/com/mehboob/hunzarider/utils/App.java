package com.mehboob.hunzarider.utils;

import static com.mehboob.hunzarider.utils.Utils.showSnackBar;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.hunzarider.activities.AddVehicalActivity;
import com.mehboob.hunzarider.activities.DashboardActivity;
import com.mehboob.hunzarider.activities.DocumentActivity;
import com.mehboob.hunzarider.activities.LoginActivity;
import com.mehboob.hunzarider.activities.PaymentActivity;
import com.mehboob.hunzarider.activities.ProfileActivity;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.recievers.NetworkChangeReceiver;
import com.mehboob.hunzarider.services.NetworkMonitorService;

public class App extends Application  implements Application.ActivityLifecycleCallbacks {
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        networkChangeReceiver = new NetworkChangeReceiver();

        // Register the receiver to listen for network changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
//        Intent serviceIntent = new Intent(this, NetworkMonitorService.class);
//        startService(serviceIntent);

        // Check if the user is logged in and all activities are completed
        checkIfActivitiesCompletedAndRedirect();
    }

    private void checkIfActivitiesCompletedAndRedirect() {
        // Assuming you have a way to get the user ID, replace with your actual implementation

//        if (FirebaseAuth.getInstance().getCurrentUser().getUid() == null || FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()) {
//            // Redirect the user to the login screen or authentication activity
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            return;  // Return early as we can't proceed without a valid user ID
//        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Check if the user is authenticated
        if (currentUser == null) {
            // Redirect the user to the login screen or authentication activity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;  // Return early as we can't proceed without a valid user ID
        }
        String userId = currentUser.getUid();
        if (userId.isEmpty()) {
            // Handle the case where the user ID is empty

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        } else {
            // User ID is not empty, proceed with your logic using the user ID
            SharedPref preferencesHelper = new SharedPref(this);
            ProgressDialog bar = new ProgressDialog(this);
            bar.setMessage("Checking profile...");
            // Check if the user has completed login and profile

            if (preferencesHelper.isLoggedIn() && preferencesHelper.isProfileCompleted() &&
                    preferencesHelper.isVehicleInfoCompleted() &&
                    preferencesHelper.isDocumentsCompleted() &&
                    preferencesHelper.isPaymentDetailsCompleted()) {

                bar.dismiss();
                // All activities are completed, redirect to Dashboard
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }else{


            // Assuming you have a Firebase reference
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(Constants.RIDER).child("status").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean profileCompleted = dataSnapshot.child("profileCompleted").getValue(Boolean.class);
                    Boolean vehicleInfoCompleted = dataSnapshot.child("vehicleInfoCompleted").getValue(Boolean.class);
                    Boolean documentsCompleted = dataSnapshot.child("documentsCompleted").getValue(Boolean.class);
                    Boolean paymentDetailsCompleted = dataSnapshot.child("paymentDetailsCompleted").getValue(Boolean.class);

                    if (profileCompleted != null && vehicleInfoCompleted != null && documentsCompleted != null &&
                            paymentDetailsCompleted != null && profileCompleted && vehicleInfoCompleted &&
                            documentsCompleted && paymentDetailsCompleted) {
                        // All activities are completed, redirect to Dashboard
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        bar.dismiss();
                        startActivity(intent);

                    } else {
                        // Not all activities are completed, redirect to the appropriate activity
                        // based on the completion status
                        if (profileCompleted == null || !profileCompleted) {
                            bar.dismiss();
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        } else if (vehicleInfoCompleted == null || !vehicleInfoCompleted) {
                            bar.dismiss();
                            Intent intent = new Intent(getApplicationContext(), AddVehicalActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        } else if (documentsCompleted == null || !documentsCompleted) {
                            bar.dismiss();
                            Intent intent = new Intent(getApplicationContext(), DocumentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        } else if (paymentDetailsCompleted == null || !paymentDetailsCompleted) {
                            bar.dismiss();
                            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors
                    bar.dismiss();
                    Toast.makeText(App.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {


        // Check if the user has completed login and profile

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
registerReceiver(networkChangeReceiver,filter);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        unregisterReceiver(networkChangeReceiver);
    }
}