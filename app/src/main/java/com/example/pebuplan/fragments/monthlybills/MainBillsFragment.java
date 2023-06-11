package com.example.pebuplan.fragments.monthlybills;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.example.pebuplan.adapter.MonthlyBillAdapter;
import com.example.pebuplan.adapter.UpdateList;
import com.example.pebuplan.model.BudgetModel;
import com.example.pebuplan.model.MonthlyBillModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class MainBillsFragment extends Fragment implements UpdateBill {


    ImageView back_image;
    ImageView addMonthlyBills;
    RecyclerView monthlyBillRecyclerView;

    ArrayList<MonthlyBillModel> monthlyBills;
    MonthlyBillAdapter adapter;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    Button saveBill;

    public MainBillsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_bills, container, false);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        Gson gson = new Gson();
        String json = preferences.getString("monthlyBills", null);
        Type type = new TypeToken<ArrayList<MonthlyBillModel>>() {
        }.getType();
        monthlyBills = gson.fromJson(json, type);
        if (monthlyBills == null) {
            monthlyBills = new ArrayList<>();
        }
        back_image = view.findViewById(R.id.back_image);
        monthlyBillRecyclerView = view.findViewById(R.id.monthly_bill_recycler_view);
        addMonthlyBills = view.findViewById(R.id.add_monthly_bill);

        TextView title = view.findViewById(R.id.title);
        title.setText("Monthly Bills");

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        monthlyBillRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MonthlyBillAdapter(monthlyBills);
        monthlyBillRecyclerView.setAdapter(adapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MonthlyBillModel deletedBill = monthlyBills.get(viewHolder.getAdapterPosition());
                deleteBill(deletedBill);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(monthlyBillRecyclerView);

//        Dialog dialog = new Dialog(requireContext());
        addMonthlyBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cd = new CustomDialog(requireActivity(), MainBillsFragment.this);
                cd.show();
            }

        });
//        dialog.show();
        saveBill = view.findViewById(R.id.save_bill);
        saveBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String billsData = gson.toJson(monthlyBills);
                editor.putString("monthlyBills", billsData);
                editor.commit();

            }
        });
        return view;
    }

    @Override
    public void update(MonthlyBillModel monthlyBillModel) {
        monthlyBills.add(monthlyBillModel);
        adapter.update(monthlyBills);
        Gson gson = new Gson();
        String json = gson.toJson(monthlyBills);
        editor.putString("monthlyBills", json);
        editor.apply();
    }

    public void deleteBill(MonthlyBillModel monthlyBillModel) {
        monthlyBills.remove(monthlyBillModel);
        Gson gson = new Gson();
        String json = gson.toJson(monthlyBills);
        editor.putString("monthlyBills", json);
        editor.apply();
    }

//    @Override
//    public void updateList(MonthlyBillModel monthlyBillModel, int position) {
//        monthlyBills.add(position, monthlyBillModel);
//    }
}