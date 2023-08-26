package com.mehboob.hunzarider.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.adapters.NotificationAdapter;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.FragmentNotificationsBinding;
import com.mehboob.hunzarider.models.NotifFirebase;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {


   private FragmentNotificationsBinding binding;
   private ArrayList<NotifFirebase> list;
   private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        list= new ArrayList<>();


        fetchNotifications();


        return binding.getRoot();
    }

    private void fetchNotifications() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child(Constants.RIDER).child("UserNotifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                   binding.progresbar.setVisibility(View.GONE);
                    for (DataSnapshot snap : snapshot.getChildren()) {

                      NotifFirebase notifFirebase=  snap.getValue(NotifFirebase.class);
                      list.add(notifFirebase);

                    }
                    notificationAdapter= new NotificationAdapter(requireContext(),list);
                    binding.recyclerNotification.setAdapter(notificationAdapter);
                    binding.recyclerNotification.setLayoutManager(new LinearLayoutManager(requireContext()));


                } else {

                    binding.progresbar.setVisibility(View.GONE);
                    binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.recyclerNotification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progresbar.setVisibility(View.GONE);
                binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                binding.recyclerNotification.setVisibility(View.GONE);
            }
        });
    }
}