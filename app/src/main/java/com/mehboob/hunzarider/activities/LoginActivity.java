package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding binding;
private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        checkEditText();

        binding.btnConnect.setOnClickListener(v -> {
           phoneNumber= "+92"+binding.etPhoneNumber.getText().toString();

            sendVerificationCode(phoneNumber);
        });
    }

    private void checkEditText() {
        binding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 10) {
                    // Enable the button and change its background color to green
                    binding.btnConnect.setEnabled(true);
                    binding.btnConnect.setBackgroundResource(R.drawable.background_button);
                } else {
                    // Disable the button and change its background color to grey
                    binding.btnConnect.setEnabled(false);
                    binding.btnConnect.setBackgroundResource(R.drawable.background_button_grey);
                }

            }
        });
    }
}