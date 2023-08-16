package com.mehboob.hunzarider.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.models.ActiveRides;

import java.util.ArrayList;

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.MyHolder> {
    private Context context;
    private ArrayList<ActiveRides> list;


    public RidesAdapter(Context context, ArrayList<ActiveRides> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

     ActiveRides rides=   list.get(position);

     holder.txtUserName.setText(rides.getRiderName());
     holder.txtPrice.setText(rides.getFare());
     holder.txtDistance.setText(rides.getTotalDistance());
     holder.txtPickUpLocation.setText(rides.getUserOriginLatitude() +" , "+rides.getUserOriginLongitude());
     holder.txtDropLocation.setText(rides.getUserDestLatitude() +" , "+rides.getUserDestLatitude());

     holder.call.setOnClickListener(v -> {


        onCallBtnClick(rides.getRiderPhone());
     });


     holder.sms.setOnClickListener(v -> {
         sendSMS(rides.getRiderPhone());
     });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView userImage,call,sms;
        private TextView txtUserName, txtPrice, txtDistance, txtPickUpLocation, txtDropLocation;


        public MyHolder(@NonNull View itemView) {

            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtPickUpLocation = itemView.findViewById(R.id.txtPickUpLocation);
            txtDropLocation = itemView.findViewById(R.id.txtDropLocation);
            call = itemView.findViewById(R.id.btnCall);
            sms = itemView.findViewById(R.id.btnChat);

        }
    }


    private void phoneCall(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(callIntent);
        } else {
            Toast.makeText(context, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            context.startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phoneNumber);
            smsIntent.putExtra("sms_body", "message");
            context.startActivity(smsIntent);
        }
    }

    private void onCallBtnClick(String phonenumber) {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(phonenumber);
        } else {

            if (ActivityCompat.checkSelfPermission(context,
                    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall(phonenumber);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, 9);
            }
        }
    }
}
