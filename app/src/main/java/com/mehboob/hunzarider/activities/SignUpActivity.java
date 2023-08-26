package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.HideKeyboard.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mehboob.hunzarider.databinding.ActivitySignUpBinding;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
private ActivitySignUpBinding binding;
private ProgressDialog progressDialog;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign up");
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(SignUpActivity.this,ProfileActivity.class));
        }


        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

//
//
//
//
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
//
//
    public void checkValidation()
    {
     if(binding.etEmailAddress.getText().toString().isEmpty()){
            showSnackBar("Add your email address");

        }else if (binding.etPassword.getText().toString().isEmpty()) {
         
         showSnackBar("Set a password");
     }else{
         

            String email= binding.etEmailAddress.getText().toString();
            String password= binding.etPassword.getText().toString();

         hideKeyboard(this);
progressDialog.show();
            createAccount(email,password);
        }
    }

    private void createAccount(String email, String password) {


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        progressDialog.dismiss();

                        showSnackBar("User created successfully!");
                            updateUi();
                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss();

                       showSnackBar(task.getException().toString());

                    }
                });
    }

    private void updateUi() {

        Intent i = new Intent(SignUpActivity.this,ProfileActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(SignUpActivity.this,DashboardActivity.class));
        }
    }
    private  void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),message
                ,
                Snackbar.LENGTH_SHORT
        );
        snackbar.show();
    }
}