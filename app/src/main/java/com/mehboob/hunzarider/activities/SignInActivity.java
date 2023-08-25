package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.HideKeyboard.hideKeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mehboob.hunzarider.databinding.ActivitySignInBinding;

import java.util.zip.ZipException;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in");
        progressDialog.setMessage("PLease Wait");
        progressDialog.setCancelable(false);


        auth = FirebaseAuth.getInstance();
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etEmailAddress.getText().toString().isEmpty()) {
                    showSnackBar("Enter email");
                } else if (binding.etPassword.getText().toString().isEmpty()) {
                    showSnackBar("Enter password");
                } else {
                    String email = binding.etEmailAddress.getText().toString();
                    String password = binding.etEmailAddress.getText().toString();
                    hideKeyboard(SignInActivity.this);
                    doLogin(email, password);
                }
            }
        });

        binding.btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

    }

    private void doLogin(String email, String password) {
        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                showSnackBar("User login successfull");
                startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
            } else {
                progressDialog.dismiss();
                showSnackBar("Something went wrong");
            }
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            showSnackBar(e.getLocalizedMessage());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        // Call the super method to handle the back button behavior
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content), message
                ,
                Snackbar.LENGTH_SHORT
        );
        snackbar.show();
    }
}