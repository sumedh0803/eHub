package com.example.fdghtdhg.ehub;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class semester extends AppCompatActivity {

    ListView sem_lv;
    Resources res;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        sem_lv = findViewById(R.id.sem_lv);
        res = getResources();
        String[] sem = res.getStringArray(R.array.semester);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sem)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(semester.this, "Product Sans Regular.ttf", true);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);


                // Generate ListView Item using TextView
                return view;
            }
        };
        sem_lv.setAdapter(adapter);
        sem_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String semString = sem_lv.getItemAtPosition(position).toString();
                try {
                    FileOutputStream fOut = openFileOutput("userSemester.txt",MODE_PRIVATE);
                    fOut.write(semString.getBytes());
                    fOut.close();
                    String uid = firebaseAuth.getInstance().getUid();
                    dref = FirebaseDatabase.getInstance().getReference("users");
                    dref.child(uid).child("semester").setValue(sem_lv.getItemAtPosition(position).toString());
                    startActivity(new Intent(semester.this,profile.class));
                    finish();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(semester.this,profile.class));
    }

    }

