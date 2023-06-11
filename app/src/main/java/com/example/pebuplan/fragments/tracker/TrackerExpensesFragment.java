package com.example.pebuplan.fragments.tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TrackerExpensesFragment extends Fragment {


    public TrackerExpensesFragment() {
    }


    AnyChartView pieChartExpense;

    TextView months_expense;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView forward_expense, backward_expense;

    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();
    int currentYear;
    int currentMonth;
    int currentDay;
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
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH)+1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String[] monthNames = new DateFormatSymbols().getMonths();

        months_expense = view.findViewById(R.id.timeline_expense);

        backward_expense = view.findViewById(R.id.backward_image_expense);
        forward_expense = view.findViewById(R.id.forward_image_expense);

        months_expense.setText(monthNames[month] + ", " + year);

        backward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + ", " + year);
                getCurrentMonthData(month);
            }
        });

        forward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + ", " + year);
                getCurrentMonthData(month);
            }
        });
        return view;
    }

    private void setPieChart(int currentMonth) {
        int income = getIncomeData(currentMonth);
        int rest = income;
        if (income == 0){
            Toast.makeText(requireContext(),"Please Enter Monthly Income first",Toast.LENGTH_SHORT).show();
            dataEntries = new ArrayList<>();
            dataEntries.add(new ValueDataEntry("No Value",0));
            Pie pieExpense = AnyChart.pie();
            if (dataEntries.size() != 0) {
                pieExpense.data(dataEntries);
                pieChartExpense.setChart(pieExpense);
            }
        }else{
            dataEntries = new ArrayList<>();
            for (int start=0;start<monthlyBillsArrayList.size();start++){
                if (!monthlyBillsArrayList.get(start).getSpent().equals("")) {
                    int spent = Integer.parseInt(monthlyBillsArrayList.get(start).getSpent());
                    rest -= spent;
                    float percentage =((float)spent/income)*100;
                    dataEntries.add(new ValueDataEntry(monthlyBillsArrayList.get(start).getCategory(), percentage));
                }
            }
            if (rest != 0){
                float percentage = ((float)rest/income)*100;
                dataEntries.add(new ValueDataEntry("Remaining", percentage));
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
        getCurrentMonthData(currentMonth-1);

    }

    private void getCurrentMonthData(int currentMonth) {
        Gson gson = new Gson();
        String[] monthNames = new DateFormatSymbols().getMonths();
        String storedHashMapString = preferences.getString("MonthData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
            hashMap = gson.fromJson(storedHashMapString, type);

            selectedDate = monthNames[currentMonth];
            if (hashMap.size() == 1){
                if (hashMap.containsKey("null")){
                    Toast.makeText(requireContext(), "Please add your spent details",Toast.LENGTH_SHORT).show();
                }else{
                    if (hashMap.get(selectedDate) != null) {
                        if (monthlyBillsArrayList == null){
                            monthlyBillsArrayList = new ArrayList<>();
                        }
                        monthlyBillsArrayList = hashMap.get(selectedDate);
                        setPieChart(currentMonth);
                    }
                }
            }else{
                if (hashMap.get(selectedDate) != null) {
                    if (monthlyBillsArrayList == null){
                        monthlyBillsArrayList = new ArrayList<>();
                    }
                    monthlyBillsArrayList = hashMap.get(selectedDate);
                    setPieChart(currentMonth);
                }else{
                    setPieChart(currentMonth);
                }
            }
        }
    }

    private int getIncomeData(int currentMonth){
        Gson gson = new Gson();
        String incomeString = preferences.getString("Income", "IncomeNotFound");
        if(!incomeString.equals("IncomeNotFound") && !incomeString.equals("")) {
            java.lang.reflect.Type typeIncome = new TypeToken<HashMap<Integer, String>>() {
            }.getType();
            incomeHashMap = gson.fromJson(incomeString, typeIncome);
        }
        if (incomeHashMap != null){
            String income = incomeHashMap.get(currentMonth);
            if (income != null) {
                return Integer.parseInt(income);
            }
        }
        return 0;
    }

}