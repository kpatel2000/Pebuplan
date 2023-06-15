package com.example.pebuplan.fragments.savings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SavingFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DataModel> dataList;
    private SavingsAdapter adapter;

    TextView annual_total;


    public SavingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving, container, false);


        recyclerView = view.findViewById(R.id.rec_view_savings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView back_image = view.findViewById(R.id.back_image);
        annual_total = view.findViewById(R.id.budget_saving_total_tracker);

        TextView title = view.findViewById(R.id.title);
        title.setText("Savings");

        Calendar calendar = Calendar.getInstance();

        String[] monthNames = new DateFormatSymbols().getMonths();

        dataList = new ArrayList<>();

        int total = 0;
        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        for(int i =0; i <= 11; i++){
            int totalExpenseOfMonth = 0;
            int days = 30;
           if(i % 2 != 0) {
               if(i != 1){
                   days = 30;
               }else {
                   days = 28;
               }
           }else{
               days = 31;
           }
           int year = calendar.get(Calendar.YEAR);
           int month=i+1;
           for (int start=1;start<=days;start++){
               String selectedDate;
               if (start < 10){
                   if (i<10) {
                       selectedDate = year + "-" + "0" + month + "-" + "0"+start;
                   }else{
                       selectedDate = year + "-" + month + "-" + "0"+start;
                   }
               }else{
                   if (i<10) {
                       selectedDate = year + "-" + "0" + month + "-" + start;
                   }else{
                       selectedDate = year + "-" + month + "-" + start;
                   }
               }
               String expenseOfDate = sharedPref.getString(selectedDate+"_expense","NotFound");
               if (!expenseOfDate.equals("NotFound")) {
                   totalExpenseOfMonth += Integer.parseInt(expenseOfDate);
               }
           }
           String income = sharedPref.getString("Income","0");
           int savingsOfMonth = 0;
           if (!income.equals("0")){
              savingsOfMonth = Integer.parseInt(income)-totalExpenseOfMonth;
           }
            dataList.add(new DataModel(
                        monthNames[i],
                        String.valueOf(days),
                        String.valueOf(savingsOfMonth)
           ));

//               String data = sharedPref.getString(monthNames[i] + "_Total_Remains", "No Data Found");
//               dataList.add(new DataModel(monthNames[i], String.valueOf(days), data));
            String result = String.valueOf(savingsOfMonth).replace(String.valueOf("₱"), "");
            total += Integer.parseInt(result);
        }

        annual_total.setText("₱ "+String.valueOf(total));

//        dataList.add(new DataModel("Total", String.valueOf(365),"₱" +  String.valueOf(total)));

//        dataList.add(new DataModel("April", "30", "₱200"));
        // Set adapter
        adapter = new SavingsAdapter(dataList);
        recyclerView.setAdapter(adapter);

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