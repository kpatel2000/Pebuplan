package com.example.pebuplan.activity;

import com.example.pebuplan.R;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Signature;

public class FingerPrintActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_USE_FINGERPRINT = 100;

    private FingerprintManager fingerprintManager;
    private TextView textView,pin_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        textView = findViewById(R.id.textView);

        pin_text = findViewById(R.id.pin_text);

        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

        // Check if the device has a fingerprint sensor
        if (!fingerprintManager.isHardwareDetected()) {
            textView.setText("Your device doesn't have a fingerprint sensor");
        }
        // Check if the app has the necessary permissions to use the fingerprint sensor
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.USE_FINGERPRINT},
                    MY_PERMISSIONS_REQUEST_USE_FINGERPRINT);
        }
        // Check if the user has registered at least one fingerprint
        else if (!fingerprintManager.hasEnrolledFingerprints()) {
            textView.setText("You haven't registered any fingerprints yet");
        }
        // Everything is ready for fingerprint authentication
        else {
            authenticate();
        }

        pin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FingerPrintActivity.this, PinConfirmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void authenticate() {
        // Create a CryptoObject
        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject((Signature) null);

        // Create a callback object for the authentication events
        FingerprintManager.AuthenticationCallback authenticationCallback =
                new FingerprintManager.AuthenticationCallback() {

                    // Authentication successful
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        Toast.makeText(FingerPrintActivity.this, "Authentication succeeded", Toast.LENGTH_SHORT).show();
                    }

                    // Authentication failed
                    @Override
                    public void onAuthenticationFailed() {
                        Toast.makeText(FingerPrintActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                };

        // Start authentication
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager.authenticate(cryptoObject, null, 0, authenticationCallback, null);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_USE_FINGERPRINT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                authenticate();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Without the permission to use the fingerprint sensor, you can't use this app.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
}
