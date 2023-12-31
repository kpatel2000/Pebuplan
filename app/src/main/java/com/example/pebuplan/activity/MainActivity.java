package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pebuplan.R;
import com.example.pebuplan.fragments.fgoal.FGoals;
import com.example.pebuplan.fragments.monthlybills.MainBillsFragment;
import com.example.pebuplan.fragments.monthlybudget.BudgetFragment;
import com.example.pebuplan.fragments.monthlybudget.IncomeFragment;
import com.example.pebuplan.fragments.savings.SavingFragment;
import com.example.pebuplan.fragments.settings.HelpFragment;
import com.example.pebuplan.fragments.settings.NotificationsFragment;
import com.example.pebuplan.fragments.settings.SecurityFragment;
import com.example.pebuplan.fragments.tipids.TipidTips;
import com.example.pebuplan.fragments.tracker.TrackerExpensesFragment;
import com.example.pebuplan.fragments.tracker.TrackerMainFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fragContainer = findViewById(R.id.fragment_container_main);
        String fragDetails = getIntent().getStringExtra("FragDetails");

        sharedPreferences = getSharedPreferences("plan", Context.MODE_PRIVATE);


        if(fragDetails.equals("m_budget")){
//            BudgetFragment fragment = new BudgetFragment();
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            String[] monthNames = new DateFormatSymbols().getMonths();
            if(sharedPreferences.getString(monthNames[currentMonth]+ "_income","").equals("")){
                IncomeFragment fragment = new IncomeFragment();
                transaction(fragment);
            }else {
                BudgetFragment fragment = new BudgetFragment();
                transaction(fragment);
            }

        } else if (fragDetails.equals("f_goals")) {
            FGoals fragment = new FGoals();
            transaction(fragment);
        } else if (fragDetails.equals("i_tracker")) {
//            TrackerMainFragment fragment = new TrackerMainFragment();
            TrackerExpensesFragment fragment = new TrackerExpensesFragment();
            transaction(fragment);
        }else if(fragDetails.equals("m_bills")){
            MainBillsFragment mainBillsFragment = new MainBillsFragment();
            transaction(mainBillsFragment);
        } else if(fragDetails.equals("nav_security")) {
            SecurityFragment fragment = new SecurityFragment();
            transaction(fragment);
        } else if (fragDetails.equals("nav_remainder")) {
            NotificationsFragment fragment = new NotificationsFragment();
            transaction(fragment);
        } else if (fragDetails.equals("nav_h_and_p")) {
            HelpFragment fragment = new HelpFragment();
            transaction(fragment);
        } else if (fragDetails.equals("tips")) {
            TipidTips fragment = new TipidTips();
            transaction(fragment);
        } else{
            SavingFragment savingFragment = new SavingFragment();
            transaction(savingFragment);
        }
    }

    public void transaction(Fragment fragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }
}