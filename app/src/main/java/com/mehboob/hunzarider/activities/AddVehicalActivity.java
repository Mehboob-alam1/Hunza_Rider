package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mehboob.hunzarider.databinding.ActivityAddVehicalBinding;

public class AddVehicalActivity extends AppCompatActivity {

    ActivityAddVehicalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearLayoutDocument
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( binding.edittextType.getText().toString().isEmpty() ||
                        binding.edittextBrand.getText().toString().isEmpty()||binding.edittextModel.getText().toString().isEmpty()
                        ||binding.edittextColor.getText().toString().isEmpty())
                {
                    Toast.makeText(AddVehicalActivity.this, "Fill the required information ", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = binding.edittextType.getText().toString();
                    String email = binding.edittextBrand.getText().toString();
                    String number = binding.edittextModel.getText().toString();
                    String address = binding.edittextColor.getText().toString();

                    startActivity(new Intent(AddVehicalActivity.this, DocumentActivity.class));
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