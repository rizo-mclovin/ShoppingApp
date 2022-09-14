package com.example.familychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.StatusBarManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ExtendedFloatingActionButton btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        final ProgressBar progressBar= findViewById(R.id.progressBar);
        final EditText inputMobile = findViewById(R.id.inputMobile);
        final EditText inputName = findViewById(R.id.enterName);
        final EditText inputPassword = findViewById(R.id.password);
        final EditText confirmPassword= findViewById(R.id.confirm_password);




        TextView toSignIn = findViewById(R.id.toSignIn);
        toSignIn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        });

        btnSignUp = findViewById(R.id.signUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobile.getText().toString().trim().isEmpty()){
                    inputMobile.setError("Enter Mobile");
                    dialog.show();
                    return;
                }else if (inputName.getText().toString().trim().isEmpty()){
                    inputName.setError("Enter Name");
                    dialog.show();
                    return;
                }else if (inputPassword.getText().toString().trim().isEmpty()){
                    inputPassword.setError("Enter Password");
                    dialog.show();
                    return;
                }else if (confirmPassword.getText().toString().trim().isEmpty()){
                    confirmPassword.setError("Confirm your password");
                    dialog.show();
                    return;
                }else if(!inputPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError("Password and confirm password must be same");
                    dialog.show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                progressBar.getIndeterminateDrawable().setColorFilter(142232, android.graphics.PorterDuff.Mode.MULTIPLY);
                btnSignUp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+7" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SignUpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnSignUp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnSignUp.setVisibility(View.VISIBLE);
                                Toast.makeText(SignUpActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnSignUp.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(SignUpActivity.this, VerifyOTPActivity.class);
                                intent.putExtra("mobile", inputMobile.getText().toString());
                                intent.putExtra("name", inputName.getText().toString());
                                intent.putExtra("password", inputPassword.getText().toString());
                                intent.putExtra("confirmPassword", confirmPassword.getText().toString());
                                intent.putExtra("verificationId", verificationId);

                                startActivity(intent);

                            }
                        }
                );

            }
        });
    }
}