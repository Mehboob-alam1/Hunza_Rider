package com.mehboob.hunzarider.fragments.bookingFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mehboob.hunzarider.adapters.CancelledAdapter;
import com.mehboob.hunzarider.adapters.RidesAdapter;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.FragmentCancelledBinding;
import com.mehboob.hunzarider.models.ActiveRides;
import com.mehboob.hunzarider.models.CompletedRides;

import java.util.ArrayList;

public class CancelledFragment extends Fragment {
private FragmentCancelledBinding binding;
    private ArrayList<CompletedRides> listRider;
    private CancelledAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCancelledBinding.inflate(inflater
        ,container,false);

listRider= new ArrayList<>();

fetchCancelledRide();
        return binding.getRoot();
    }
    private void fetchCancelledRide() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER_CANCELLED_RIDES);

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listRider.clear();
                    binding.noDataLayout.getRoot().setVisibility(View.GONE);
                    binding.progresbar.setVisibility(View.GONE);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        CompletedRides completedRides = snap.getValue(CompletedRides.class);

                        listRider.add(completedRides);
                    }

                    adapter = new CancelledAdapter(listRider, getContext());
                    binding.recyclerCancelled.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    binding.recyclerCancelled.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.progresbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progresbar.setVisibility(View.GONE);
                binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            binding.recyclerCancelled.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.recyclerCancelled.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

}