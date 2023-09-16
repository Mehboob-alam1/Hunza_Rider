package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.Utils.showSnackBar;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityOtpBinding;
import com.mehboob.hunzarider.utils.SharedPref;

import java.util.concurrent.TimeUnit;

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


        binding.btnResend.setOnClickListener(v -> new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                binding.counter.setText(String.valueOf(millisUntilFinished / 1000));
                sendVerificationCode(number);
            }

            @Override
            public void onFinish() {
                binding.btnResend.setVisibility(View.VISIBLE);
            }
        }.start());


        OtpListner();

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                binding.counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.btnResend.setVisibility(View.VISIBLE);
            }
        }.start();

        binding.btnBack.setOnClickListener(v -> finish());
        binding.textViewNumber.setText(number);
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
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            sharedPref.saveUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sharedPref.setLoggedIn(true);
                            addStatus(FirebaseAuth.getInstance().getCurrentUser().getUid());


                            startActivity(new Intent(OtpActivity.this, ProfileActivity.class));

                        } else {
                            showSnackBar(OtpActivity.this, "Verification code invalid");
                        }
                    }).addOnFailureListener(e -> showSnackBar(OtpActivity.this, e.getLocalizedMessage()));
                } else {
                    showSnackBar(OtpActivity.this, "Check internet connection");
                }
            }
        });


    }

    private void sendVerificationCode(String phoneNumber) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
                60,
                TimeUnit.SECONDS,
                OtpActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        showSnackBar(OtpActivity.this, e.getLocalizedMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        OtpListner();
                    }
                });
    }

    private void addStatus(String userId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER);
        databaseReference.child("status")
                .child(userId)
                .child("Login")
                .setValue(true);

    }
}