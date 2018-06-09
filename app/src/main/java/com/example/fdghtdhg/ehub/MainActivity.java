package com.example.fdghtdhg.ehub;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    private boolean isSecondActivityLaunched;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv1);

        new Handler().postDelayed(new Runnable() {
        // Using handler with postDelayed called runnable run method
            @Override
               public void run() {
                presentActivity(tv);
                // close this activity


            }
        }, 3*1000); // wait for 3 seconds
        //ActivityCompat. finishAfterTransition(this);


    }


    public void presentActivity(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        if(firebaseAuth.getCurrentUser() != null)
        {
            Intent i=new Intent(MainActivity.this,home.class);
            i.putExtra(login.EXTRA_CIRCULAR_REVEAL_X, revealX);
            i.putExtra(login.EXTRA_CIRCULAR_REVEAL_Y, revealY);
            ActivityCompat.startActivity(this, i, options.toBundle());
        }
        else
        {
            Intent i=new Intent(MainActivity.this,login.class);
            i.putExtra(login.EXTRA_CIRCULAR_REVEAL_X, revealX);
            i.putExtra(login.EXTRA_CIRCULAR_REVEAL_Y, revealY);
            ActivityCompat.startActivity(this, i, options.toBundle());
        }




        isSecondActivityLaunched = true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(isSecondActivityLaunched) {
            finish();
        }
    }
}


