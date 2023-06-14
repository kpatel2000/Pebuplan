package com.example.pebuplan.fragments.fgoal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pebuplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RecordFragment extends Fragment implements SaveRecord {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Record> recordList;

    String value;
    public RecordFragment(String value) {
        this.value = value;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        ImageView floatingActionButton = view.findViewById(R.id.fab_record);
        recyclerView = view.findViewById(R.id.record_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Record> records = getRecords();

        if (recordList == null){
            recordList = new ArrayList<>();
        }
        for (Record record : records) {
            String recordText = record.getRecord();
            String dateText = record.getDate();

            recordList.add(new Record(recordText,dateText));
        }

        if (recordList == null){
            recordList = new ArrayList<>();
        }
        adapter = new RecyclerViewAdapter(recordList);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.showCustomDialog(requireContext(),RecordFragment.this);
            }
        });
        return view;
    }

    private List<Record> getRecords() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("plan", Context.MODE_PRIVATE);
        String recordsJson = sharedPreferences.getString(value+"_records", "");
        if (!TextUtils.isEmpty(recordsJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Record>>() {}.getType();
            return gson.fromJson(recordsJson, type);
        }
        return new ArrayList<>();
    }

    @Override
    public void saveRecordData(Record record) {
        ArrayList<Record> records = new ArrayList<>();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String recordsJson = sharedPreferences.getString(value+"_records", "");
        if (!TextUtils.isEmpty(recordsJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Record>>() {}.getType();
            records = gson.fromJson(recordsJson, type);
        }
        records.add(record);
        Gson gson = new Gson();
        String recordString = gson.toJson(records);
        editor.putString(value+"_records",recordString);
        editor.apply();
        adapter.update(records);
    }
}