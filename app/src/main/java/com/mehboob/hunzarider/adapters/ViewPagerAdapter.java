package com.mehboob.hunzarider.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mehboob.hunzarider.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpager_slider, container, false);

        ImageView imageView;


        imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.textviewHeading);
        TextView desc = view.findViewById(R.id.textviewDescription);

        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.frame2);
                heading.setText("Accept a job");
                desc.setText(" ");
                break;
            case 1:
                imageView.setImageResource(R.drawable.frame3);
                heading.setText("Earn money ");
                desc.setText(" ");
                break;
            case 2:
                imageView.setImageResource(R.drawable.frame4);
                heading.setText("Enable Your Location");
                desc.setText("Choose your location to start find \n" +
                        "the request around you");

                break;

        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        container.removeView((View) object);

    }
}
