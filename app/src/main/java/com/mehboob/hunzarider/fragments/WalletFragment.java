package com.mehboob.hunzarider.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.hunzarider.WithdrawActivity;

import com.mehboob.hunzarider.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {
FragmentWalletBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater,container,false);


        binding.btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



startActivity(new Intent(getActivity(), WithdrawActivity.class));
            }
        });

        return binding.getRoot();
    }
}