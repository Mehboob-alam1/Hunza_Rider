package com.mehboob.hunzarider.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mehboob.hunzarider.fragments.bookingFragment.CancelledFragment;
import com.mehboob.hunzarider.fragments.bookingFragment.CompleteFragment;
import com.mehboob.hunzarider.fragments.bookingFragment.RequestFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    public TabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RequestFragment requestFragment= new RequestFragment();
                return requestFragment;
            case 1:
                CompleteFragment completeFragment = new CompleteFragment();
                return completeFragment;
            case 2:
                CancelledFragment cancelledFragment = new CancelledFragment();
                return cancelledFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
