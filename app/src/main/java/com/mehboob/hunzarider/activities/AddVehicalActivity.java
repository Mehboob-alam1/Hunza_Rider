package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.HideKeyboard.hideKeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityAddVehicalBinding;
import com.mehboob.hunzarider.models.VehicleDetailsClass;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddVehicalActivity extends AppCompatActivity  {

    ActivityAddVehicalBinding binding;
    private DatabaseReference mRef;
    private String [] vehicles={"Select a vehicle","Car","Bike","Ac Car"};
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRef=FirebaseDatabase.getInstance().getReference(Constants.RIDER);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,vehicles);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinner.setAdapter(aa);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Adding vehicle ");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        binding.linearLayoutDocument
                .setOnClickListener(v -> {

                    if (
                            binding.edittextBrand.getText().toString().isEmpty()||binding.edittextModel.getText().toString().isEmpty()
                            ||binding.edittextColor.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddVehicalActivity.this, "Fill the required information ", Toast.LENGTH_SHORT).show();
                    }else if (binding.spinner.getSelectedItem().toString().equals("Select a vehicle")){
                        Toast.makeText(this, "Select a vehicle", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String vehicleBrand = binding.edittextBrand.getText().toString();
                        String vehicleModel = binding.edittextModel.getText().toString();
                        String vehicleColor = binding.edittextColor.getText().toString();
                        String vehicleType = binding.spinner.getSelectedItem().toString();
                        hideKeyboard(this);
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