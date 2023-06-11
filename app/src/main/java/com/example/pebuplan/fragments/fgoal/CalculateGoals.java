package com.example.pebuplan.fragments.fgoal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CalculateGoals extends Fragment implements DatePickerDialog.OnDateSetListener{


    TextInputEditText home_price,downpayment_price;

    EditText datetext;
    ImageView date_image;

    Button sumbit_btn;
    public CalculateGoals() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_goals, container, false);
        home_price = view.findViewById(R.id.home_edit_text);
        downpayment_price = view.findViewById(R.id.downpayment_edit_text);
        datetext = view.findViewById(R.id.date_edit_text);
        date_image = view.findViewById(R.id.date_image);
        sumbit_btn = view.findViewById(R.id.submit_btn);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        String name_plate = sharedPref.getString("fgoals_task", "Home");

        date_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), CalculateGoals.this, year, month, day);
                datePickerDialog.show();
            }
        });



        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(name_plate + "fgoals_price", home_price.getText().toString());
                editor.putString(name_plate + "fgoals_downpayment", downpayment_price.getText().toString());
                editor.putString(name_plate + "fgoals_date", datetext.getText().toString());
                editor.apply();
                Toast.makeText(requireContext(),"Data Saved!", Toast.LENGTH_LONG).show();
            }
        });

        datetext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), CalculateGoals.this, year, month, day);
                    datePickerDialog.show();

                    datetext.clearFocus();
                } else {
                    datetext.clearFocus();
                }
            }
        });


        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        datetext.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
    }

}