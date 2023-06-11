package com.example.pebuplan.fragments.monthlybills;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pebuplan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MonthlyReviewFragment extends Fragment {


    public MonthlyReviewFragment() {
        // Required empty public constructor
    }


    public static MonthlyReviewFragment newInstance(String param1, String param2) {
        MonthlyReviewFragment fragment = new MonthlyReviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly_review, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

/*        List<String> monthList = new ArrayList<>();
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");

        List<String> categoryList = new ArrayList<>();
        categoryList.add("A");
        categoryList.add("B");
        categoryList.add("C");

        List<String> amountList = new ArrayList<>();
        amountList.add("E");
        amountList.add("F");
        amountList.add("G");

        List<String> modeList = new ArrayList<>();
        modeList.add("G");
        modeList.add("H");
        modeList.add("I");*/

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("month", "");

        String json2 = sharedPreferences.getString("cate", "");

        String json3 = sharedPreferences.getString("amount", "");

        String json4 = sharedPreferences.getString("mode", "");

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> monthList = gson.fromJson(json, type);
        ArrayList<String> categoryList = gson.fromJson(json2, type);
        ArrayList<String> amountList = gson.fromJson(json3, type);
        ArrayList<String> modeList = gson.fromJson(json4, type);

        Log.d("Tag1", String.valueOf(modeList.size()));
        Log.d("Tag2", String.valueOf(categoryList.size()));
        Log.d("Tag3", String.valueOf(amountList.size()));
        Log.d("Tag4", String.valueOf(modeList.size()));

       BillsAdapter adapter = new BillsAdapter(monthList, categoryList, amountList, modeList);
//
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}