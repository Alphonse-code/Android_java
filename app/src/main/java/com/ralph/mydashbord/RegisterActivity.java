package com.ralph.mydashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ralph.mydashbord.model.RegisterRequest;
import com.ralph.mydashbord.model.RegisterResponse;
import com.ralph.mydashbord.service.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    EditText  username, email, password ,confirmPassword;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        username = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextPasswordConfirm);
        registerBtn = (Button) findViewById(R.id.cirRegisterButton);

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(confirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"All field required", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(password.getText().toString()) != TextUtils.isEmpty(confirmPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password != confirm password", Toast.LENGTH_LONG).show();
                }
                else{
                    register();
                }
            }
        });
    }

    public void register() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setUsername(username.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"Register successfull", Toast.LENGTH_LONG).show();
                    RegisterResponse registerResponse = response.body();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class).putExtra("message",registerResponse.toString()));
                        }
                    },700);
                }else{
                    Toast.makeText(RegisterActivity.this,"Register Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
// asn1
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}