package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.HideKeyboard.hideKeyboard;
import static com.mehboob.hunzarider.utils.Utils.showSnackBar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityProfileBinding;
import com.mehboob.hunzarider.models.ProfileDetailsClass;
import com.mehboob.hunzarider.utils.SharedPref;

public class ProfileActivity extends AppCompatActivity {
private ActivityProfileBinding binding;
private ProgressDialog progressDialog ;
private DatabaseReference mRef;
private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        mRef=FirebaseDatabase.getInstance().getReference(Constants.RIDER);

        progressDialog = new ProgressDialog(this);

        sharedPref= new SharedPref(this);
        binding.linearLayoutAddVehical.setOnClickListener(v -> {

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

                hideKeyboard(ProfileActivity.this);

                progressDialog.setMessage("Please wait....");
                progressDialog.setCancelable(false);
                progressDialog.show();


                mRef.child(Constants.RIDER_PROFILE).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new ProfileDetailsClass(name,email,number,address,Constants.USER_ID))
                        .addOnCompleteListener(task -> {
                            if (task.isComplete() && task.isSuccessful()){
                                progressDialog.dismiss();
                               showSnackBar(ProfileActivity.this,"Profile Created");
                               sharedPref.setProfileCompleted(true);
                                addProfileStatus(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            }else{
                                progressDialog.dismiss();
                                showSnackBar(ProfileActivity.this,"Something went wrong! Try checking your internet connection");

                            }
                        }).addOnFailureListener(e -> {
                            progressDialog.dismiss();
                           showSnackBar(ProfileActivity.this,e.getLocalizedMessage());
                        });

            }
        });



        binding.textViewPolicy.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Terms are selected ", Toast.LENGTH_SHORT).show());

        binding.textviewTerms.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, " Read the policy successfully", Toast.LENGTH_SHORT).show());

        binding.btnback.setOnClickListener(v -> {
            finishAffinity();
            progressDialog.dismiss();

        });
    }
    private void addProfileStatus(String userId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER);
        databaseReference.child("status")
                .child(userId)
                .child("profileCompleted")
                .setValue(true).addOnCompleteListener(task -> {

                    if (task.isComplete() && task.isSuccessful()){
                        startActivity(new Intent(ProfileActivity.this,AddVehicalActivity.class));
                    }else{
                        showSnackBar(ProfileActivity.this,"Check internet connection");
                    }
                }).addOnFailureListener(e -> {
                    showSnackBar(ProfileActivity.this,e.getLocalizedMessage());
                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }
}