package com.mehboob.hunzarider.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.activities.SignInActivity;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.FragmentAccountBinding;
import com.mehboob.hunzarider.models.ProfileDetailsClass;

public class AccountFragment extends Fragment {
    private ProfileDetailsClass profileDetails;
    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.logout_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogeView);

        AlertDialog dialog = builder.create();


        fetchAccount();
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialogeView.findViewById(R.id.textView17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getContext().getApplicationContext(), SignInActivity.class));
                getActivity().finishAffinity();
            }
        });

        dialogeView.findViewById(R.id.textView16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        return binding.getRoot();
    }


    private void fetchAccount() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child(Constants.RIDER).child(Constants.RIDER_PROFILE).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            profileDetails = snapshot.getValue(ProfileDetailsClass.class);

                            binding.txtUserName.setText(profileDetails.getUserName());
                            binding.txtUserEmail.setText(profileDetails.getUserEmail());
                            binding.txtUserNumber.setText(profileDetails.getUserPhoneNumber());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}