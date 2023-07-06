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
import com.mehboob.hunzarider.databinding.ActivityProfileBinding;
import com.mehboob.hunzarider.models.ProfileDetailsClass;

public class ProfileActivity extends AppCompatActivity {
ActivityProfileBinding binding;
ProgressDialog progressDialog ;
private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        mRef=FirebaseDatabase.getInstance().getReference(Constants.RIDER);

        progressDialog = new ProgressDialog(this);

        binding.linearLayoutAddVehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.checkbox.isChecked() || binding.edittextName.getText().toString().isEmpty() ||
                        binding.edittextEmail.getText().toString().isEmpty()||binding.edittextAddress.getText().toString().isEmpty()
                        ||binding.edittextPhoneNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(ProfileActivity.this, "Fill the information", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = binding.edittextName.getText().toString();
                    String email = binding.edittextEmail.getText().toString();
                    String number = binding.edittextPhoneNumber.getText().toString();
                    String address = binding.edittextAddress.getText().toString();


                    progressDialog.setTitle("Signing in");
                    progressDialog.setMessage("PLease Wait");
                    progressDialog.setCancelable(false);


                    mRef.child(Constants.RIDER_PROFILE).child(Constants.USER_ID).setValue(new ProfileDetailsClass(name,email,number,address))
                            .addOnCompleteListener(task -> {
                                if (task.isComplete() && task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ProfileActivity.this,AddVehicalActivity.class));
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            });

                }
            }
        });



        binding.textViewPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Terms are selected ", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textviewTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, " Read the policy successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }
}