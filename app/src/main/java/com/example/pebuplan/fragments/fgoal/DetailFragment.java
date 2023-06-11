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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;


public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int PICK_IMAGE = 1;
    TextInputEditText goal_amount, target_date, monthly_contribution;

    String imagepath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;

    String name_plate;
    ImageView upload_image;

    public DetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Button submit_btn = view.findViewById(R.id.button3);

        goal_amount = view.findViewById(R.id.goal_amount_tbox);
        target_date = view.findViewById(R.id.target_date_tbox);
        monthly_contribution = view.findViewById(R.id.monthly_contribution_tbox);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        name_plate = sharedPref.getString("fgoals_task", "Home");

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
                Toast.makeText(requireContext(),"Data Saved! It might take few minutes to load the data",Toast.LENGTH_LONG).show();
                editor.putString(name_plate + "goal_amount",goal_amount.getText().toString());
                editor.putString(name_plate + "target_date",target_date.getText().toString());
                editor.putString(name_plate + "monthly_contribution",monthly_contribution.getText().toString());
                editor.putString("image_goals",imagepath);
                editor.apply();
                //Toast.makeText(requireContext(),"Da",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

/*    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri uri = data.getData();
            ImageView imageView = getView().findViewById(R.id.upload_image);
            imageView.setImageURI(uri);
            imagepath = uri.getPath();
        }
    }*/

    private void pickImage() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    private void saveImageToStorage(Bitmap bitmap) {
        String fileName = name_plate + "my_image.jpg";
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
                        saveImageToStorage(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        target_date.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
    }
}