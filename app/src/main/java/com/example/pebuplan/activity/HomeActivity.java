package com.example.pebuplan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.pebuplan.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private VideoView videoView;
    private SeekBar seekBar;

    //public DrawerLayout drawer;

    Intent intent;
    PieChart pieChart;
    private CardView m_budget, f_goals, m_bills, i_tracker, d_savings;

    TextView income_txt, expenses_txt, savings_txt;

    //private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        SharedPreferences prefs = getSharedPreferences("plan", Context.MODE_PRIVATE);
        String noti_data = prefs.getString("bill_reminder", "false");

        if(noti_data.equals("true")) {
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

            OneSignal.initWithContext(this);
            OneSignal.setAppId("c803c1d8-6b5e-4f37-a6a5-604399e347ab");
            OneSignal.promptForPushNotifications();
        }else{
            Log.d("Hi", "Bhai data nahi hai");
        }


        m_budget = findViewById(R.id.m_budget);
        f_goals = findViewById(R.id.f_goals);
        m_bills = findViewById(R.id.m_bills);
        i_tracker = findViewById(R.id.i_tracker);
        d_savings = findViewById(R.id.d_savings);

        income_txt = findViewById(R.id.income);
        expenses_txt = findViewById(R.id.expenses);
        savings_txt = findViewById(R.id.savings);

        videoView = findViewById(R.id.video_view);
        seekBar = findViewById(R.id.seek_bar);
        pieChart = findViewById(R.id.piechart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        videoView.setVideoPath(videoPath);

        intent = new Intent(HomeActivity.this, MainActivity.class);
       // Log.d("To check",String.valueOf(drawer.isDrawerOpen(GravityCompat.START)));
         Log.d("hi","1");
        //drawerLayout.close();

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        String[] monthNames = new DateFormatSymbols().getMonths();


        String income = prefs.getString(monthNames[month] + "_Total_Budget","0").replace("₱", "");
        String expenses = prefs.getString(monthNames[month] + "_Total_Spent","0").replace("₱", "");
        String savings = prefs.getString(monthNames[month] + "_Total_Remains","0").replace("₱", "");

        final Handler handler = new Handler();
        final Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    seekBar.setProgress(videoView.getCurrentPosition());
                    handler.postDelayed(this, 100);
                }
            }
        };


        videoView.start();
        handler.postDelayed(updateSeekBar, 100);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // pause video when user starts touching seek bar
                videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                videoView.start();
            }
        });


        pieChart.addPieSlice(
                new PieModel(
                        "Budget",
                        Integer.parseInt(income),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Spent",
                        Integer.parseInt(expenses),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Savings",
                        Integer.parseInt(savings),
                        Color.parseColor("#22A1E2")));


        income_txt.setText("INCOME : " + income);
        expenses_txt.setText("EXPENSES : " + expenses);
        savings_txt.setText("SAVINGS : " + savings);


        m_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "m_budget");
                startActivity(intent);
            }
        });

        f_goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "f_goals");
                startActivity(intent);
            }
        });

        m_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "m_bills");
                startActivity(intent);
            }
        });

        i_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "i_tracker");
                startActivity(intent);
            }
        });

        d_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "d_savings");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("1", String.valueOf(menu));
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_budget) {
            intent.putExtra("FragDetails", "m_budget");
            startActivity(intent);
        } else if (id == R.id.action_goals) {
            intent.putExtra("FragDetails", "f_goals");
            startActivity(intent);
        }else if(id == R.id.action_bills){
            intent.putExtra("FragDetails", "m_bills");
            startActivity(intent);
        } else if (id == R.id.action_tracker) {
            intent.putExtra("FragDetails", "i_tracker");
            startActivity(intent);
        } else if (id == R.id.action_savings) {
            intent.putExtra("FragDetails", "d_savings");
            startActivity(intent);
        } else if (id == R.id.nav_security) {
            intent.putExtra("FragDetails", "nav_security");
            startActivity(intent);
        } else if (id == R.id.nav_h_and_p) {
            intent.putExtra("FragDetails", "nav_h_and_p");
            startActivity(intent);
        }else{
            intent.putExtra("FragDetails", "tips");
            startActivity(intent);
        }

        Log.d("1", String.valueOf(id));
        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}