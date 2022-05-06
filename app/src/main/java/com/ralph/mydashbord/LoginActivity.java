package com.ralph.mydashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ralph.mydashbord.model.LoginRequest;
import com.ralph.mydashbord.model.LoginResponse;
import com.ralph.mydashbord.service.ApiClient;

import retrofit2.Call;

import android.os.Handler;


import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static ArrayList<String> getonToken = new ArrayList<String>();
    EditText  username, password;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginBtn = (Button) findViewById(R.id.cirLoginButton);

        loginBtn.setOnClickListener(v -> {
            if(TextUtils.isEmpty(username.getText().toString()) && TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(LoginActivity.this,"Username and password required", Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(LoginActivity.this,"Password required",Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(username.getText().toString())){
                Toast.makeText(LoginActivity.this,"Username required",Toast.LENGTH_LONG).show();
            }
            else{

                login();
            }
        });
    }
    public void login() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){

                    Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();

                    LoginResponse loginResponse = response.body();

                    getonToken.add(response.body().getAccessToken());

                    System.out.println("Response login"+loginResponse.toString());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("data",loginResponse.toString()));
                        }
                    },700);

                }else{
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
               System.out.println("-------- CALL ---------"+call);
                Toast.makeText(LoginActivity.this,"Error :) "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}