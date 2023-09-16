package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.Utils.showSnackBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.databinding.ActivityLoginBinding;
import com.mehboob.hunzarider.utils.HideKeyboard;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String phoneNumber;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        checkEditText();

        binding.btnConnect.setOnClickListener(v -> {
            phoneNumber = "+92" + binding.etPhoneNumber.getText().toString();
            binding.btnConnect.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            HideKeyboard.hideKeyboard(this);

            sendVerificationCode(phoneNumber);
        });
    }

    private void sendVerificationCode(String phoneNumber) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(  phoneNumber,
                60,
                TimeUnit.SECONDS,
                LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnConnect.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnConnect.setVisibility(View.VISIBLE);
                        showSnackBar(LoginActivity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnConnect.setVisibility(View.VISIBLE);
                        Intent intent= new Intent(LoginActivity.this,OtpActivity.class);
                        intent.putExtra("number",phoneNumber);
                        intent.putExtra("verificationID",verificationId);
                        startActivity(intent);
                    }
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