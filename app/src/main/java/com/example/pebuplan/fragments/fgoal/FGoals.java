package com.example.pebuplan.fragments.fgoal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.example.pebuplan.activity.MainActivity;

public class FGoals extends Fragment {

    CardView home, debt, car, vacation, others;
    public FGoals() {

    }

    Bundle bundle = new Bundle();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f_goals, container, false);

        TextView title = view.findViewById(R.id.title);

        home = view.findViewById(R.id.home_goals);
        debt  = view.findViewById(R.id.credit_goals);
        car = view.findViewById(R.id.buy_car);
        vacation = view.findViewById(R.id.plan_vacation);
        others = view.findViewById(R.id.others);

        title.setText("Finacial Goal");

        ImageView back_image = view.findViewById(R.id.back_image);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("key", "home");
                MainGoals fragment = new MainGoals();
                fragment.setArguments(bundle);
                editor.putString("fgoals_task","Home");
                editor.apply();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("key", "debt");
                editor.putString("fgoals_task","Debt");
                editor.apply();
                MainGoals fragment = new MainGoals();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("key", "car");
                MainGoals fragment = new MainGoals();
                fragment.setArguments(bundle);
                editor.putString("fgoals_task","Car");
                editor.apply();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        vacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("key", "vacation");
                MainGoals fragment = new MainGoals();
                fragment.setArguments(bundle);
                editor.putString("fgoals_task","Vacation");
                editor.apply();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                ;

            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("key", "others");
                MainGoals fragment = new MainGoals();
                editor.putString("fgoals_task","Others");
                editor.apply();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return view;
    }

}