package com.example.familychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText inputCode1, inputCode2,inputCode3,inputCode4,inputCode5;
    private String verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final FloatingActionButton btnVerify = findViewById(R.id.btnVerify);


        verificationId = getIntent().getStringExtra("verificationId");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1.getText().toString().trim().isEmpty()
                    || inputCode2.getText().toString().trim().isEmpty()
                    || inputCode3.getText().toString().trim().isEmpty()
                    || inputCode4.getText().toString().trim().isEmpty()
                    || inputCode4.getText().toString().trim().isEmpty()
                    || inputCode5.getText().toString().trim().isEmpty()
                ){
                    Toast.makeText(VerifyOTPActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code =
                        inputCode1.getText().toString() +
                                inputCode2.getText().toString() +
                                inputCode3.getText().toString() +
                                inputCode4.getText().toString() +
                                inputCode5.getText().toString();
            if (verificationId != null){
                progressBar.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.INVISIBLE);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                btnVerify.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()){
                                    Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else
                                    Toast.makeText(VerifyOTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        });

                findViewById(R.id.textMobile).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+7" +  getIntent().getStringExtra("mobile"),
                                60,
                                TimeUnit.SECONDS,
                                VerifyOTPActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        Toast.makeText(VerifyOTPActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        verificationId = newVerificationId;
                                        Toast.makeText(VerifyOTPActivity.this, "Code Sent",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                    }
                });
            }
            }
        });

        TextView textMobile = findViewById(R.id.textMobile);
         textMobile.setText(String.format(
                 "+7-%s", getIntent().getStringExtra("mobile")
         ));
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);

        setOTPInputs();
    }


    private void setOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}