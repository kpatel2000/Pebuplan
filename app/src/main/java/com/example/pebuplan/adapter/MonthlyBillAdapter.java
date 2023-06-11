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
import com.example.pebuplan.model.MonthlyBillModel;

import java.util.ArrayList;
import java.util.List;

public class MonthlyBillAdapter extends RecyclerView.Adapter<MonthlyBillAdapter.ViewHolder> {

    ArrayList<MonthlyBillModel> monthlyBillList;
    UpdateList updateList;
    public MonthlyBillAdapter(ArrayList<MonthlyBillModel> monthlyBill){
        if (monthlyBill.isEmpty()){
            monthlyBillList = new ArrayList<>();
        }else{
            monthlyBillList = monthlyBill;
        }
//        this.updateList = updateList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_bills,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(monthlyBillList.get(position).getCategory());
        holder.amount.setText(monthlyBillList.get(position).getAmount());
        holder.pay.setText(monthlyBillList.get(position).getPay());
        holder.debt.setText(monthlyBillList.get(position).getDebt());
        holder.status.setText(monthlyBillList.get(position).getStatus());

        holder.amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_amount = holder.amount.getText().toString();
                MonthlyBillModel bill = monthlyBillList.get(holder.getAdapterPosition());
                bill.setAmount(txt_amount);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.pay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_pay = holder.pay.getText().toString();
                MonthlyBillModel bill = monthlyBillList.get(holder.getAdapterPosition());
                bill.setPay(txt_pay);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        int debt = Integer.parseInt(holder.amount.getText().toString()) - Integer.parseInt(holder.pay.getText().toString());
        holder.debt.setText(String.valueOf(debt));

        String status = (debt == 0) ? "Paid" : "Unpaid";
        holder.status.setText(status);
    }

    @Override
    public int getItemCount() {
        return monthlyBillList.size();
    }

    public void update(ArrayList<MonthlyBillModel> billsList){
        this.monthlyBillList = billsList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText  amount, pay;
        TextView category, debt, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category_bill);
            amount = itemView.findViewById(R.id.amount);
            debt = itemView.findViewById(R.id.dept);
            pay = itemView.findViewById(R.id.pay);
            status = itemView.findViewById(R.id.status);
        }
    }
}

