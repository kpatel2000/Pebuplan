package com.example.pebuplan.fragments.monthlybudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class IncomeFragment extends Fragment {

    EditText incomeInput;
    Button submit;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        incomeInput = view.findViewById(R.id.income_budget_planner);
        submit = view.findViewById(R.id.submit_income);

        ImageView back_image = view.findViewById(R.id.back_image);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String income = incomeInput.getText().toString();
                if (!income.equals("")) {
                    editor.putString("Income", income);
                    editor.apply();

                    BudgetFragment fragment= new BudgetFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main, fragment)
                            .addToBackStack(null)
                            .commit();

                }else{
                    Toast.makeText(getActivity(), "Please enter your income first", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;


    }
}