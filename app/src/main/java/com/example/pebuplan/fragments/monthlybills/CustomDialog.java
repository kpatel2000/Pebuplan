package com.example.pebuplan.fragments.monthlybills;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.pebuplan.R;
import com.example.pebuplan.model.MonthlyBillModel;

public class CustomDialog extends Dialog {

    public Activity activity;
    EditText category;
    EditText amount;
    EditText pay;
    UpdateBill updateBill;

    Button add;

    public CustomDialog(Activity a, UpdateBill updateBill){
        super(a);
        activity = a;
        this.updateBill = updateBill;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_bills_dialog_layout);

        category = findViewById(R.id.category_et);
        amount = findViewById(R.id.amount_et);
        pay = findViewById(R.id.pay_et);
        add = findViewById(R.id.add_bill);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBill.update(new MonthlyBillModel(
                        category.getText().toString(),
                        amount.getText().toString(),
                        pay.getText().toString()
                ));
                dismiss();
            }
        });
    }
}

interface UpdateBill{
    public void update(MonthlyBillModel monthlyBillModel);
}
