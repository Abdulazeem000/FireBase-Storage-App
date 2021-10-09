package com.example.trialwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneSignActivity extends AppCompatActivity {


    EditText phoneNumber;
    Button sendCode;
    EditText smsCode;
    Button signWithPhone;
    String codeSent;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign);

        phoneNumber = findViewById(R.id.editTextPhoneNumber);
        sendCode = findViewById(R.id.buttonSendSms);
        smsCode = findViewById(R.id.editTextCode);
        signWithPhone = findViewById(R.id.buttonSignInPhone);

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userPhoneNumber = phoneNumber.getText().toString();
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(userPhoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(phoneSignActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallBacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);



            }
        });

        signWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signWithPhoneCode();



            }
        });

    }

    public void signWithPhoneCode(){

         String userEnterCode = smsCode.getText().toString();
         PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,userEnterCode); // firebase woll compare these both codes..
         signInWithPhoneAuthCredential(credential);

    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){

                            Intent intent = new Intent(phoneSignActivity.this, main_menu.class);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            Toast.makeText(phoneSignActivity.this, "Code is Incorrect", Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks =       // I call the object from this class
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    codeSent = s;


                }
            };




}