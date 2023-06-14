package com.example.pebuplan.fragments.tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;
import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.fragments.savings.DataModel;
import com.example.pebuplan.fragments.savings.SavingsAdapter;
import com.example.pebuplan.model.BudgetModel;

import java.util.ArrayList;
import java.util.List;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerAdapter.ViewHolder> {


    ArrayList<BudgetModel> TrackerList;
    public TrackerAdapter(ArrayList<BudgetModel> trackerList){
        if (TrackerList == null){
            TrackerList = new ArrayList<>();
        }
        TrackerList = trackerList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracker_item,parent,false);
        return new TrackerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(TrackerList.get(position).getCategory());
        if (!TrackerList.get(position).getDaily().equals("")){
            holder.budget.setText(TrackerList.get(position).getDaily());
        }
        if (TrackerList.get(position).getExpense() != null){
            holder.expense.setText(TrackerList.get(position).getExpense());
        }
    }
    public void updateRecyclerView(ArrayList<BudgetModel> list){
        TrackerList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return TrackerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView category;
        TextView budget;
        TextView expense;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_data_tracker);
            budget = itemView.findViewById(R.id.budget_data_tracker);
            expense = itemView.findViewById(R.id.expense_data_tracker);
        }
    }
}

