package com.example.pebuplan.fragments.fgoal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainGoals extends Fragment {


    public MainGoals() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_goals, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        TextView title = view.findViewById(R.id.title);
        title.setText("Financial Goal");
        GoalsAdapter adapter = new GoalsAdapter(this);
        viewPager.setAdapter(adapter);

        Bundle bundle = getArguments();
        String value = bundle.getString("key");

        ImageView back_image = view.findViewById(R.id.back_image);

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
                    tab.setText("Detail");
                    break;
                case 1:
                    tab.setText("Record");
                    break;
            }
        }).attach();

        return view;
    }
}