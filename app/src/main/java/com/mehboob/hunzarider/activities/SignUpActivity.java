package com.mehboob.hunzarider.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mehboob.hunzarider.databinding.ActivitySignUpBinding;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
ActivitySignUpBinding binding;
ProgressDialog progressDialog;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(SignUpActivity.this,ProfileActivity.class));
        }


        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidaion();
            }
        });





        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }


    public void CheckValidaion()
    {
        if(binding.edittextNumber.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show();
        }
        else if(binding.edittextNumber.getText().toString().trim().length()!=10) {

            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
        }
        else
        {

            progressDialog.setTitle("Sending Otp");
            progressDialog.setMessage("PLease Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String number = binding.edittextNumber.getText().toString();

            PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + number,
                    60l, TimeUnit.SECONDS, SignUpActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            progressDialog.dismiss();

                            Intent intent = new Intent(SignUpActivity.this,OtpActivity.class);
                            intent.putExtra("verificationID" ,s);
                            intent.putExtra("number"+92,number);
                            startActivity(intent);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {

                            Log.d("Exception",e.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "no send", Toast.LENGTH_SHORT).show();

                        }
                    });

        }

    }
}