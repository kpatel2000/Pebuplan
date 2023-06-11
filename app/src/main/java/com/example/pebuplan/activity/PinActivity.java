package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.R;


public class PinActivity extends AppCompatActivity {

    int count = 0;
    int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);


        Button save = findViewById(R.id.button2);
        EditText pincode = findViewById(R.id.pin_Code_box);
        EditText pinconfirm = findViewById(R.id.pin_Code_confirm);
        ImageView pinShow = findViewById(R.id.pin_show);
        ImageView pinConfirmShow = findViewById(R.id.pin_confirm_show);

        SharedPreferences preferences = getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Intent intent = getIntent();
        String calledFrom = intent.getStringExtra("callFrom");

        pinShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    pincode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    count = 1;
                }else{
                    pincode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count = 0;
                }
            }
        });

        pinConfirmShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count2 == 0){
                    pinconfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    count2 = 1;
                }else{
                    pinconfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count2 = 0;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pincode.getText().toString().equals(pinconfirm.getText().toString()) && !pincode.getText().toString().isEmpty()){
                    if (!calledFrom.equals("ForgetPin")) {
                        Intent intent = new Intent(PinActivity.this, SecurityQuestions.class);
                        intent.putExtra("callFrom", "PinActivity");
                        editor.putString("pincode", pincode.getText().toString());
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(PinActivity.this, PinConfirmActivity.class);
                        editor.putString("pincode", pincode.getText().toString());
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(PinActivity.this, "Pin code doesn't match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}