package com.mehboob.hunzarider.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mehboob.hunzarider.databinding.ActivityOtpBinding;
import com.mehboob.hunzarider.utils.SharedPref;

import in.aabhasjindal.otptextview.OTPListener;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;


    private String number;
    private FirebaseAuth firebaseAuth;
    private String OTPID;
    private String verificatonID;
    private PhoneAuthProvider.ForceResendingToken ResendToken;

    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPref = new SharedPref(this);
        firebaseAuth = FirebaseAuth.getInstance();
        number = (getIntent().getStringExtra("number"));
        verificatonID = getIntent().getStringExtra("verificationID");



        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        binding.counter.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        binding.btnResend.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });


        OtpListner();

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                binding.counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.btnResend.setVisibility(View.VISIBLE);
            }
        }.start();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void OtpListner() {
        binding.otpview.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(String otp) {

                if (verificatonID != null) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonID, otp);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sharedPref.saveUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                startActivity(new Intent(OtpActivity.this, ProfileActivity.class));

                            } else {
                                Toast.makeText(OtpActivity.this, "verificaton code invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OtpActivity.this, "Exception" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });


    }
}