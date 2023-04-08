package com.mehboob.hunzarider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.databinding.ActivityWithdrawBinding;

public class WithdrawActivity extends AppCompatActivity {

    ActivityWithdrawBinding binding;
    String  textAmount;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CheckBox checkBox = findViewById(R.id.checkBoxBank);



        binding.checkBoxEasyPaisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkBoxEasyPaisa.isChecked())
                {
                   binding.checkBoxJazz.setChecked(false);
                   binding.checkBoxBank.setChecked(false);



                }
            }
        });

        binding.checkBoxJazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkBoxBank.isChecked())
                {
                    binding.checkBoxEasyPaisa.setChecked(false);
                    binding.checkBoxBank.setChecked(false);



                }
            }
        });

        binding.checkBoxBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkBoxBank.isChecked())
                {
                    binding.checkBoxJazz.setChecked(false);
                    binding.checkBoxEasyPaisa.setChecked(false);



                }
            }
        });

        @SuppressLint("ResourceType")
        View dialogWithdrawView = LayoutInflater.from(this).inflate(R.layout.withdraw_layout,null);

        android.app.AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setView(dialogWithdrawView);

       TextView textView1 =dialogWithdrawView.findViewById(R.id.textViewAmount);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        binding.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!binding.editTextAmount.getText().toString().isEmpty())
                {

                 textAmount = binding.editTextAmount.getText().toString();

                    if (binding.checkBoxBank.isChecked() || binding.checkBoxJazz.isChecked() || binding.checkBoxEasyPaisa.isChecked())
                    {


                        alertDialog.show();
                        TextView textView=  dialogWithdrawView.findViewById(R.id.textViewAmount);
                        textView.setText(textAmount);

                    }
                    else {
                        Toast.makeText(WithdrawActivity.this, "Select a payment method", Toast.LENGTH_SHORT).show();


                    }

                }
                else
                {

                    Toast.makeText(WithdrawActivity.this, "Enter the amount", Toast.LENGTH_SHORT).show();


                }



            }
        });



        dialogWithdrawView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                binding.editTextAmount.setText(null);
                binding.checkBoxBank.setChecked(false);
                binding.checkBoxJazz.setChecked(false);
                binding.checkBoxEasyPaisa.setChecked(false);
            }
        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });









    }


}