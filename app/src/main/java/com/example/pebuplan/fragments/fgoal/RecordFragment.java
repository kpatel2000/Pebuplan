package com.example.pebuplan.fragments.fgoal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pebuplan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class RecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Record> recordList;

    public RecordFragment() {
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

        ImageView floatingActionButton = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Record> records = CustomDialog.getRecords(requireContext());
        recordList = new ArrayList<>();

        for (Record record : records) {
            String recordText = record.getRecord();
            String dateText = record.getDate();

            recordList.add(new Record(recordText,dateText));
        }


/*        recordList.add(new Record("Record 1", "2023-05-01"));
        recordList.add(new Record("Record 2", "2023-05-02"));
        recordList.add(new Record("Record 3", "2023-05-03"));*/

        adapter = new RecyclerViewAdapter(recordList);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.showCustomDialog(requireContext());
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();
        List<Record> records = CustomDialog.getRecords(requireContext());
        recordList = new ArrayList<>();
        adapter.notifyDataSetChanged();
        for (Record record : records) {
            String recordText = record.getRecord();
            String dateText = record.getDate();
            recordList.add(new Record(recordText,dateText));
        }
    }
}