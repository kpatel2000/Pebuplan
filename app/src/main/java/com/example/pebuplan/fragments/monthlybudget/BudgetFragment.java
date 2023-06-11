package com.example.pebuplan.fragments.monthlybudget;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.example.pebuplan.fragments.fgoal.GoalsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BudgetFragment extends Fragment {


    public BudgetFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        TextView title = view.findViewById(R.id.title);

        title.setText("Budget Planner");

        ImageView back_image = view.findViewById(R.id.back_image);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        BudgetAdapter adapter = new BudgetAdapter(this);
        viewPager.setAdapter(adapter);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("DAY");
                    break;
                case 1:
                    tab.setText("WEEK");
                    break;
                default:
                    tab.setText("MONTH");
                    break;
            }
        }).attach();
        return view;
    }
}