package com.example.pebuplan.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pebuplan.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        SharedPreferences sharedPref = this.getSharedPreferences("plan", Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("login", "yes");


        SignInButton signInButton = findViewById(R.id.google_signin_button);

        LoginButton loginButton = findViewById(R.id.facebook_login_button);
        loginButton.setPermissions("email", "public_profile");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        EditText etName = findViewById(R.id.name_box);
        EditText etEmail = findViewById(R.id.email_box);
        EditText etPassword = findViewById(R.id.password_box);
        EditText etConfirmPassword = findViewById(R.id.password_box2);
        Button btnSignup = findViewById(R.id.sign_up);
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmpassword = etPassword.getText().toString().trim();
                Matcher matcher = pattern.matcher(password);

                if (TextUtils.isEmpty(name)) {
                    etName.setError("Name is required");
                    return;
                }

                else if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }

                else if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }

                else if(!password.equals(confirmpassword)){
                    etConfirmPassword.setError("Confirm password is incorrect");
                    return;
                }

                else if(!matcher.matches()){
                    etPassword.setError("This field should contain 8 char, contains at least one uppercase letter special symbol, number");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();


                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        user.sendEmailVerification()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(SignUpActivity.this, "A link has been sent to your email Id for verification", Toast.LENGTH_LONG).show();
                                                                            if (user.isEmailVerified()) {
                                                                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                                                                editor.apply();
                                                                                startActivity(intent);
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Check your Internet connection or try again later", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Check your Internet connection or try again later", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }

                                                                });
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });

        CallbackManager callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                editor.apply();
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // Handle cancel event
            }

            @Override
            public void onError(FacebookException error) {
                // Handle error event
            }
        });




    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        Uri photoUrl = user.getPhotoUrl();


                    } else {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //editor.apply();
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

}