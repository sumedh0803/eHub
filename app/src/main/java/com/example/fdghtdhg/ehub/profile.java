package com.example.fdghtdhg.ehub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class profile extends AppCompatActivity {

    LinearLayout ll1,ll2;
    String nameString;
    TextView name_desc,branch_desc,univ_desc,sem_desc;
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
                startActivity(new Intent(profile.this,university.class));
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
                try
                {
                    FileOutputStream fOut=openFileOutput("userName.txt", MODE_PRIVATE);
                    fOut.write(nameString.getBytes());
                    fOut.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


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

    public void showUnivList()
    {
        List<String> univ = new ArrayList<String>();
        univ.add("Mumbai University");
        univ.add("Pune University");
        univ.add("Anna University");
        univ.add("Gujarat Technical University");
        univ.add("GGSIPU - Guru Gobind");
        univ.add("PTU - IK Gujral Punjab");
        univ.add("KTU APJ Abdul Kalam");
        univ.add("JNTUH JNTU Hyderabad");
        univ.add("JNTUK JNTU Kakinada");
        univ.add("Rajasthan Technical University");
        univ.add("RGPV - Rajiv Gandhi");
        univ.add("DU - University of Delhi");
        univ.add("UPTU - Uttar Pradesh");
        univ.add("VTU - Visveswaraya");
        univ.add("WBUT - Maulana Abul");

        //Create sequence of items
        final CharSequence[] University = univ.toArray(new String[univ.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Universities");
        dialogBuilder.setItems(University, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String univString = University[item].toString();  //Selected item in listview
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();

    }

    public void displayProfile()
    {
        //add here
    }
}
