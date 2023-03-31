package com.mehboob.hunzarider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mehboob.hunzarider.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edittextNumber.getText().toString().isEmpty())
                {
                    binding.textviewError.setVisibility(View.VISIBLE);
                }
                else
                {
                    startActivity(new Intent(SignInActivity.this,ProfileActivity.class));

                }
            }
        });
    }
}