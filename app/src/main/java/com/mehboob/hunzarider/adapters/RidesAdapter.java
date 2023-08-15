package com.mehboob.hunzarider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
     holder.txtPickUpLocation.setText(rides.getUserOriginLatitude() +""+rides.getUserOriginLongitude());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView userImage;
        private TextView txtUserName, txtPrice, txtDistance, txtPickUpLocation, txtDropLocation;

        public MyHolder(@NonNull View itemView) {

            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtPickUpLocation = itemView.findViewById(R.id.txtPickUpLocation);
            txtDropLocation = itemView.findViewById(R.id.txtDropLocation);
        }
    }
}
