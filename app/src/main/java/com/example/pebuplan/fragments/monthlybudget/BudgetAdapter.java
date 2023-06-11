package com.example.pebuplan.fragments.monthlybudget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BudgetAdapter extends FragmentStateAdapter {

    public BudgetAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DayFragment();
            case 1:
                return new WeeklyBudget();
            case 2:
                return new MonthlyBudgetFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}