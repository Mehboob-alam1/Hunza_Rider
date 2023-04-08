package com.mehboob.hunzarider.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.activities.SignInActivity;
import com.mehboob.hunzarider.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding =FragmentAccountBinding.inflate(inflater,container,false);
      View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.logout_layout,null);

      AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
      builder.setView(dialogeView);

      AlertDialog dialog = builder.create();

      binding.logout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.show();
          }
      });

      dialogeView.findViewById(R.id.textView17).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(getActivity().getApplicationContext(), "Logout successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext().getApplicationContext(), SignInActivity.class));
        getActivity().finish();
          }
      });

      dialogeView.findViewById(R.id.textView16).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(getActivity().getApplicationContext(), "Cancel Successfully", Toast.LENGTH_SHORT).show();
         dialog.dismiss();
          }
      });




        return binding.getRoot();
    }
}