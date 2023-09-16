package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mehboob.hunzarider.databinding.ActivityNoNetworkBinding;

public class NoNetworkActivity extends AppCompatActivity {

    private ActivityNoNetworkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityNoNetworkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}