package com.example.fdghtdhg.ehub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import me.anwarshahriar.calligrapher.Calligrapher;

public class login extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;

    Button emailSignin;
    EditText email, pwd;
    TextView register,errorMsg;
    ProgressDialog pd;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(this);

        //Set universal font for the activity
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

        firebaseAuth = FirebaseAuth.getInstance();

        //Animation
        final Intent intent = getIntent();
        rootLayout = findViewById(R.id.root_layout);
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y))
        {
            rootLayout.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }
        else
        {
            rootLayout.setVisibility(View.VISIBLE);
        }

        //login
        emailSignin = findViewById(R.id.emailSignin);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        register = findViewById(R.id.register);

        emailSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String pwdString = pwd.getText().toString().trim();
                userlogin(emailString,pwdString);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,register.class);
                startActivity(i);
            }
        });





    }
    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    protected void userlogin(String emailString,String pwdString)
    {
        errorMsg = findViewById(R.id.errorMsg);
        pd.setMessage("Signing in");
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(emailString,pwdString)
        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplication(),"Go to home activity",Toast.LENGTH_SHORT).show();
                    errorMsg.setVisibility(View.GONE);
                    pd.cancel();
                }
                else
                {
                    pd.cancel();
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
        });



    }
}
