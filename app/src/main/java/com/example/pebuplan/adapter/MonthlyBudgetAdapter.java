package com.example.pebuplan.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;
import com.example.pebuplan.model.BudgetModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyBudgetAdapter extends RecyclerView.Adapter<MonthlyBudgetAdapter.ViewHolder> {

    ArrayList<BudgetModel> BudgetList;
    String calledFrom;
    public MonthlyBudgetAdapter(ArrayList<BudgetModel> budgetList, String calledFrom){
        if (BudgetList == null){
            BudgetList = new ArrayList<>();
        }
        BudgetList = budgetList;
        this.calledFrom = calledFrom;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (calledFrom.equals("daily")){
            holder.category.setText(BudgetList.get(position).getCategory());
            holder.budget.setText(BudgetList.get(position).getDaily());
            holder.spent.setText(BudgetList.get(position).getWeekly());
            holder.remain.setText(BudgetList.get(position).getMonthly());
            holder.budget.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String txt_budget = holder.budget.getText().toString();
                    BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                    if (!txt_budget.equals("")){
                        bm.setDaily(txt_budget);
                        Calendar calendar = Calendar.getInstance();
                        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int daily = Integer.parseInt(bm.getDaily());
                        bm.setWeekly(String.valueOf(daily*7));
                        bm.setMonthly(String.valueOf(daily*lastDayOfMonth));
                        holder.spent.setText(bm.getWeekly());
                        holder.remain.setText(bm.getMonthly());
                    }else{
                        bm.setDaily(txt_budget);
                        bm.setWeekly("");
                        bm.setMonthly("");
                        holder.spent.setText(bm.getWeekly());
                        holder.remain.setText(bm.getMonthly());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else if (calledFrom.equals("weekly")){
            holder.category.setText(BudgetList.get(position).getCategory());
            holder.budget.setText(BudgetList.get(position).getWeekly());
            holder.spent.setText(BudgetList.get(position).getDaily());
            holder.remain.setText(BudgetList.get(position).getMonthly());
            holder.budget.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String txt_budget = holder.budget.getText().toString();
                    BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                    if (!txt_budget.equals("")) {
                        bm.setWeekly(txt_budget);
                        Calendar calendar = Calendar.getInstance();
                        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int weekly = Integer.parseInt(bm.getWeekly());
                        bm.setDaily(String.valueOf(weekly / 7));
                        bm.setMonthly(String.valueOf(Integer.parseInt(bm.getDaily()) * lastDayOfMonth));
                        holder.spent.setText(bm.getDaily());
                        holder.remain.setText(bm.getMonthly());
                    }else{
                        bm.setWeekly(txt_budget);
                        bm.setDaily("");
                        bm.setMonthly("");
                        holder.spent.setText(bm.getWeekly());
                        holder.remain.setText(bm.getMonthly());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else if (calledFrom.equals("monthly")){
            holder.category.setText(BudgetList.get(position).getCategory());
            holder.budget.setText(BudgetList.get(position).getMonthly());
            holder.spent.setText(BudgetList.get(position).getDaily());
            holder.remain.setText(BudgetList.get(position).getWeekly());
            holder.budget.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String txt_budget = holder.budget.getText().toString();
                    BudgetModel bm = BudgetList.get(holder.getAdapterPosition());
                    if (!txt_budget.equals("")) {
                        bm.setMonthly(txt_budget);
                        Calendar calendar = Calendar.getInstance();
                        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int monthly = Integer.parseInt(bm.getMonthly());
                        bm.setDaily(String.valueOf(monthly / lastDayOfMonth));
                        bm.setWeekly(String.valueOf(Integer.parseInt(bm.getDaily()) * 7));
                        holder.spent.setText(bm.getDaily());
                        holder.remain.setText(bm.getMonthly());
                    }else{
                        bm.setMonthly(txt_budget);
                        bm.setDaily("");
                        bm.setWeekly("");
                        holder.spent.setText(bm.getDaily());
                        holder.remain.setText(bm.getWeekly());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
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
        EditText budget;
        TextView spent, remain, category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category_data);
            budget = itemView.findViewById(R.id.budget_data);
            spent = itemView.findViewById(R.id.spents_data);
            remain = itemView.findViewById(R.id.remains_data);
        }
    }
}