package com.mehboob.hunzarider.activities;

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
import com.mehboob.hunzarider.databinding.ActivityPaymentBinding;
import com.mehboob.hunzarider.models.PaymentDetailClass;
import com.mehboob.hunzarider.utils.SharedPref;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;
    private DatabaseReference mRef;
    private ProgressDialog mProgressDialog;

    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPref= new SharedPref(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait....");
        mProgressDialog.setCancelable(false);
        mRef = FirebaseDatabase.getInstance().getReference(Constants.RIDER).child(Constants.BANK_DETAILS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        binding.btnFinish.setOnClickListener(v -> {


            if (binding.edittextBank.getText().toString().isEmpty() || binding.edittextName.getText().toString().isEmpty()
                    || binding.edittextAccountNo.getText().toString().isEmpty()) {

                binding.edittextBank.setError("Enter Bank Name");
                binding.edittextName.setError("Enter Your Name");
                binding.edittextAccountNo.setError("Enter Your Account No.");
            } else {
                uploadBankDetail(binding.edittextBank.getText().toString(), binding.edittextName.getText().toString(), binding.edittextAccountNo.getText().toString());


            }
        });
        binding.btnback.setOnClickListener(v -> finish());
    }

    private void uploadBankDetail(String bankName, String name, String accountNumber) {
        PaymentDetailClass paymentDetail = new PaymentDetailClass(bankName, name, accountNumber,Constants.USER_ID);
        mProgressDialog.show();
        mRef.setValue(paymentDetail).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showSnackBar(PaymentActivity.this,"Payment details added");
                sharedPref.setPaymentDetailsCompleted(true);
                mProgressDialog.dismiss();
                addPaymentsStatus(FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        }).addOnFailureListener(e -> {
            mProgressDialog.dismiss();
            Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void addPaymentsStatus(String userId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER);
        databaseReference.child("status")
                .child(userId)
                .child("paymentDetailsCompleted")
                .setValue(true).addOnCompleteListener(task -> {

                    if (task.isComplete() && task.isSuccessful()){
                        startActivity(new Intent(PaymentActivity.this,DashboardActivity.class));
                    }else{
                        showSnackBar(PaymentActivity.this,"Check internet connection");
                    }
                }).addOnFailureListener(e -> {
                    showSnackBar(PaymentActivity.this,e.getLocalizedMessage());
                });

    }
}