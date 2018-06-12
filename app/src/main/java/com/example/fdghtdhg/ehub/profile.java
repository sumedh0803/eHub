package com.example.fdghtdhg.ehub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class profile extends AppCompatActivity {

    LinearLayout ll1, ll2,ll3,ll4;
    String nameString = "",univString = "",branchString = "",semString = "";
    TextView name_desc, branch_desc, univ_desc, sem_desc;
    Button saveData;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);
        displayProfile();

        ll1 = findViewById(R.id.ll1);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });

        ll2 = findViewById(R.id.ll2);
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, university.class));
                finish();
            }
        });

        ll3 = findViewById(R.id.ll3);
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, branch.class));
                finish();
            }
        });

        ll4 = findViewById(R.id.ll4);
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, semester.class));
                finish();
            }
        });

        saveData = findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,home.class));
                finish();
            }
        });


    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_name);

        dialogBuilder.setTitle("Enter your name");
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                nameString = edt.getText().toString().trim();
                name_desc = findViewById(R.id.name_desc);
                name_desc.setText(nameString);
                try {
                    FileOutputStream fOut = openFileOutput("userName.txt", MODE_PRIVATE);
                    fOut.write(nameString.getBytes());
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String uid = firebaseAuth.getInstance().getUid();
                dref = FirebaseDatabase.getInstance().getReference("users");
                dref.child(uid).child("name").setValue(nameString);



            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void displayProfile()
    {
        try
        {
            FileInputStream fisName = openFileInput("userName.txt");
            FileInputStream fisUniversity = openFileInput("userUniversity.txt");
            FileInputStream fisBranch = openFileInput("userBranch.txt");
            FileInputStream fisSem = openFileInput("userSemester.txt");
            int c;

            while ((c = fisName.read()) > 0)
            {
                nameString += Character.toString((char) c);
            }
            while ((c = fisUniversity.read()) > 0)
            {
                univString += Character.toString((char) c);
            }
            while ((c = fisBranch.read()) > 0)
            {
                branchString += Character.toString((char) c);
            }
            while ((c = fisSem.read()) > 0)
            {
                semString += Character.toString((char) c);
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        name_desc = findViewById(R.id.name_desc);
        name_desc.setText(nameString);
        univ_desc = findViewById(R.id.univ_desc);
        univ_desc.setText(univString);
        branch_desc = findViewById(R.id.branch_desc);
        branch_desc.setText(branchString);
        sem_desc = findViewById(R.id.sem_desc);
        sem_desc.setText(semString);
    }
}
