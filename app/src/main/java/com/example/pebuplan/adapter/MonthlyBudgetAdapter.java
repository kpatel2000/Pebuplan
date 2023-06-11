package com.example.pebuplan.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;
import com.example.pebuplan.model.BudgetModel;

import java.util.ArrayList;
import java.util.List;

public class MonthlyBudgetAdapter extends RecyclerView.Adapter<MonthlyBudgetAdapter.ViewHolder> {

    ArrayList<BudgetModel> BudgetList;
    public MonthlyBudgetAdapter(ArrayList<BudgetModel> budgetList){
        if (BudgetList == null){
            BudgetList = new ArrayList<>();
        }
        BudgetList = budgetList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(BudgetList.get(position).getCategory());
        holder.budget.setText(BudgetList.get(position).getBudget());
        holder.spent.setText(BudgetList.get(position).getSpent());
        holder.remain.setText(BudgetList.get(position).getRemain());
        holder.budget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_budget = holder.budget.getText().toString();
                BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                bm.setBudget(txt_budget);
                setNewRemain(holder);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_category = holder.category.getText().toString();
                BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                bm.setCategory(txt_category);
                setNewRemain(holder);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.spent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_spent = holder.spent.getText().toString();
                BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                bm.setSpent(txt_spent);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        int remains = Integer.parseInt(holder.budget.getText().toString()) - Integer.parseInt(holder.spent.getText().toString());
        holder.remain.setText(String.valueOf(remains));
    }

    void setNewRemain(ViewHolder holder){
        int remains = Integer.parseInt(holder.budget.getText().toString()) - Integer.parseInt(holder.spent.getText().toString());
        holder.remain.setText(String.valueOf(remains));
    }
    @Override
    public int getItemCount() {
        return BudgetList.size();
    }

    public void update(ArrayList<BudgetModel> budgetModelList){
        this.BudgetList = budgetModelList;
        notifyDataSetChanged();
    }

    public void updateRecyclerView(ArrayList<BudgetModel> list){
        BudgetList = list;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText category, budget, spent, remain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category_data);
            budget = itemView.findViewById(R.id.budget_data);
            spent = itemView.findViewById(R.id.spents_data);
            remain = itemView.findViewById(R.id.remains_data);
        }
    }
}