package com.mehboob.hunzarider.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.adapters.TabAdapter;
import com.mehboob.hunzarider.databinding.FragmentBookingBinding;


public class BookingFragment extends Fragment {

private FragmentBookingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentBookingBinding.inflate(inflater,container,false);



        final TabAdapter adapter = new TabAdapter(getContext(),getParentFragmentManager(), binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);


        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }
}