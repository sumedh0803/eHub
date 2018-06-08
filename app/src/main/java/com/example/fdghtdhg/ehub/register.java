package com.example.fdghtdhg.ehub;

import android.app.ProgressDialog;
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

public class register extends AppCompatActivity {
    EditText email, pwd,name,clg,sem;
    Button signup;
    private ProgressDialog pb;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        name = findViewById(R.id.name);
        clg = findViewById(R.id.clg);
        sem = findViewById(R.id.sem);
        signup = findViewById(R.id.signup);
        pb = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String pwdString = pwd.getText().toString().trim();
                String nameString = name.getText().toString().trim();
                String clgString = clg.getText().toString().trim();
                String semString = sem.getText().toString().trim();

                pb.setMessage("Registering..");
                pb.show();
                firebaseAuth.createUserWithEmailAndPassword(emailString,pwdString)
                        .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
                                    pb.cancel();
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
