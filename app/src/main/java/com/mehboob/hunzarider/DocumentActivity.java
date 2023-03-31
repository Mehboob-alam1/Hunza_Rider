package com.mehboob.hunzarider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.mehboob.hunzarider.databinding.ActivityDocumentBinding;

public class DocumentActivity extends AppCompatActivity {
ActivityDocumentBinding binding;

    private static final int pickImage= 1;
    private static final int pickNic= 2;
    private static final int pickVehicalPaper= 3;
    private static final int pickDrivingLicence= 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DocumentActivity.this,PaymentActivity.class));
            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        binding.btnUPloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent,pickImage);


            }
        });


        binding.btnUlploadNic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent,pickNic);


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnVehicalPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent,pickVehicalPaper);


            }
        });


        binding.btnDrivingLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent,pickDrivingLicence);


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pickImage && resultCode == RESULT_OK && data != null)
        {

            Uri imageUri =  data.getData();
            binding.uploadedImage.setImageURI(imageUri);
        }
        else if (requestCode == pickNic && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri =  data.getData();
            binding.uploadNic.setImageURI(imageUri);
        }
        else if (requestCode == pickVehicalPaper && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri =  data.getData();
            binding.vehicalsPaper.setImageURI(imageUri);
        }
        else
        {
            Uri imageUri =  data.getData();
            binding.drivingLicence.setImageURI(imageUri);
        }
    }
}