package iti.abdallah.logintest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    SharedPreferences SP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button MainSignIn = (Button) findViewById(R.id.mainSignIn);
        TextView MainSignUp = (TextView)findViewById(R.id.mainSignUp);


        SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
        MainSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Boolean LogInFlagMain = SP.getBoolean("LogKey",false);
                Toast.makeText(getApplicationContext(),LogInFlagMain+"",Toast.LENGTH_LONG).show();
                if (LogInFlagMain == true)
                {
                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(MainActivity.this, LogIn.class);
                    startActivity(i);
                }

            }
        });
        MainSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });

    }
}
