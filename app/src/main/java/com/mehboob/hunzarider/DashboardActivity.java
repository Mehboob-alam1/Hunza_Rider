package com.mehboob.hunzarider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationView;
import com.mehboob.hunzarider.databinding.ActivityDashboardBinding;
import com.mehboob.hunzarider.fragments.AccountFragment;
import com.mehboob.hunzarider.fragments.BookingFragment;
import com.mehboob.hunzarider.fragments.HomeFragment;
import com.mehboob.hunzarider.fragments.NotificationsFragment;
import com.mehboob.hunzarider.fragments.WalletFragment;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();
        View appbarView = findViewById(R.id.appBar);

        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.bottom_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();
                     appbarView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_booking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new BookingFragment()).commit();
                          appbarView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_wallet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new WalletFragment()).commit();
                        appbarView.setVisibility(View.GONE);
                        break;
                    case R.id.bottom_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new NotificationsFragment()).commit();
//                        appbarView.setVisibility(View.GONE);
                       break;
                    case R.id.bottom_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new AccountFragment()).commit();
                        appbarView.setVisibility(View.GONE);
                        break;


                }
                return true;
            }
        });

        binding.navigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);

                        CallFragment(fragment);
                        break;
                    case R.id.nav_booking:
                        fragment = new BookingFragment();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        binding.bottomNavigation.setSelectedItemId(R.id.bottom_booking);

                        CallFragment(fragment);
                        break;
                    case R.id.nav_wallet:
                        fragment = new WalletFragment();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        binding.bottomNavigation.setSelectedItemId(R.id.bottom_wallet);

                        CallFragment(fragment);
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(DashboardActivity.this,AboutActivity.class));
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);
                        break;

                    case R.id.nav_contact:
                        startActivity(new Intent(DashboardActivity.this,ContactActivity.class));
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        binding.bottomNavigation.setSelectedItemId(R.id.bottom_home);
                        break;


                }
                return true;
            }
        });
    }

    private void CallFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameContainer, this.fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}