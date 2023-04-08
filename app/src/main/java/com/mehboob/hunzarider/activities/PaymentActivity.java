package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mehboob.hunzarider.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.edittextBank.getText().toString().isEmpty() || binding.edittextName.getText().toString().isEmpty()
                || binding.edittextAccountNo.getText().toString().isEmpty())
                {

                    binding.edittextBank.setError("Enter Bank Name");
                    binding.edittextName.setError("Enter Your Name");
                    binding.edittextAccountNo.setError("Enter Your Account No.");
                }
                else {
                    startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));

                }
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