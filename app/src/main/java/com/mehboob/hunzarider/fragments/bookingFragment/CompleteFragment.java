package com.mehboob.hunzarider.fragments.bookingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.hunzarider.databinding.FragmentCompleteBinding;

public class CompleteFragment extends Fragment {
FragmentCompleteBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCompleteBinding.inflate(inflater,container,false);





        return binding.getRoot();
    }
}