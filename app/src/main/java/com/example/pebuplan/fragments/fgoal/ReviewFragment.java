package com.example.pebuplan.fragments.fgoal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


public class ReviewFragment extends Fragment {



    ImageView review_image;
    TextInputEditText title, goal_amount,target_date,monthly_tbox, duration_tbox;
    public ReviewFragment() {
        // Required empty public constructor
    }

    String name_plate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        title = view.findViewById(R.id.title_tbox);
        goal_amount = view.findViewById(R.id.goal_amaount_tbox);
        target_date = view.findViewById(R.id.target_date_tbox);
        monthly_tbox = view.findViewById(R.id.monthly_tbox);
        duration_tbox = view.findViewById(R.id.duration_tbox);
        review_image = view.findViewById(R.id.review_image);

        name_plate = sharedPref.getString("fgoals_task", "Home");

//        Bitmap storedImage = ((MainActivity) getActivity()).loadImageFromStorage();
        String title_text = sharedPref.getString("fgoals_task", "Home");
        String goal_text = sharedPref.getString(name_plate +  "fgoals_price", "");
        String date_text = sharedPref.getString(name_plate + "fgoals_date", "");
        String monthly_contribution = sharedPref.getString(name_plate + "monthly_contribution", "");
        String duration = sharedPref.getString(name_plate + "target_date", "");
//        String image = sharedPref.getString("image_goals", "R.drawable.upload_image");


        title.setText(title_text);
        goal_amount.setText(goal_text);
        target_date.setText(date_text);
        monthly_tbox.setText(monthly_contribution);
        duration_tbox.setText(duration);
        review_image.setImageBitmap(loadImageFromStorage());

        return view;
    }

    private Bitmap loadImageFromStorage() {
        String fileName = name_plate + "my_image.jpg";
        Bitmap imageBitmap = null;

        try {
            File file = new File(requireContext().getFilesDir(), fileName);
            InputStream inputStream = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                inputStream = Files.newInputStream(file.toPath());
            }
            imageBitmap = BitmapFactory.decodeStream(inputStream);
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageBitmap;
    }
}