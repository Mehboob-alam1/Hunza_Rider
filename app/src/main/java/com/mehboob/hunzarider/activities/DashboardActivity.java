package com.mehboob.hunzarider.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.mapboxsdk.Mapbox;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityDashboardBinding;
import com.mehboob.hunzarider.fragments.AccountFragment;
import com.mehboob.hunzarider.fragments.BookingFragment;
import com.mehboob.hunzarider.fragments.HomeFragment;
import com.mehboob.hunzarider.fragments.NotificationsFragment;
import com.mehboob.hunzarider.fragments.WalletFragment;
import com.mehboob.hunzarider.models.Availability;

public class DashboardActivity extends AppCompatActivity {

   private ActivityDashboardBinding binding;
    private Fragment fragment;
    public DrawerLayout drawerLayout;

    public ActionBarDrawerToggle actionBarDrawerToggle;


    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getResources().getString(R.string.mapbox_access_token));
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawerLayout);

         mRef= FirebaseDatabase.getInstance().getReference(Constants.RIDER).child(Constants.AVAILABILITY);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        View appbarView = findViewById(R.id.appBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();

        binding.navigationView.setCheckedItem(R.id.nav_home);


        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);

                    appbarView.setVisibility(View.VISIBLE);
                    callFragment(fragment);
                    break;
                case R.id.nav_booking:
                    fragment = new BookingFragment();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.bottomNavigation.setSelectedItemId(R.id.bottom_booking);

                    callFragment(fragment);
                    break;
                case R.id.nav_wallet:

                    fragment = new WalletFragment();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.bottomNavigation.setSelectedItemId(R.id.bottom_wallet);

                    callFragment(fragment);
                    break;

                case R.id.nav_rate:
                    Toast.makeText(DashboardActivity.this, "rate the app", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_about:
                    startActivity(new Intent(DashboardActivity.this, AboutActivity.class));
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);
                    break;

                case R.id.nav_contact:
                    startActivity(new Intent(DashboardActivity.this, ContactActivity.class));
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);

                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);
                    break;

            }
            return true;
        });


        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    callFragment(new HomeFragment());
                    appbarView.setVisibility(View.VISIBLE);

                    break;
                case R.id.bottom_booking:
                    callFragment(new BookingFragment());
                    break;
                case R.id.bottom_wallet:
                    callFragment(new WalletFragment());


                    break;
                case R.id.bottom_notification:
                    callFragment(new NotificationsFragment());

                    break;
                case R.id.bottom_account:
                    callFragment(new AccountFragment());
                    appbarView.setVisibility(View.GONE);
                    break;
//
            }
            return true;
        });


        ImageView imageView = findViewById(R.id.nav_menu);
        imageView.setOnClickListener(v -> {
            if (drawerLayout.isOpen())

                drawerLayout.closeDrawer(GravityCompat.START);

            else drawerLayout.openDrawer(GravityCompat.START);
        });
        TextView textView = findViewById(R.id.textView11);
        textView.setText("Offline");


        Switch mySwitch = findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
              //  Toast.makeText(getApplicationContext(), "Switch is ON", Toast.LENGTH_SHORT).show();

                setAvailability(true, Constants.USER_ID);
                textView.setText("Online");
            } else {
              //  Toast.makeText(getApplicationContext(), "Switch is OFF", Toast.LENGTH_SHORT).show();
                setAvailability(false, Constants.USER_ID);
                textView.setText("Offline");
            }
        });

    }

    private void setAvailability(boolean avail, String userId) {

        mRef.child(userId).setValue(new Availability(avail,userId))
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful())
                    {
                        Log.d("Availability","You are available");
                    }else{
                        Log.d("Availability","You are unavailable");

                    }
                }).addOnFailureListener(e -> {
                    Log.d("Availability","You are available" +e.getLocalizedMessage());

                });

    }


    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen())
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
        finish();
    }
}