package com.mehboob.hunzarider.utils;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.models.UserProfileInfo;

public class GetUserImage {
 private static Uri uri=null;

    public static Uri getUserImage(String userId) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HunzaBykea");

        databaseReference.child("UserInfo").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            UserProfileInfo data = snapshot.getValue(UserProfileInfo.class);
                         uri  = Uri.parse( data.getImage());
//                            databaseReference.child("UserInfo").child(userId).child("image")
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return uri;

    }
}
