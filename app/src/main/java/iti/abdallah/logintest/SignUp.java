package iti.abdallah.logintest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.ModelSignUp;
import rest.ApiClientSginUp;
import rest.ApiInterfaceSginUp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends Activity implements View.OnClickListener {

    EditText name ;
    EditText phone;
    Button reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=(EditText)findViewById(R.id.SignUpName);
        phone=(EditText)findViewById(R.id.SignUpPhone);
        reg=(Button)findViewById(R.id.SignUoNow);
        reg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        //Get User Data from EditText..
        String userName=name.getText().toString();
        String userPhone=phone.getText().toString();
        ApiInterfaceSginUp apiService =
                ApiClientSginUp.getClient().create(ApiInterfaceSginUp.class);

        final ProgressDialog load=new ProgressDialog(this);
        load.setMessage("Signing Up..");

        Call<ModelSignUp> call = apiService.getAnsweres(userName,userPhone);
        load.show();
        call.enqueue(new Callback<ModelSignUp>() {
            @Override
            public void onResponse(Call<ModelSignUp>call, Response<ModelSignUp> response) {
                //Get Response from json..
                String status = response.body().getStatus();
                String result = response.body().getResult();
                if(response.isSuccessful()) {
                    load.dismiss();
                    if(status.equals("SUCCESS")) {


                        SharedPreferences SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
                        SharedPreferences.Editor edit = SP.edit();
                        edit.putBoolean("LogKey", true);
                        edit.commit();
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SignUp.this, LogIn.class);
                        startActivity(i);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                        name.setText("");
                        phone.setText("");
                    }
                }

                


            }

            @Override
            public void onFailure(Call<ModelSignUp>call, Throwable t) {
                load.dismiss();
                Toast.makeText(getApplicationContext(),"No INTERNET..",Toast.LENGTH_LONG).show();


            }
        });

    }
}
