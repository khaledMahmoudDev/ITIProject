package iti.abdallah.logintest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends Activity implements View.OnClickListener {
    Button logout;
    Button myMovies;
    Button about;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        logout=(Button)findViewById(R.id.logout);
        myMovies=(Button)findViewById(R.id.mymovies);
        about=(Button)findViewById(R.id.about);

        logout.setOnClickListener(this);
        myMovies.setOnClickListener(this);
        about.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logout){
            SharedPreferences SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
            SharedPreferences.Editor edit = SP.edit();
            edit.putBoolean("LogKey", false);
            edit.commit();

            Intent i = new Intent(Home.this,MainActivity.class);
            startActivity(i);
        }

        if(view.getId()==R.id.mymovies){
            Intent movies = new Intent(Home.this,MoviesList.class);
            startActivity(movies);
        }
        if(view.getId()==R.id.about){
            Intent about =new Intent(Home.this,About.class);
            startActivity(about);
        }
    }
}
