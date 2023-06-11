package com.example.pebuplan.fragments.monthlybudget;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.pebuplan.R;
import com.example.pebuplan.model.BudgetModel;

public class BudgetCustomDialog extends Dialog {

    public Activity activity;
    EditText category;
    EditText budget;
    EditText spent;
    UpdateBudgetTable updateBudgetTable;

    Button add;

    public BudgetCustomDialog(Activity a, UpdateBudgetTable updateBudgetTable) {
        super(a);
        activity = a;
        this.updateBudgetTable = updateBudgetTable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_dialog_layout);

        category = findViewById(R.id.category_et);
        budget = findViewById(R.id.budget_et);
        spent = findViewById(R.id.spent_et);
        add = findViewById(R.id.add_budget);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBudgetTable.update(new BudgetModel(
                        category.getText().toString(),
                        budget.getText().toString(),
                        spent.getText().toString()
                ));
                dismiss();
            }
        });
    }
}

interface UpdateBudgetTable {
    public void update(BudgetModel budgetModel);
}