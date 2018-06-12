package com.example.fdghtdhg.ehub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class university extends AppCompatActivity {


    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list = new ArrayList<>();
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        listview = (ListView) findViewById(R.id.univ_lv);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(university.this, "Product Sans Regular.ttf", true);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);


                // Generate ListView Item using TextView
                return view;
            }
        };
        listview.setAdapter(adapter);
        dref = FirebaseDatabase.getInstance().getReference().child("universities").child("all-univ");

        dref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Calligrapher calligrapher = new Calligrapher(getBaseContext());
                calligrapher.setFont(university.this, "Product Sans Regular.ttf", true);
                list.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Calligrapher calligrapher = new Calligrapher(getBaseContext());
                calligrapher.setFont(university.this, "Product Sans Regular.ttf", true);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String univName = (String) parent.getItemAtPosition(position);
                try {
                    FileOutputStream fOut = openFileOutput("userUniversity.txt",MODE_PRIVATE);
                    fOut.write(univName.getBytes());
                    fOut.close();
                    startActivity(new Intent(university.this,profile.class));
                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String uid = firebaseAuth.getInstance().getUid();
                dref = FirebaseDatabase.getInstance().getReference("users");
                dref.child(uid).child("university").setValue(univName);


              //  Toast.makeText(getApplicationContext(),position + ":" + selectedItem,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
