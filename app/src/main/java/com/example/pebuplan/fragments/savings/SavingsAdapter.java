package com.example.pebuplan.fragments.savings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebuplan.R;

import java.util.List;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.ViewHolder> {

    private List<DataModel> dataList;

    public SavingsAdapter(List<DataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saving_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel data = dataList.get(position);

        holder.monthTextView.setText(data.getMonth());
        holder.numOfDaysTextView.setText(data.getNumOfDays());
        holder.savingsTextView.setText(data.getSavings());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView monthTextView;
        TextView numOfDaysTextView;
        TextView savingsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            monthTextView = itemView.findViewById(R.id.textView23);
            numOfDaysTextView = itemView.findViewById(R.id.textView30);
            savingsTextView = itemView.findViewById(R.id.textView31);
        }
    }
}
