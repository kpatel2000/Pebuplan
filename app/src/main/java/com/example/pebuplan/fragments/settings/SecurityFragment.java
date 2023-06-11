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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;


public class SecurityFragment extends Fragment {



    public SecurityFragment() {

    }

    public static SecurityFragment newInstance(String param1, String param2) {
        SecurityFragment fragment = new SecurityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        String pinCode = sharedPref.getString("p_tick", "true");
        String fingerPrint = sharedPref.getString("finger_tick","true");



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_security, container, false);


        Switch pin = view.findViewById(R.id.switch1);
        Switch finger = view.findViewById(R.id.switch2);

        if(pinCode.equals("false")){
            pin.setChecked(false);
        }else{
            pin.setChecked(true);
        }

        if(fingerPrint.equals("false")){
            finger.setChecked(false);
        }else{
            finger.setChecked(true);
        }


        Button save = view.findViewById(R.id.button5);
        TextView title = view.findViewById(R.id.title);
        title.setText("Security");

        ImageView back_image = view.findViewById(R.id.back_image);

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
                if(!pin.isChecked()){
                    editor.putString("p_tick","false");
                }else{
                    editor.putString("p_tick","true");
                }

                if (!finger.isChecked()){
                    editor.putString("finger_tick","false");
                }else {
                    editor.putString("finger_tick","true");
                }

                editor.apply();

                Toast.makeText(getContext(),"Data Saved!",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}