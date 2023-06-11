package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pebuplan.R;

public class SecurityQuestions extends AppCompatActivity {

    int count = 0;
    int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_questions);

        Button save = findViewById(R.id.security_save_btn);
        EditText question1 = findViewById(R.id.question1_et);
        EditText question2 = findViewById(R.id.question2_et);
        ImageView ques1Show = findViewById(R.id.ques1Show);
        ImageView ques2Show = findViewById(R.id.ques2Show);

        SharedPreferences preferences = getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        Intent intent = getIntent();
        String calledFrom = intent.getStringExtra("callFrom");
        ques1Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    question1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    count = 1;
                } else {
                    question1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count = 0;
                }
            }
        });

        ques2Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count2 == 0) {
                    question2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    count2 = 1;
                } else {
                    question2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count2 = 0;
                }
            }
        });
        if (calledFrom.equals("ForgetPin")) {
            save.setText("Verify");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: SAVE TO Shared Preference
                if (!calledFrom.equals("ForgetPin")) {
                    String ans1 = question1.getText().toString();
                    String ans2 = question2.getText().toString();
                    editor.putString("Question1", ans1);
                    editor.putString("Question2", ans2);
                    editor.apply();
                    Intent intent = new Intent(SecurityQuestions.this, PinConfirmActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String ans1 = question1.getText().toString();
                    String ans2 = question2.getText().toString();
                    String savedAns1 = preferences.getString("Question1", "notFound");
                    String savedAns2 = preferences.getString("Question2", "notFound");
                    if (!savedAns1.equals("notFound") && !savedAns2.equals("notFound") && ans1.equalsIgnoreCase(savedAns1) && ans2.equalsIgnoreCase(savedAns2)) {
                        Intent intent = new Intent(SecurityQuestions.this, PinActivity.class);
                        intent.putExtra("callFrom", "ForgetPin");
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SecurityQuestions.this,"Incorrect Answers",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}