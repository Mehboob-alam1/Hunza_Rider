package com.mehboob.hunzarider.fragments.bookingFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.hunzarider.adapters.RidesAdapter;
import com.mehboob.hunzarider.databinding.FragmentRequestBinding;
import com.mehboob.hunzarider.fragments.BookingFragment;
import com.mehboob.hunzarider.models.ActiveRides;

import java.util.ArrayList;


public class RequestFragment extends Fragment {

    private FragmentRequestBinding binding;
    private ArrayList<ActiveRides> list;
    private RidesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRequestBinding.inflate(inflater, container, false);

        list = new ArrayList<>();


        fetchRequests();

        return binding.getRoot();
    }


    private void fetchRequests() {
        binding.progresbar.setVisibility(View.VISIBLE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RiderActiveRides");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            int child = (int) snapshot.getChildrenCount();
                            ActiveRides activeRides = snapshot.getValue(ActiveRides.class);
                            list.add(activeRides);
                            Toast.makeText(getContext(), "" + activeRides.getRiderName(), Toast.LENGTH_SHORT).show();
                            adapter = new RidesAdapter(getContext(), list);
                            binding.progresbar.setVisibility(View.GONE);
                            binding.recyclerRequest.setAdapter(adapter);
                            binding.recyclerRequest.setLayoutManager(new LinearLayoutManager(getContext()));

                        } else {
                            binding.progresbar.setVisibility(View.GONE);
                            binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.progresbar.setVisibility(View.GONE);
                        binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                    }
                });
    }





}