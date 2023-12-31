package com.example.pebuplan.fragments.monthlybudget;

import android.content.Context;
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

import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.adapter.MonthlyBillAdapter;
import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class DayFragment extends Fragment implements UpdateBudgetTable{


    RecyclerView budget_rec_view;
    ArrayList<BudgetModel> budgetBillsArrayList = new ArrayList<>();
    ImageView fab;
    Button saveBudget;
    String selectedDate;
    Date currentDate;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    MonthlyBudgetAdapter adapter;
    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();

    TextView totalBudget, totalSpent, totalRemains;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_day, container, false);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = Calendar.getInstance().getTime();
        selectedDate = format.format(currentDate);
        budgetBillsArrayList = getDayData(selectedDate);

        totalBudget = view.findViewById(R.id.budget_total);
        totalSpent = view.findViewById(R.id.spents_total);
        totalRemains = view.findViewById(R.id.remains_total);

        if (budgetBillsArrayList.size() == 0){
            budgetBillsArrayList.add(new BudgetModel(
                    "Groceries",
                    "",
                    "",
                    ""
            ));
            budgetBillsArrayList.add(new BudgetModel(
                    "Electricity",
                    "",
                    "",
                    ""
            ));
            budgetBillsArrayList.add(new BudgetModel(
                    "Water",
                    "",
                    "",
                    ""
            ));
            budgetBillsArrayList.add(new BudgetModel(
                    "House Rent",
                    "",
                    "",
                    ""
            ));
            budgetBillsArrayList.add(new BudgetModel(
                    "Gasoline",
                    "",
                    "",
                    ""
            ));
        }else{
            int sumOfDailyBudget = 0;
            int sumOfWeeklyBudget = 0;
            int sumOfMonthlyBudget = 0;
            for (int start=0;start<budgetBillsArrayList.size();start++){
                if (!budgetBillsArrayList.get(start).getDaily().equals("")) {
                    sumOfDailyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getDaily());
                    sumOfWeeklyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getWeekly());
                    sumOfMonthlyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getMonthly());
                }
            }
            totalBudget.setText(String.valueOf(sumOfDailyBudget));
            totalSpent.setText(String.valueOf(sumOfWeeklyBudget));
            totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
        }
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                // on below line we are adding a range
                // as start date and end date to our calendar.
                .range(startDate, endDate)
                // on below line we are providing a number of dates
                // which will be visible on the screen at a time.
                .datesNumberOnScreen(5)
                // at last we are calling a build method
                // to build our horizontal recycler view.
                .build();
        // on below line we are setting calendar listener to our calendar view.
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date selDate = date.getTime();
                selectedDate = format.format(selDate);
                Gson gson = new Gson();
                String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
                if(!storedHashMapString.equals("oopsDintWork")){
                    java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
                    HashMap<String, ArrayList<BudgetModel>> hashMap = gson.fromJson(storedHashMapString, type);
                    if (budgetBillsArrayList != null && hashMap.get(selectedDate) != null) {
                        budgetBillsArrayList = hashMap.get(selectedDate);
                        adapter.updateRecyclerView(budgetBillsArrayList);
                        if (budgetBillsArrayList.size() == 0){
                            budgetBillsArrayList.add(new BudgetModel(
                                    "Groceries",
                                    "",
                                    "",
                                    ""
                            ));
                            budgetBillsArrayList.add(new BudgetModel(
                                    "Electricity",
                                    "",
                                    "",
                                    ""
                            ));
                            budgetBillsArrayList.add(new BudgetModel(
                                    "Water",
                                    "",
                                    "",
                                    ""
                            ));
                            budgetBillsArrayList.add(new BudgetModel(
                                    "House Rent",
                                    "",
                                    "",
                                    ""
                            ));
                            budgetBillsArrayList.add(new BudgetModel(
                                    "Gasoline",
                                    "",
                                    "",
                                    ""
                            ));
                        }else{
                            int sumOfDailyBudget = 0;
                            int sumOfWeeklyBudget = 0;
                            int sumOfMonthlyBudget = 0;
                            for (int start=0;start<budgetBillsArrayList.size();start++){
                                if (!budgetBillsArrayList.get(start).getDaily().equals("")) {
                                    sumOfDailyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getDaily());
                                    sumOfWeeklyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getWeekly());
                                    sumOfMonthlyBudget += Integer.parseInt(budgetBillsArrayList.get(start).getMonthly());
                                }
                            }
                            totalBudget.setText(String.valueOf(sumOfDailyBudget));
                            totalSpent.setText(String.valueOf(sumOfWeeklyBudget));
                            totalRemains.setText(String.valueOf(sumOfMonthlyBudget));
                        }
                    }else{
                        budgetBillsArrayList = new ArrayList<>();
                        budgetBillsArrayList.add(new BudgetModel(
                                "Groceries",
                                "",
                                "",
                                ""
                        ));
                        budgetBillsArrayList.add(new BudgetModel(
                                "Electricity",
                                "",
                                "",
                                ""
                        ));
                        budgetBillsArrayList.add(new BudgetModel(
                                "Water",
                                "",
                                "",
                                ""
                        ));
                        budgetBillsArrayList.add(new BudgetModel(
                                "House Rent",
                                "",
                                "",
                                ""
                        ));
                        budgetBillsArrayList.add(new BudgetModel(
                                "Gasoline",
                                "",
                                "",
                                ""
                        ));
                        adapter.updateRecyclerView(budgetBillsArrayList);

                        totalBudget.setText("");
                        totalSpent.setText("");
                        totalRemains.setText("");
                    }
                }

            }
        });

        fab = view.findViewById(R.id.fab_day);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BudgetCustomDialog cd = new BudgetCustomDialog(requireActivity(), DayFragment.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
            }
        });

        budget_rec_view = view.findViewById(R.id.rec_view_budget);
        budget_rec_view.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (budgetBillsArrayList == null){
            budgetBillsArrayList = new ArrayList<>();
        }
        adapter = new MonthlyBudgetAdapter(budgetBillsArrayList, "daily");
        budget_rec_view.setAdapter(adapter);

        saveBudget = view.findViewById(R.id.save_budget);
        saveBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashMap.put(selectedDate,budgetBillsArrayList);
                Gson gson = new Gson();
                String hashMapString = gson.toJson(hashMap);
                editor.putString("DayData", hashMapString);
                editor.apply();
            }
        });
    }

    private ArrayList<BudgetModel> getDayData(String selectedDate) {
        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
            hashMap = gson.fromJson(storedHashMapString, type);
            if (hashMap.get(selectedDate) != null) {
                if (budgetBillsArrayList == null){
                    budgetBillsArrayList = new ArrayList<>();
                }
                return budgetBillsArrayList = hashMap.get(selectedDate);
            }
        }
        return new ArrayList<>();
    }


    @Override
    public void update(BudgetModel budgetModel) {
        if (budgetBillsArrayList == null){
            budgetBillsArrayList = new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daily = Integer.parseInt(budgetModel.getDaily());
        BudgetModel newBudgetModel = new BudgetModel(budgetModel.getCategory(),budgetModel.getDaily(),String.valueOf(daily*7),String.valueOf(daily*lastDayOfMonth));
        budgetBillsArrayList.add(newBudgetModel);
        adapter.update(budgetBillsArrayList);
    }
}