package com.mehboob.hunzarider.fragments.bookingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.hunzarider.databinding.FragmentRequestBinding;


public class RequestFragment extends Fragment {

 FragmentRequestBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRequestBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }
}