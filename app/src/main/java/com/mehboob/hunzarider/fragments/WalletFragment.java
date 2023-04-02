package com.mehboob.hunzarider.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {
FragmentWalletBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }
}