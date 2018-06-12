package com.example.fdghtdhg.ehub;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class branch extends AppCompatActivity {

    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list = new ArrayList<>();
    String univ_name = "";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        //set the array adapter
        listview = (ListView) findViewById(R.id.branch_lv);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(branch.this, "Product Sans Regular.ttf", true);
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

        //get university name
        File f = getBaseContext().getFileStreamPath("userUniversity.txt");
        if (f.exists()) {
            try {
                FileInputStream fis = openFileInput("userUniversity.txt");
                int c;

                while ((c = fis.read()) > 0) {
                    univ_name += Character.toString((char) c);
                }
                Toast.makeText(getBaseContext(),univ_name,Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dref = FirebaseDatabase.getInstance().getReference().child("universities").child("branch").child(univ_name);
            dref.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    list.add(dataSnapshot.getValue(String.class));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
                    String branchName = (String) parent.getItemAtPosition(position);
                    try {
                        FileOutputStream fOut = openFileOutput("userBranch.txt", MODE_PRIVATE);
                        fOut.write(branchName.getBytes());
                        fOut.close();
                        startActivity(new Intent(branch.this, profile.class));
                        finish();



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String uid = firebaseAuth.getInstance().getUid();
                    dref = FirebaseDatabase.getInstance().getReference("users");
                    dref.child(uid).child("branch").setValue(branchName);
                }
            });

        }
        else
        {
            startActivity(new Intent(branch.this,profile.class));
            //Toast.makeText(profile.class,"First, select your university.",Toast.LENGTH_SHORT).show();
        }




    }
}
