package com.mehboob.hunzarider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.mehboob.hunzarider.databinding.ActivityContactBinding;
import com.mehboob.hunzarider.fragments.HomeFragment;

public class ContactActivity extends AppCompatActivity {

    ActivityContactBinding binding;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();

            }
        });
    }



}