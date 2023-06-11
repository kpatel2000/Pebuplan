package com.example.pebuplan.fragments.fgoal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Record> recordList;

    public RecyclerViewAdapter(List<Record> recordList) {
        this.recordList = recordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.recordTextView.setText(record.getRecord());
        holder.dateTextView.setText(record.getDate());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recordTextView;
        TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            recordTextView = itemView.findViewById(R.id.record);
            dateTextView = itemView.findViewById(R.id.date);
        }
    }
}