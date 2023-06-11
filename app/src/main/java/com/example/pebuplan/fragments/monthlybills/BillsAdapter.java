package com.example.pebuplan.fragments.monthlybills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;

import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {


    private List<String> monthList;
    private List<String> categoryList;
    private List<String> amountList;
    private List<String> modeList;

    public BillsAdapter(List<String> monthList, List<String> categoryList, List<String> amountList, List<String> modeList) {
        this.monthList = monthList;
        this.categoryList = categoryList;
        this.amountList = amountList;
        this.modeList = modeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.monthTextView.setText(monthList.get(position));
        holder.categoryTextView.setText(categoryList.get(position));
        holder.amountTextView.setText(amountList.get(position));
       holder.modeTextView.setText(modeList.get(position));
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView monthTextView;
        public TextView categoryTextView;
        public TextView amountTextView;
        public TextView modeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            monthTextView = itemView.findViewById(R.id.month);
            categoryTextView = itemView.findViewById(R.id.cate);
            amountTextView = itemView.findViewById(R.id.amount);
            modeTextView = itemView.findViewById(R.id.mode);
        }
    }
}
