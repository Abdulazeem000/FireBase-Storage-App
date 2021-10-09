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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {


    EditText mail;
    EditText password;
    Button enter;

    FirebaseAuth auth = FirebaseAuth.getInstance(); // it will use for signup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mail = findViewById(R.id.editTextEmailSignUp);
        password = findViewById(R.id.editTextPasswordSignUp);
        enter = findViewById(R.id.buttonenter);



        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = mail.getText().toString();
                String userPassword = password.getText().toString();

                signUpFirebase(userEmail,userPassword);


            }
        });
    }

              public void signUpFirebase(String userEmail, String userPassword){

                  auth.createUserWithEmailAndPassword(userEmail, userPassword)
                          .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  if (task.isSuccessful()) {
                                      // Sign in success, update UI with the signed-in user's information

                                      Toast.makeText(SignUp.this, "Your Account is created", Toast.LENGTH_SHORT).show();
                                      Intent intent = new Intent(SignUp.this,MainActivity.class);
                                      startActivity(intent);
                                      finish();;
                                      
                                  } else {
                                      // If sign in fails, display a message to the user.

                                      Toast.makeText(SignUp.this, "There is a problem.", Toast.LENGTH_SHORT).show();


                                  }
                              }
                          });



              }


}