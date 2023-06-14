package com.example.pebuplan.fragments.tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.pebuplan.R;
import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TrackerExpensesFragment extends Fragment {


    public TrackerExpensesFragment() {
    }


    AnyChartView pieChartExpense;

    RecyclerView tracker_rec_view;
    TrackerAdapter adapter;
    ArrayList<BudgetModel> trackerArrayList = new ArrayList<>();
    TextView months_expense, totalBudget, totalExpense;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView forward_expense, backward_expense;
    int currentYear;
    int currentMonth;
    String selectedDate;
    List<DataEntry> dataEntries;
    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();
    HashMap<Integer, String > incomeHashMap = new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = Calendar.getInstance().getTime();
        selectedDate = format.format(currentDate);
        trackerArrayList = getDayData(selectedDate);
        totalBudget = view.findViewById(R.id.budget_total_tracker);
        totalExpense = view.findViewById(R.id.expense_total_tracker);
        if (trackerArrayList.size() == 0){
            trackerArrayList.add(new BudgetModel(
                    "Groceries",
                    "",
                    ""
            ));
            trackerArrayList.add(new BudgetModel(
                    "Electricity",
                    "",
                    ""
            ));
            trackerArrayList.add(new BudgetModel(
                    "Water",
                    "",
                    ""
            ));
            trackerArrayList.add(new BudgetModel(
                    "House Rent",
                    "",
                    ""
            ));
            trackerArrayList.add(new BudgetModel(
                    "Gasoline",
                    "",
                    ""
            ));
        }else{
            int sumOfDailyBudget = 0;
            int sumOfExpense = 0;
            for (int start=0;start<trackerArrayList.size();start++){
                if (!trackerArrayList.get(start).getDaily().equals("") && trackerArrayList.get(start).getExpense() != null) {
                    sumOfDailyBudget += Integer.parseInt(trackerArrayList.get(start).getDaily());
                    sumOfExpense += Integer.parseInt(trackerArrayList.get(start).getExpense());
                }
            }
            totalBudget.setText(String.valueOf(sumOfDailyBudget));
            totalExpense.setText(String.valueOf(sumOfExpense));
        }

        tracker_rec_view = view.findViewById(R.id.rec_view_tracker_expense);
        tracker_rec_view.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TrackerAdapter(trackerArrayList);
        tracker_rec_view.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH)+1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);

        String[] monthNames = new DateFormatSymbols().getMonths();

        months_expense = view.findViewById(R.id.timeline_expense);

        backward_expense = view.findViewById(R.id.backward_image_expense);
        forward_expense = view.findViewById(R.id.forward_image_expense);

        months_expense.setText(monthNames[month] + " " + day + ", " + year);

        backward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackerArrayList.clear();
                calendar.add(Calendar.DATE, -1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = calendar.getTime();
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DATE);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + " " + day + ", " + year);
                selectedDate = format.format(currentDate);
                trackerArrayList = getDayData(selectedDate);
                adapter.updateRecyclerView(trackerArrayList);
                if (trackerArrayList.size() == 0){
                    trackerArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            ""
                    ));
                    adapter.updateRecyclerView(trackerArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfExpense = 0;
                    for (int start=0;start<trackerArrayList.size();start++){
                        if (!trackerArrayList.get(start).getDaily().equals("") && trackerArrayList.get(start).getExpense() != null) {
                            sumOfDailyBudget += Integer.parseInt(trackerArrayList.get(start).getDaily());
                            sumOfExpense += Integer.parseInt(trackerArrayList.get(start).getExpense());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfDailyBudget));
                    totalExpense.setText(String.valueOf(sumOfExpense));
                }
                setPieChart(selectedDate);

            }
        });

        forward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackerArrayList.clear();
                calendar.add(Calendar.DATE, 1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = calendar.getTime();
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DATE);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + " " + day + ", " + year);
                selectedDate = format.format(currentDate);
                trackerArrayList = getDayData(selectedDate);
                adapter.updateRecyclerView(trackerArrayList);
                if (trackerArrayList.size() == 0){
                    trackerArrayList.add(new BudgetModel(
                            "Groceries",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Electricity",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Water",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "House Rent",
                            "",
                            ""
                    ));
                    trackerArrayList.add(new BudgetModel(
                            "Gasoline",
                            "",
                            ""
                    ));
                    adapter.updateRecyclerView(trackerArrayList);
                }else{
                    int sumOfDailyBudget = 0;
                    int sumOfExpense = 0;
                    for (int start=0;start<trackerArrayList.size();start++){
                        if (!trackerArrayList.get(start).getDaily().equals("") && trackerArrayList.get(start).getExpense() != null) {
                            sumOfDailyBudget += Integer.parseInt(trackerArrayList.get(start).getDaily());
                            sumOfExpense += Integer.parseInt(trackerArrayList.get(start).getExpense());
                        }
                    }
                    totalBudget.setText(String.valueOf(sumOfDailyBudget));
                    totalExpense.setText(String.valueOf(sumOfExpense));
                }
                setPieChart(selectedDate);
            }
        });
        return view;
    }


    private void setPieChart(String selectedDate) {
        trackerArrayList = getDayData(selectedDate);
        if (trackerArrayList.isEmpty()){
            Toast.makeText(requireContext(),"Please enter your budget data",Toast.LENGTH_SHORT).show();
            dataEntries = new ArrayList<>();
            dataEntries.add(new ValueDataEntry("No Value",0));
            Pie pieExpense = AnyChart.pie();
            if (dataEntries.size() != 0) {
                pieExpense.data(dataEntries);
                pieChartExpense.setChart(pieExpense);
            }
        }else{
            dataEntries = new ArrayList<>();
            for (int start=0;start<trackerArrayList.size();start++){
                if (!trackerArrayList.get(start).getDaily().equals("")) {
                    int budget = Integer.parseInt(trackerArrayList.get(start).getDaily());
                    dataEntries.add(new ValueDataEntry(trackerArrayList.get(start).getCategory(), budget));
                }
            }
            Pie pieExpense = AnyChart.pie();
            if (dataEntries.size() != 0) {
                pieExpense.data(dataEntries);
                pieChartExpense.setChart(pieExpense);
            }
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChartExpense = view.findViewById(R.id.pieChartExpense);
        setPieChart(selectedDate);
    }
    private ArrayList<BudgetModel> getDayData(String selectedDate) {
        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
            hashMap = gson.fromJson(storedHashMapString, type);
            if (hashMap.get(selectedDate) != null) {
                if (trackerArrayList == null){
                    trackerArrayList = new ArrayList<>();
                }
                return trackerArrayList = hashMap.get(selectedDate);
            }
        }
        return new ArrayList<>();
    }

}