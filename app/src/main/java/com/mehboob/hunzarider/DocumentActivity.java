package com.mehboob.hunzarider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mehboob.hunzarider.databinding.ActivityDocumentBinding;

import java.io.IOException;

public class DocumentActivity extends AppCompatActivity {
    ActivityDocumentBinding binding;

    private static final int pickImage = 1;
    private static final int requestPickImage = 1;
    private static final int captureImage = 11;
    private static final int pickNic = 2;
    private static final int pickVehicalPaper = 3;
    private static final int pickDrivingLicence = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DocumentActivity.this, PaymentActivity.class));
            }
        });


        binding.btnUPloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImagePicker.with(DocumentActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(pickImage);

            }
        });


        binding.btnUlploadNic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(DocumentActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(pickNic);


            }
        });


        binding.btnVehicalPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(DocumentActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(pickVehicalPaper);


            }
        });


        binding.btnDrivingLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(DocumentActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(pickDrivingLicence );


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


        if (requestCode == 1 && data != null) {
            Uri uri = data.getData();
            binding.uploadedImage.setImageURI(uri);
            Toast.makeText(this, "Image Seletedted ", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && data != null) {
            Uri uri = data.getData();
            binding.uploadNic.setImageURI(uri);
        } else if (requestCode == 3 && data != null) {
            Uri uri = data.getData();
            binding.vehicalsPaper.setImageURI(uri);
        } else {
            Uri uri = data.getData();
            binding.drivingLicence.setImageURI(uri);
        }


    }
}