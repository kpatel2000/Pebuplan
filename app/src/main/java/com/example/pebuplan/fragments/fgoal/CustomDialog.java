package com.example.pebuplan.fragments.fgoal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.pebuplan.R;
import com.example.pebuplan.fragments.monthlybills.TransferFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomDialog {

    public static final String SHARED_PREF_NAME = "CustomDialogPrefs";
    public static final String KEY_RECORDS = "records";
    private static EditText dateEditText;

    public static void showCustomDialog(final Context context) {
        // Create a Dialog instance
        final Dialog dialog = new Dialog(context);

        // Set the custom layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
        dialog.setContentView(dialogView);

        // Get the EditText fields and Save button from the dialog layout
        final EditText recordEditText = dialog.findViewById(R.id.recordEditText);
        dateEditText = dialog.findViewById(R.id.dateEditText);
        Button saveButton = dialog.findViewById(R.id.saveButton);

        // Set click listener for the Save button
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month_date = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, CustomDialog::onDateSet, year, month_date, day);
                datePickerDialog.show();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String record = recordEditText.getText().toString();
                String date = dateEditText.getText().toString();

                // Create a new Record object
                Record newRecord = new Record(record, date);

                // Get the existing list of records from SharedPreferences
                List<Record> records = getRecords(context);

                // Add the new record to the list
                records.add(newRecord);

                // Save the updated list of records to SharedPreferences
                saveRecords(context, records);

                // Close the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    public static List<Record> getRecords(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String recordsJson = sharedPreferences.getString(KEY_RECORDS, "");
        if (!TextUtils.isEmpty(recordsJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Record>>() {}.getType();
            return gson.fromJson(recordsJson, type);
        }
        return new ArrayList<>();
    }

    public static void saveRecords(Context context, List<Record> records) {
        Gson gson = new Gson();
        String recordsJson = gson.toJson(records);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RECORDS, recordsJson);
        editor.apply();
    }

    public static void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateEditText.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
    }
}
