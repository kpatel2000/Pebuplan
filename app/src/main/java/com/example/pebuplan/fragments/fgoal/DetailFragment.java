package com.example.pebuplan.fragments.fgoal;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int PICK_IMAGE = 1;
    EditText goal_amount, target_date, monthly_savings;

    String imagepath;
    int target_month;
    int calculatedMonthlySavings;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;

    String name_plate;
    ImageView upload_image;
    String value;

    public DetailFragment(String value) {
        this.value = value;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Button submit_btn = view.findViewById(R.id.save_details);

        goal_amount = view.findViewById(R.id.goal_amount);
        target_date = view.findViewById(R.id.target_date);
        monthly_savings = view.findViewById(R.id.monthly_savings);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String goalAmount = sharedPref.getString(value+"_details_goalAmount","0");
        String months = sharedPref.getString(value+"_details_target_date","0");
        String savings = sharedPref.getString(value+"_details_monthly_contribution","0");
        String image = sharedPref.getString(value+"_details_image",null);
        if (!goalAmount.equals("0") && !months.equals("0") && !savings.equals("0") && image!=null){
            goal_amount.setText(goalAmount);
            target_date.setText(months);
            monthly_savings.setText(savings);
            upload_image.setImageBitmap(decodeBase64(image));
        }else if(!goalAmount.equals("0") && !months.equals("0") && !savings.equals("0")){
            goal_amount.setText(goalAmount);
            target_date.setText(months);
            monthly_savings.setText(savings);
        }

        upload_image = view.findViewById(R.id.upload_image);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_IMAGE);
                pickImage();
            }
        });

        target_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), DetailFragment.this, year, month, day);
                    datePickerDialog.show();

                    target_date.clearFocus();
                } else {
                    target_date.clearFocus();
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(),"Data Saved! Your monthly savings is calculated",Toast.LENGTH_SHORT).show();

                String goal = goal_amount.getText().toString();
                Calendar cal = Calendar.getInstance();
                int curr_month = cal.get(Calendar.MONTH);
                int savings;
                if(curr_month > target_month){
                    savings = Integer.parseInt(goal)/(curr_month - target_month);
                }else{
                    savings = Integer.parseInt(goal)/(target_month - curr_month);
                }

                monthly_savings.setText(String.valueOf(savings));

                editor.putString(value+"_details_goalAmount",goal_amount.getText().toString());
                editor.putString(value+"_details_target_date",target_date.getText() != null? target_date.getText().toString():"");
                editor.putString(value+"_details_monthly_contribution",String.valueOf(savings));
                editor.putString(value+"_details_image",imagepath);
                editor.apply();
            }
        });

        return view;
    }

    private void pickImage() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    private void saveImageToStorage(Bitmap bitmap) {
        String fileName =  value+".jpg";
        FileOutputStream outputStream;

        try {
            outputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        upload_image.setImageBitmap(imageBitmap);
                        imagepath = encodeTobase64(imageBitmap);
                        saveImageToStorage(imageBitmap);
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedImageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    if (imageBitmap != null) {
                        upload_image.setImageBitmap(imageBitmap);
                        imagepath = encodeTobase64(imageBitmap);
                        saveImageToStorage(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        target_date.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
        target_month = month;
    }
}