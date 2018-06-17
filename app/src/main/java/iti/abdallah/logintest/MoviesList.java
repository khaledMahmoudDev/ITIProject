package iti.abdallah.logintest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import model.Movies;
import rest.ApiClientMovies;
import rest.ApiInterfaceMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);


        ApiInterfaceMovies apiService = ApiClientMovies.getClient().create(ApiInterfaceMovies.class);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));


        Call<ArrayList<Movies>> call = apiService.getTopRatedMovies();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // ON CLICK ITEAM HANDLE
                        Intent movieDetails = new Intent(getApplicationContext(), MovieDetails.class);
                        movieDetails.putExtra("MOVIE_NUMBER", position);
                        startActivity(movieDetails);
//                        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
                    }
                })
        );
        progressDialog.show();
        call.enqueue(new Callback<ArrayList<Movies>>() {

            @Override
            public void onResponse(Call<ArrayList<Movies>> call, Response<ArrayList<Movies>> response) {
                ArrayList<Movies> movies = response.body();


                if (response.isSuccessful()) {

                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_cell, getApplicationContext()));
                    progressDialog.dismiss();

                    //Toast.makeText(getApplicationContext(), "Number : " + movies.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Connect Server.. " + response.code(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movies>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable To Connect..", Toast.LENGTH_LONG).show();
            }
        });


    }
}
