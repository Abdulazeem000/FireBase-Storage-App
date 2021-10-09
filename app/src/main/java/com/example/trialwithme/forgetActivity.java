package com.example.trialwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetActivity extends AppCompatActivity {


    EditText resetemail;
    Button reset;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);


        resetemail = findViewById(R.id.editTextReset);
        reset = findViewById(R.id.buttonReset);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = resetemail.getText().toString();

                auth.sendPasswordResetEmail(useremail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(forgetActivity.this, "We sent an Email to reset your Password", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                finish();


            }

        });


    }
}