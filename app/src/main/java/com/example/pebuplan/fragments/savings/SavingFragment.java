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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SavingFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DataModel> dataList;
    private SavingsAdapter adapter;


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


        recyclerView = view.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView back_image = view.findViewById(R.id.back_image);

        TextView title = view.findViewById(R.id.title);
        title.setText("Savings");

        Calendar calendar = Calendar.getInstance();

        String[] monthNames = new DateFormatSymbols().getMonths();

        dataList = new ArrayList<>();

        int total = 0;
        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        for(int i =0; i <= 11; i++){
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
               String data = sharedPref.getString(monthNames[i] + "_Total_Remains", "No Data Found");
               dataList.add(new DataModel(monthNames[i], String.valueOf(days), data));
               if(!data.equals("No Data Found")){
                   String result = data.replace(String.valueOf("₱"), "");
                   total += Integer.parseInt(result);
               }
        }

        dataList.add(new DataModel("Total", String.valueOf(365),"₱" +  String.valueOf(total)));

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