package com.example.pebuplan.fragments.fgoal;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GoalsAdapter extends FragmentStateAdapter {

    String value;
    public GoalsAdapter(Fragment fragment, String value) {
        super(fragment);
        this.value = value;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
         switch (position) {
            case 0:
                return new DetailFragment(value);
            case 1:
                return new RecordFragment(value);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

