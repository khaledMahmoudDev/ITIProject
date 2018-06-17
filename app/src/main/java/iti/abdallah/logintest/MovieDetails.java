package iti.abdallah.logintest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Movies;
import rest.ApiClientMovies;
import rest.ApiInterfaceMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    ImageView poster ;
    ImageView profile;
    Integer MOVIE_NUMBER;
    TextView movieName;
    TextView year;
    TextView rate;
    TextView genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        poster=(ImageView)findViewById(R.id.poster);
        profile=(ImageView)findViewById(R.id.profile);
        movieName=(TextView)findViewById(R.id.movie_name);
        year=(TextView)findViewById(R.id.year);
        rate=(TextView)findViewById(R.id.rate);
        genre=(TextView)findViewById(R.id.genre);



        ApiInterfaceMovies apiService = ApiClientMovies.getClient().create(ApiInterfaceMovies.class);


        Call<ArrayList<Movies>> call = apiService.getTopRatedMovies();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        MOVIE_NUMBER=getIntent().getIntExtra("MOVIE_NUMBER",0);
        progressDialog.show();
        call.enqueue(new Callback<ArrayList<Movies>>() {

            @Override
            public void onResponse(Call<ArrayList<Movies>> call, Response<ArrayList<Movies>> response) {
                ArrayList<Movies> movies = response.body();


                if (response.isSuccessful())
                {

                    //Set Movie Profile Data....

                    Glide.with(getApplicationContext())
                            .load(movies.get(MOVIE_NUMBER).getImage())
                            .into(poster);

                    Glide.with(getApplicationContext())
                            .load(movies.get(MOVIE_NUMBER).getImage())
                            .into(profile);

                    movieName.setText(movies.get(MOVIE_NUMBER).getTitle());
                    year.setText("Release Year : " +movies.get(MOVIE_NUMBER).getReleaseYear());
                    rate.setText("Rating :  " + movies.get(MOVIE_NUMBER).getRating());

                    //Toast.makeText(getApplicationContext(),movies.get(MOVIE_NUMBER).getGenre().get(0).toString(),Toast.LENGTH_LONG).show();

                    if(MOVIE_NUMBER!=13) {

                        genre.setText("Genre: " + movies.get(MOVIE_NUMBER).getGenre().get(0).toString() + "," + movies.get(MOVIE_NUMBER).getGenre().get(1).toString());

                    }else if (MOVIE_NUMBER==13){

                        genre.setText("Genre: " + movies.get(MOVIE_NUMBER).getGenre().get(0).toString());

                    }
                    progressDialog.dismiss();

                    //Cashing data here to reuse it when it's offline






                    //Toast.makeText(getApplicationContext(), "Number : " + movies.size(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed To Connect Server.. " + response.code(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movies>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Unable To Connect..", Toast.LENGTH_LONG).show();
            }
        });




    }
}
