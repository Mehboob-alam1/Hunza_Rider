package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityPaymentBinding;
import com.mehboob.hunzarider.models.PaymentDetailClass;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRef= FirebaseDatabase.getInstance().getReference(Constants.RIDER).child(Constants.BANK_DETAILS).child(Constants.USER_ID);
        binding.btnFinish.setOnClickListener(v -> {


            if (binding.edittextBank.getText().toString().isEmpty() || binding.edittextName.getText().toString().isEmpty()
            || binding.edittextAccountNo.getText().toString().isEmpty())
            {

                binding.edittextBank.setError("Enter Bank Name");
                binding.edittextName.setError("Enter Your Name");
                binding.edittextAccountNo.setError("Enter Your Account No.");
            }
            else {
                uploadBankDetail(binding.edittextBank.getText().toString(),binding.edittextName.getText().toString(),binding.edittextAccountNo.getText().toString());


            }
        });
        binding.btnback.setOnClickListener(v -> finish());
    }

    private void uploadBankDetail(String bankName, String name, String accountNumber) {
        PaymentDetailClass paymentDetail = new PaymentDetailClass(bankName,name,accountNumber);

        mRef.setValue(paymentDetail).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Bank details added ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaymentActivity.this,DashboardActivity.class));
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}