package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityAddVehicalBinding;
import com.mehboob.hunzarider.models.VehicleDetailsClass;

public class AddVehicalActivity extends AppCompatActivity {

    ActivityAddVehicalBinding binding;
    private DatabaseReference mRef;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRef=FirebaseDatabase.getInstance().getReference(Constants.RIDER);

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Adding vehicle ");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        binding.linearLayoutDocument
                .setOnClickListener(v -> {

                    if ( binding.edittextType.getText().toString().isEmpty() ||
                            binding.edittextBrand.getText().toString().isEmpty()||binding.edittextModel.getText().toString().isEmpty()
                            ||binding.edittextColor.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddVehicalActivity.this, "Fill the required information ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String vehicleType = binding.edittextType.getText().toString();
                        String vehicleBrand = binding.edittextBrand.getText().toString();
                        String vehicleModel = binding.edittextModel.getText().toString();
                        String vehicleColor = binding.edittextColor.getText().toString();
                        mRef.child(Constants.VEHICLES).child(Constants.USER_ID).setValue(new VehicleDetailsClass(vehicleType,vehicleBrand,vehicleModel,vehicleColor,Constants.USER_ID))
                                        .addOnCompleteListener(task -> {
                                            if (task.isComplete()&& task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(AddVehicalActivity.this, "Vehicle added successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AddVehicalActivity.this,DocumentActivity.class));
                                            }else{
                                                progressDialog.dismiss();
                                                Toast.makeText(AddVehicalActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(e -> {
                                            progressDialog.dismiss();
                                    Toast.makeText(AddVehicalActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                });


                    }
                });
        binding.btnback.setOnClickListener(v -> finish());
    }
}