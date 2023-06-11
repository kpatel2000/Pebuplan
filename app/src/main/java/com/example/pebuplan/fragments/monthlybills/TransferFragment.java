package com.example.pebuplan.fragments.monthlybills;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.example.pebuplan.fragments.fgoal.CalculateGoals;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;


public class TransferFragment extends Fragment {

    ImageView date_image;
    TextInputEditText datetext,amount,transfer;
    String selectedItem;
    String cat_selectItem;
    int month_date;
    public TransferFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        Spinner spinner = view.findViewById(R.id.cate_spinner);

        amount = view.findViewById(R.id.amount_tbox);

        transfer = view.findViewById(R.id.transfer_tbox);

        Button pay_btn = view.findViewById(R.id.pay_button);

        datetext = view.findViewById(R.id.date_tbox);

        Spinner spinner2 = view.findViewById(R.id.cate_payment);

        String[] items = {"Bills and utilities", "Education", "Family Care", "Investment", "Insurance","Drink & Dine"};

        String[] items2 = {"Paid","Unpaid"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, items);

        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_selectItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        date_image = view.findViewById(R.id.imageView19);

        date_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                month_date = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), TransferFragment.this::onDateSet, year, month_date, day);
                datePickerDialog.show();
            }
        });

        datetext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    month_date = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), TransferFragment.this::onDateSet, year, month_date, day);
                    datePickerDialog.show();

                    datetext.clearFocus();
                } else {
                    datetext.clearFocus();
                }
            }
        });

/*        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(datetext.getText().toString());
        arrayList.add(amount.getText().toString());
        arrayList.add(transfer.getText().toString());
        arrayList.add(selectedItem.toString());
        arrayList.add(cat_selectItem.toString());*/

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        String savedListJson = sharedPreferences.getString("month", "");
        ArrayList<String> month;

        if (!savedListJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            month = new Gson().fromJson(savedListJson, type);
        } else {
            month = new ArrayList<>();
        }

        String savedListJson2 = sharedPreferences.getString("cate", "");
        ArrayList<String> cate;

        if (!savedListJson2.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            cate = new Gson().fromJson(savedListJson2, type);
        } else {
            cate = new ArrayList<>();
        }

        String savedListJson3 = sharedPreferences.getString("amount", "");
        ArrayList<String> amoun;

        if (!savedListJson3.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            amoun = new Gson().fromJson(savedListJson3, type);
        } else {
            amoun = new ArrayList<>();
        }

        String savedListJson4 = sharedPreferences.getString("mode", "");
        ArrayList<String> mode;

        if (!savedListJson4.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            mode = new Gson().fromJson(savedListJson4, type);
        } else {
            mode = new ArrayList<>();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!datetext.getText().toString().isEmpty() && !amount.getText().toString().isEmpty() && !transfer.getText().toString().isEmpty()) {

                    month.add(new DateFormatSymbols().getMonths()[month_date]);
                    cate.add(cat_selectItem);
                    amoun.add("₱" + amount.getText().toString());
                    mode.add(selectedItem);


                    String listJson1 = new Gson().toJson(month);
                    String listJson2 = new Gson().toJson(cate);
                    String listJson3 = new Gson().toJson(amoun);
                    String listJson4 = new Gson().toJson(mode);

                    editor.putString("month", listJson1);
                    editor.putString("cate", listJson2);
                    editor.putString("amount", listJson3);
                    editor.putString("mode", listJson4);
                    editor.apply();

                    Toast.makeText(requireContext(),"Your Data has been saved !",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Every field is important", Toast.LENGTH_SHORT).show();
                    Log.d("i", "Every field is important");
                }
            }
        });

        return view;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        datetext.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
    }
}