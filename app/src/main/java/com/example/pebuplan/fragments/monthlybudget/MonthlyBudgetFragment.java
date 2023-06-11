package com.example.pebuplan.fragments.monthlybudget;

import static com.facebook.appevents.UserDataStore.clear;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pebuplan.R;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MonthlyBudgetFragment extends Fragment implements UpdateBudgetTable {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

    TextView months;

    ImageView forward, backward;

    String incomeToSet;
    Button save, incomeSave;

    RecyclerView budget_rec_view_month;
    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();

    ImageView fab_month;

    EditText incomeInput;
    MonthlyBudgetAdapter adapter_month;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    int currentYear;
    int currentMonth;
    int currentDay;
    String selectedDate;
    TextView totalBudget, totalSpent, totalRemains;
    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();
    HashMap<Integer, String> incomeHashMap = new HashMap<>();

    public MonthlyBudgetFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_year, container, false);
        incomeInput = view.findViewById(R.id.incomeinput);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        totalBudget = view.findViewById(R.id.budget_total_month);
        totalSpent = view.findViewById(R.id.spents_total_month);
        totalRemains = view.findViewById(R.id.remains_total_month);
        incomeSave = view.findViewById(R.id.incomeSave);
        save = view.findViewById(R.id.saveMonth);

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        Gson gson = new Gson();
        setIncome(currentMonth);

        String[] monthNames = new DateFormatSymbols().getMonths();

        getCurrentMonthData(currentMonth);
        int sumOfBudget = 0;
        int sumOfSpent = 0;
        for (int start = 0; start < monthlyBillsArrayList.size(); start++) {
            sumOfBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getBudget());
            sumOfSpent += Integer.parseInt(monthlyBillsArrayList.get(start).getSpent());
        }
        totalBudget.setText(String.valueOf(sumOfBudget));
        totalSpent.setText(String.valueOf(sumOfSpent));
        totalRemains.setText(String.valueOf((sumOfBudget - sumOfSpent)));

        months = view.findViewById(R.id.timeline);
        backward = view.findViewById(R.id.backward_image);
        forward = view.findViewById(R.id.forward_image);

        months.setText(monthNames[currentMonth]);


        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthlyBillsArrayList.clear();
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                currentMonth = calendar.get(Calendar.MONTH);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months.setText(monthNames[currentMonth]);
                selectedDate = monthNames[currentMonth];
                getCurrentMonthData(currentMonth);
                if (hashMap.get(selectedDate) != null) {
                    if (monthlyBillsArrayList == null) {
                        monthlyBillsArrayList = new ArrayList<>();
                    }
                    monthlyBillsArrayList = hashMap.get(selectedDate);
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                } else {
                    monthlyBillsArrayList = new ArrayList<>();
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                }
                int sumOfBudget = 0;
                int sumOfSpent = 0;
                for (int start = 0; start < monthlyBillsArrayList.size(); start++) {
                    sumOfBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getBudget());
                    sumOfSpent += Integer.parseInt(monthlyBillsArrayList.get(start).getSpent());
                }
                totalBudget.setText(String.valueOf(sumOfBudget));
                totalSpent.setText(String.valueOf(sumOfSpent));
                totalRemains.setText(String.valueOf((sumOfBudget - sumOfSpent)));
                setIncome(currentMonth);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthlyBillsArrayList.clear();
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                currentMonth = calendar.get(Calendar.MONTH);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months.setText(monthNames[currentMonth]);
                selectedDate = monthNames[currentMonth];
                getCurrentMonthData(currentMonth);
                if (hashMap.get(selectedDate) != null) {
                    if (monthlyBillsArrayList == null) {
                        monthlyBillsArrayList = new ArrayList<>();
                    }
                    monthlyBillsArrayList = hashMap.get(selectedDate);
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                } else {
                    monthlyBillsArrayList = new ArrayList<>();
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                }
                int sumOfBudget = 0;
                int sumOfSpent = 0;
                for (int start = 0; start < monthlyBillsArrayList.size(); start++) {
                    sumOfBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getBudget());
                    sumOfSpent += Integer.parseInt(monthlyBillsArrayList.get(start).getSpent());
                }
                totalBudget.setText(String.valueOf(sumOfBudget));
                totalSpent.setText(String.valueOf(sumOfSpent));
                totalRemains.setText(String.valueOf((sumOfBudget - sumOfSpent)));
                setIncome(currentMonth);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                hashMap.put(selectedDate, monthlyBillsArrayList);
                String hashMapString = gson.toJson(hashMap);
                editor.putString("MonthData", hashMapString);
                editor.apply();
            }
        });

        incomeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String incomeString = preferences.getString("Income", "IncomeNotFound");
                if (!incomeString.equals("IncomeNotFound") && !incomeString.equals("")) {
                    java.lang.reflect.Type typeIncome = new TypeToken<HashMap<Integer, String>>() {
                    }.getType();
                    incomeHashMap = gson.fromJson(incomeString, typeIncome);
                }
                String income = incomeInput.getText().toString();
                if (!income.equals("")) {
                    incomeHashMap.put(currentMonth, income);
                    String hashmapIncome = gson.toJson(incomeHashMap);
                    editor.putString("Income", hashmapIncome);
                    editor.apply();
                }
            }
        });

        return view;
    }

    private void getCurrentMonthData(int currentMonth) {
        Gson gson = new Gson();
        String[] monthNames = new DateFormatSymbols().getMonths();
        String storedHashMapString = preferences.getString("MonthData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")) {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>() {
            }.getType();
            hashMap = gson.fromJson(storedHashMapString, type);

            selectedDate = monthNames[currentMonth];
            if (hashMap.get(selectedDate) != null) {
                if (monthlyBillsArrayList == null) {
                    monthlyBillsArrayList = new ArrayList<>();
                }
                monthlyBillsArrayList = hashMap.get(selectedDate);
            }

        }
        if (monthlyBillsArrayList != null) {
            save.setEnabled(true);
        }
    }

    private void setIncome(int currentMonth) {
        Gson gson = new Gson();
        String incomeString = preferences.getString("Income", "IncomeNotFound");
        if (!incomeString.equals("IncomeNotFound") && !incomeString.equals("")) {
            java.lang.reflect.Type typeIncome = new TypeToken<HashMap<Integer, String>>() {
            }.getType();
            HashMap<Integer, String> incomeHash = gson.fromJson(incomeString, typeIncome);
            incomeToSet = incomeHash.get(currentMonth);
            if (incomeToSet == null) {
                incomeInput.setText("");
                incomeInput.setHint("Income");
            } else {
                incomeInput.setText(incomeToSet);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (monthlyBillsArrayList == null) {
            monthlyBillsArrayList = new ArrayList<>();
        }
        adapter_month = new MonthlyBudgetAdapter(monthlyBillsArrayList);
        budget_rec_view_month = view.findViewById(R.id.rec_view_budget_month);
        budget_rec_view_month.setLayoutManager(new LinearLayoutManager(requireContext()));
        budget_rec_view_month.setAdapter(adapter_month);

        fab_month = view.findViewById(R.id.fab_month);

        fab_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BudgetCustomDialog cd = new BudgetCustomDialog(requireActivity(), MonthlyBudgetFragment.this);
                cd.show();
            }
        });
    }

    @Override
    public void update(BudgetModel budgetModel) {
        if (monthlyBillsArrayList == null) {
            monthlyBillsArrayList = new ArrayList<>();
        }
        monthlyBillsArrayList.add(budgetModel);
        adapter_month.update(monthlyBillsArrayList);
        save.setEnabled(true);
    }
}