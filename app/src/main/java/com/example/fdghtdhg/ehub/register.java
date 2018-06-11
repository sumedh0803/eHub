package com.example.fdghtdhg.ehub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import me.anwarshahriar.calligrapher.Calligrapher;

public class register extends AppCompatActivity {
    EditText email, pwd;
    Button signup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        signup = findViewById(R.id.signup);

        firebaseAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String pwdString = pwd.getText().toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(emailString,pwdString)
                        .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(register.this, profile.class));

                                }
                                else
                                {
                                    Toast.makeText(getBaseContext(),"Failure",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}
