package com.example.pebuplan.fragments.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;


public class HelpFragment extends Fragment {

    ImageView open_budget, open_bills, open_expenses;

    TextView help1_text, help2_text, help3_text;

    public HelpFragment() {

    }

    public Boolean budget = true;
    public Boolean bills = true;
    public Boolean expenses = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_help, container, false);

        open_budget = view.findViewById(R.id.open_budget);
        open_bills = view.findViewById(R.id.open_bills);
        open_expenses = view.findViewById(R.id.open_expenses);

        help1_text = view.findViewById(R.id.help1_text);
        help2_text = view.findViewById(R.id.help2_text);
        help3_text = view.findViewById(R.id.help3_text);

        TextView title = view.findViewById(R.id.title);
        title.setText("Help");

        ImageView back_image = view.findViewById(R.id.back_image);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        open_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budget){
                    open_budget.setImageResource(R.drawable.up_arrow);
                    budget = false;
                    help1_text.setVisibility(View.VISIBLE);
                }else {
                    open_budget.setImageResource(R.drawable.down_arrow);
                    budget = true;
                    help1_text.setVisibility(View.GONE);
                }
            }
        });

        open_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bills){
                    open_bills.setImageResource(R.drawable.up_arrow);
                    bills = false;
                    help2_text.setVisibility(View.VISIBLE);
                }else {
                    open_bills.setImageResource(R.drawable.down_arrow);
                    bills = true;
                    help2_text.setVisibility(View.GONE);
                }
            }
            });

        open_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expenses){
                    open_expenses.setImageResource(R.drawable.up_arrow);
                    expenses = false;
                    help3_text.setVisibility(View.VISIBLE);
                }else {
                    open_expenses.setImageResource(R.drawable.down_arrow);
                    expenses = true;
                    help3_text.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }
}