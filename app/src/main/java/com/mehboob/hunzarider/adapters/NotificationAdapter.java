package com.mehboob.hunzarider.adapters;

import static com.mehboob.hunzarider.utils.ConvertMilliToDate.getMyPrettyDate;
import static com.mehboob.hunzarider.utils.GetUserImage.getUserImage;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.models.NotifFirebase;
import com.mehboob.hunzarider.models.NotificationData;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private Context context;
    private ArrayList<NotifFirebase> list;


    public NotificationAdapter(Context context, ArrayList<NotifFirebase> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view
                = LayoutInflater.from(context).inflate(R.layout.notification_sample, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

        NotifFirebase data = list.get(position);

        holder.date.setText(getMyPrettyDate(Long.parseLong(data.getTime())));
        holder.userName.setText(data.getTitle());
        holder.notificationText.setText(data.getMessage());

       Uri uri= getUserImage(data.getFrom());
       if(uri!=null){
           Glide.with(context).load(uri)
                   .into(holder.userImage);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        public ImageView userImage;
        private TextView userName;
        private TextView notificationText;
        private TextView date;


        public NotificationHolder(@NonNull View itemView) {
            super(itemView);


            userImage = itemView.findViewById(R.id.image);
            userName = itemView.findViewById(R.id.name);
            notificationText = itemView.findViewById(R.id.comment);
            date = itemView.findViewById(R.id.date);
        }
    }
}
