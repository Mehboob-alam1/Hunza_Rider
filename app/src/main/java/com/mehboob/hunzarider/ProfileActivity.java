package com.mehboob.hunzarider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mehboob.hunzarider.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.checkbox.isChecked() || binding.edittextName.getText().toString().isEmpty() ||
                        binding.edittextEmail.getText().toString().isEmpty()||binding.edittextAddress.getText().toString().isEmpty()
                        ||binding.edittextPhoneNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(ProfileActivity.this, "Fill the information", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = binding.edittextName.getText().toString();
                    String email = binding.edittextEmail.getText().toString();
                    String number = binding.edittextPhoneNumber.getText().toString();
                    String address = binding.edittextAddress.getText().toString();

                    startActivity(new Intent(ProfileActivity.this,AddVehicalActivity.class));
                }
            }
        });



        binding.textViewPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Terms are selected ", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textviewTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, " Read the policy successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}