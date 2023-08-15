package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mehboob.hunzarider.databinding.ActivitySignInBinding;

import java.util.zip.ZipException;

public class SignInActivity extends AppCompatActivity {
ActivitySignInBinding binding;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in");
        progressDialog.setMessage("PLease Wait");
        progressDialog.setCancelable(false);



        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edittextNumber.getText().toString().isEmpty())
                {
                    binding.textviewError.setVisibility(View.VISIBLE);
                }
                else
                {


                    String number  = binding.edittextNumber.getText().toString();


                    progressDialog.show();








                    startActivity(new Intent(SignInActivity.this, DashboardActivity.class));



                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
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
}