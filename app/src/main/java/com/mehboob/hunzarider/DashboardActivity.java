package com.mehboob.hunzarider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.mehboob.hunzarider.databinding.ActivityDashboardBinding;
import com.mehboob.hunzarider.fragments.AccountFragment;
import com.mehboob.hunzarider.fragments.BookingFragment;
import com.mehboob.hunzarider.fragments.HomeFragment;
import com.mehboob.hunzarider.fragments.NotificationsFragment;
import com.mehboob.hunzarider.fragments.WalletFragment;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new HomeFragment()).commit();


        binding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new HomeFragment()).commit();
                        break;
                    case R.id.booking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new BookingFragment()).commit();
                        break;
                    case R.id.wallet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new WalletFragment()).commit();
                        break;
                    case R.id.notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new NotificationsFragment()).commit();
                        break;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new AccountFragment()).commit();
                        break;


                }
                return true;
            }
        });
    }
}