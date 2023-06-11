package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;



public class PinConfirmActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_confirm);

        TextView hide_text = findViewById(R.id.textView34);

        TextView pin_Code_box = findViewById(R.id.pin_Code_box);
        TextView forgetPin = findViewById(R.id.forget_pin);
        Button sign_in = findViewById(R.id.button4);

        TextView signwithfinger = findViewById(R.id.signwithfinger);
        ImageView pinConfirmShow = findViewById(R.id.pinConfirmShow);

        signwithfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PinConfirmActivity.this, FingerPrintActivity.class);
                startActivity(intent);
            }
        });

        pinConfirmShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    pin_Code_box.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    count = 1;
                }else{
                    pin_Code_box.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count = 0;
                }
            }
        });

        forgetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PinConfirmActivity.this,SecurityQuestions.class);
                intent.putExtra("callFrom","ForgetPin");
                startActivity(intent);
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String value = sharedPref.getString("pincode", "default_value");

        String p_tick = sharedPref.getString("p_tick", "default_value");
        String finger_tick = sharedPref.getString("finger_tick", "default_value");


        if(p_tick.equals("false")){
            hide_text.setVisibility(View.INVISIBLE);
            pin_Code_box.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(PinConfirmActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        if(finger_tick.equals("false")){
            signwithfinger.setVisibility(View.INVISIBLE);
        }

/*        if(p_tick.equals("false") && finger_tick.equals("false") ){
            Intent intent = new Intent(PinConfirmActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/

        //Log.d("1",value);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin_Code_box.getText().toString().equals(value)){
                    Intent intent = new Intent(PinConfirmActivity.this, HomeActivity.class);
                    editor.putString("login", "yes");
                    editor.apply();
                    startActivity(intent);
                }else{
                    Log.d("2", pin_Code_box.getText().toString());
                    pin_Code_box.setError("Error: Text is invalid");
                }
            }
        });

    }
}