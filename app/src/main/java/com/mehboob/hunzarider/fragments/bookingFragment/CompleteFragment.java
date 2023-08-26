package com.mehboob.hunzarider.fragments.bookingFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.hunzarider.adapters.CompletedAdapter;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.FragmentCompleteBinding;
import com.mehboob.hunzarider.models.CompletedRides;

import java.util.ArrayList;

public class CompleteFragment extends Fragment {
FragmentCompleteBinding binding;
    private ArrayList<CompletedRides> listRider;
    private CompletedAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCompleteBinding.inflate(inflater,container,false);

listRider= new ArrayList<>();

fetchCompletedRide();

        return binding.getRoot();
    }


    private void fetchCompletedRide() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER_COMPLETED_RIDES);

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

                    adapter = new CompletedAdapter(listRider, getContext());
                    binding.recyclerCompleted.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    binding.recyclerCompleted.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            binding.recyclerCompleted.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.recyclerCompleted.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}