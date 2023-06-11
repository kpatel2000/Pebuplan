package com.example.pebuplan.fragments.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;

import java.util.Objects;

public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        TextView title = view.findViewById(R.id.title);
        title.setText("Notification");

        ImageView back_image = view.findViewById(R.id.back_image);

        CheckBox bill_reminder = view.findViewById(R.id.bill_reminder);

        CheckBox budget_reminder = view.findViewById(R.id.checkBox2);

        Button save = view.findViewById(R.id.button6);

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String bill_tick = sharedPref.getString("bill_reminder", "default_value");
        String bill_noti = sharedPref.getString("bill_noti", "default_value");

        if(bill_tick.equals("true")){
            bill_reminder.setChecked(true);
        }

        if (bill_noti.equals("true")){
            budget_reminder.setChecked(true);
        }

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill_reminder.isChecked() && budget_reminder.isChecked()){
                    editor.putString("bill_reminder","true");
                    editor.putString("bill_noti","true");
                    editor.apply();
                    Toast.makeText(requireContext(), "Data Saved !", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
}