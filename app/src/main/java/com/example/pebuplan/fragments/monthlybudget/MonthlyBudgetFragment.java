package com.example.pebuplan.fragments.monthlybudget;

import static com.facebook.appevents.UserDataStore.clear;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    Button save;

    RecyclerView budget_rec_view_month;
    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();

    ImageView fab_month;

    MonthlyBudgetAdapter adapter_month;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    int currentYear;
    int currentMonth;
    int currentDay;
    String selectedDate;
    TextView totalBudget, totalSpent, totalRemains;
    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();


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
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        totalBudget = view.findViewById(R.id.budget_total_month);
        totalSpent = view.findViewById(R.id.spents_total_month);
        totalRemains = view.findViewById(R.id.remains_total_month);
        save = view.findViewById(R.id.save_month);

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

        String[] monthNames = new DateFormatSymbols().getMonths();

        monthlyBillsArrayList = getCurrentMonthData(monthNames[currentMonth]);
        if (monthlyBillsArrayList.size() == 0){
            monthlyBillsArrayList.add(new BudgetModel(
                    "Groceries",
                    "",
                    "",
                    ""
            ));
            monthlyBillsArrayList.add(new BudgetModel(
                    "Electricity",
                    "",
                    "",
                    ""
            ));
            monthlyBillsArrayList.add(new BudgetModel(
                    "Water",
                    "",
                    "",
                    ""
            ));
            monthlyBillsArrayList.add(new BudgetModel(
                    "House Rent",
                    "",
                    "",
                    ""
            ));
            monthlyBillsArrayList.add(new BudgetModel(
                    "Gasoline",
                    "",
                    "",
                    ""
            ));
        }else{
            int sumOfDailyBudget = 0;
            int sumOfWeeklyBudget = 0;
            int sumOfMonthlyBudget = 0;
            for (int start=0;start<monthlyBillsArrayList.size();start++){
                if (!monthlyBillsArrayList.get(start).getMonthly().equals("")) {
                    sumOfDailyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getDaily());
                    sumOfWeeklyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getWeekly());
                    sumOfMonthlyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getMonthly());
                }
            }
            totalBudget.setText(String.valueOf(sumOfMonthlyBudget));
            totalSpent.setText(String.valueOf(sumOfDailyBudget));
            totalRemains.setText(String.valueOf(sumOfWeeklyBudget));
        }

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
                monthlyBillsArrayList = getCurrentMonthData(selectedDate);
                adapter_month.updateRecyclerView(monthlyBillsArrayList);
                if (monthlyBillsArrayList.size() == 0){
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<monthlyBillsArrayList.size();start++){
                        if (!monthlyBillsArrayList.get(start).getMonthly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfMonthlyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfWeeklyBudget));
                }
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
                monthlyBillsArrayList = getCurrentMonthData(selectedDate);
                if (monthlyBillsArrayList.size() == 0){
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    monthlyBillsArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_month.updateRecyclerView(monthlyBillsArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<monthlyBillsArrayList.size();start++){
                        if (!monthlyBillsArrayList.get(start).getMonthly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfMonthlyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfWeeklyBudget));
                }
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

        return view;
    }

    private ArrayList<BudgetModel> getCurrentMonthData(String  selectedDate) {
        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("MonthData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")) {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>() {
            }.getType();
            hashMap = gson.fromJson(storedHashMapString, type);
            if (hashMap.get(selectedDate) != null) {
                if (monthlyBillsArrayList == null) {
                    monthlyBillsArrayList = new ArrayList<>();
                }
                return monthlyBillsArrayList = hashMap.get(selectedDate);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (monthlyBillsArrayList == null) {
            monthlyBillsArrayList = new ArrayList<>();
        }
        adapter_month = new MonthlyBudgetAdapter(monthlyBillsArrayList,"monthly");
        budget_rec_view_month = view.findViewById(R.id.rec_view_budget_month);
        budget_rec_view_month.setLayoutManager(new LinearLayoutManager(requireContext()));
        budget_rec_view_month.setAdapter(adapter_month);

        fab_month = view.findViewById(R.id.fab_month);

        fab_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BudgetCustomDialog cd = new BudgetCustomDialog(requireActivity(), MonthlyBudgetFragment.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
            }
        });
    }

    @Override
    public void update(BudgetModel budgetModel) {
        if (monthlyBillsArrayList == null){
            monthlyBillsArrayList = new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daily = Integer.parseInt(budgetModel.getMonthly())/lastDayOfMonth;
        BudgetModel newBudgetModel = new BudgetModel(budgetModel.getCategory(),budgetModel.getMonthly(),String.valueOf(daily),String.valueOf(daily*7));
        monthlyBillsArrayList.add(newBudgetModel);
        adapter_month.update(monthlyBillsArrayList);
    }
}