package com.example.fdghtdhg.ehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class getSubjects extends AppCompatActivity {

    ListView subjectList;
    Button addSubj,next;
    EditText subjString;
    ArrayList<String> subjList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_subjects);
        //Set universal font for the activity
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);
        getSupportActionBar().setElevation(0);

        subjString = findViewById(R.id.subjString);
        addSubj = findViewById(R.id.addSubj);
        subjectList = findViewById(R.id.subjectList);


        final customSubjectAdapter csa = new customSubjectAdapter(getSubjects.this,subjList);


        addSubj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjString.getText().toString().trim();
                subjList.add(subject);
                csa.notifyDataSetChanged();
                subjString.setText("");

                /**
                 * TODO: save the subject list in a file
                 */


            }
        });
        subjectList.setAdapter(csa);




    }
}
