package com.example.pebuplan.fragments.monthlybudget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WeeklyBudget extends Fragment implements UpdateBudgetTable{


    TextView curr_month1, curr_month2, curr_month3, curr_month4;
    String current_month;
    LinearLayout ll_week1, ll_week2, ll_week3, ll_week4;
    View select_view1, select_view2, select_view3, select_view4;
    Button saveWeekBudget;
    RecyclerView budget_rec_view_week;
    ImageView fab;
    String selectedDate = "week1";

    MonthlyBudgetAdapter adapter_week;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    TextView totalSpent,totalBudget,totalRemains;
    int currentDay;
    int currentMonth;
    int currentYear;
    ArrayList<BudgetModel> weeklyBillArrayList = new ArrayList<>();

    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_monthly_budget, container, false);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalBudget = view.findViewById(R.id.budget_total_week);
        totalSpent = view.findViewById(R.id.spents_total_week);
        totalRemains = view.findViewById(R.id.remains_total_week);
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;

        weeklyBillArrayList = getWeekData("week1");
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();

        if (weeklyBillArrayList.size() == 0){
            weeklyBillArrayList.add(new BudgetModel(
                    "Groceries",
                    "",
                    "",
                    ""
            ));
            weeklyBillArrayList.add(new BudgetModel(
                    "Electricity",
                    "",
                    "",
                    ""
            ));
            weeklyBillArrayList.add(new BudgetModel(
                    "Water",
                    "",
                    "",
                    ""
            ));
            weeklyBillArrayList.add(new BudgetModel(
                    "House Rent",
                    "",
                    "",
                    ""
            ));
            weeklyBillArrayList.add(new BudgetModel(
                    "Gasoline",
                    "",
                    "",
                    ""
            ));
        }else{
            int sumOfDailyBudget = 0;
            int sumOfWeeklyBudget = 0;
            int sumOfMonthlyBudget = 0;
            for (int start=0;start<weeklyBillArrayList.size();start++){
                if (!weeklyBillArrayList.get(start).getWeekly().equals("")) {
                    sumOfDailyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getDaily());
                    sumOfWeeklyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getWeekly());
                    sumOfMonthlyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getMonthly());
                }
            }
            totalBudget.setText(String.valueOf(sumOfWeeklyBudget));
            totalSpent.setText(String.valueOf(sumOfDailyBudget));
            totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
        }
        current_month = dateFormat.format(date);

        curr_month1 = view.findViewById(R.id.curr_month1);
        curr_month2 = view.findViewById(R.id.curr_month2);
        curr_month3 = view.findViewById(R.id.curr_month3);
        curr_month4 = view.findViewById(R.id.curr_month4);

        curr_month1.setText(current_month);
        curr_month2.setText(current_month);
        curr_month3.setText(current_month);
        curr_month4.setText(current_month);

        ll_week1 = view.findViewById(R.id.ll_week1);
        ll_week2 = view.findViewById(R.id.ll_week2);
        ll_week3 = view.findViewById(R.id.ll_week3);
        ll_week4 = view.findViewById(R.id.ll_week4);

        select_view1 = view.findViewById(R.id.select_Week1);
        select_view2 = view.findViewById(R.id.select_Week2);
        select_view3 = view.findViewById(R.id.select_Week3);
        select_view4 = view.findViewById(R.id.select_Week4);

        ll_week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.VISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                selectedDate = "week1";
                weeklyBillArrayList = getWeekData(selectedDate);
                adapter_week.updateRecyclerView(weeklyBillArrayList);

                if (weeklyBillArrayList.size() == 0){
                    weeklyBillArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_week.updateRecyclerView(weeklyBillArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<weeklyBillArrayList.size();start++){
                        if (!weeklyBillArrayList.get(start).getWeekly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfWeeklyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
                }
            }
        });

        ll_week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.VISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                weeklyBillArrayList.clear();

                selectedDate = "week2";
                weeklyBillArrayList = getWeekData(selectedDate);
                adapter_week.updateRecyclerView(weeklyBillArrayList);
                if (weeklyBillArrayList.size() == 0){
                    weeklyBillArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_week.updateRecyclerView(weeklyBillArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<weeklyBillArrayList.size();start++){
                        if (!weeklyBillArrayList.get(start).getWeekly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfWeeklyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
                }
            }
        });

        ll_week3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.VISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                weeklyBillArrayList.clear();

                selectedDate = "week3";
                weeklyBillArrayList = getWeekData(selectedDate);
                adapter_week.updateRecyclerView(weeklyBillArrayList);
                if (weeklyBillArrayList.size() == 0){
                    weeklyBillArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_week.updateRecyclerView(weeklyBillArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<weeklyBillArrayList.size();start++){
                        if (!weeklyBillArrayList.get(start).getWeekly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfWeeklyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
                }
            }
        });

        ll_week4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.VISIBLE);
                weeklyBillArrayList.clear();

                selectedDate = "week4";
                weeklyBillArrayList = getWeekData(selectedDate);
                adapter_week.updateRecyclerView(weeklyBillArrayList);
                if (weeklyBillArrayList.size() == 0){
                    weeklyBillArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            "",
                            ""
                    ));
                    weeklyBillArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            "",
                            ""
                    ));

                    totalBudget.setText("");
                    totalSpent.setText("");
                    totalRemains.setText("");
                    adapter_week.updateRecyclerView(weeklyBillArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfWeeklyBudget = 0;
                    int sumOfMonthlyBudget = 0;
                    for (int start=0;start<weeklyBillArrayList.size();start++){
                        if (!weeklyBillArrayList.get(start).getWeekly().equals("")) {
                            sumOfDailyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getDaily());
                            sumOfWeeklyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getWeekly());
                            sumOfMonthlyBudget += Integer.parseInt(weeklyBillArrayList.get(start).getMonthly());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfWeeklyBudget));
                    totalSpent.setText(String.valueOf(sumOfDailyBudget));
                    totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
                }
            }
        });
        if (weeklyBillArrayList == null){
            weeklyBillArrayList = new ArrayList<>();
        }
        adapter_week = new MonthlyBudgetAdapter(weeklyBillArrayList,"weekly");
        budget_rec_view_week = view.findViewById(R.id.rec_view_budget_week);
        budget_rec_view_week.setLayoutManager(new LinearLayoutManager(requireContext()));
        budget_rec_view_week.setAdapter(adapter_week);


        fab = view.findViewById(R.id.fab_week);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BudgetCustomDialog cd = new BudgetCustomDialog(requireActivity(), WeeklyBudget.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
            }
        });

        saveWeekBudget = view.findViewById(R.id.save_week);
        saveWeekBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashMap.put(selectedDate,weeklyBillArrayList);
                Gson gson = new Gson();
                String hashMapString = gson.toJson(hashMap);
                editor.putString("WeekData", hashMapString);
                editor.apply();
            }
        });

    }

    private ArrayList<BudgetModel> getWeekData(String selectedDate) {
        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("WeekData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
            hashMap = gson.fromJson(storedHashMapString, type);
            if (hashMap.get(selectedDate) != null) {
                if (weeklyBillArrayList == null){
                    weeklyBillArrayList = new ArrayList<>();
                }
                return weeklyBillArrayList = hashMap.get(selectedDate);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void update(BudgetModel budgetModel) {
        if (weeklyBillArrayList == null){
            weeklyBillArrayList = new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daily = Integer.parseInt(budgetModel.getWeekly())/7;
        BudgetModel newBudgetModel = new BudgetModel(budgetModel.getCategory(),budgetModel.getWeekly(),String.valueOf(daily),String.valueOf(daily*lastDayOfMonth));
        weeklyBillArrayList.add(newBudgetModel);
        adapter_week.update(weeklyBillArrayList);
    }
}