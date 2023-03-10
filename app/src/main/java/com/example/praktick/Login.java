package com.example.praktick;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    public static UserMask User;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void nextMain(View v) {
        if (etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")) {
            Toast.makeText(Login.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
        } else {
            Pattern p = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(etEmail.getText().toString());
            boolean b = m.find();
            if (b) {
                callLogin();
            } else {
                Toast.makeText(Login.this, "Поле для Email обязательно должно содержать символ '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callLogin() {
        String email = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        ModelSentUser modelSendUser = new ModelSentUser(email, password);
        Call<UserMask> call = retrofitAPI.createUser(modelSendUser);
        call.enqueue(new Callback<UserMask>() {
            @Override
            public void onResponse(Call<UserMask> call, Response<UserMask> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Пользователь с такой почтой и паролем не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    if (response.body().getToken() != null) {
                        User = response.body();
                        Toast.makeText(Login.this, "ЗАШЕЕЕЕЛ: " , Toast.LENGTH_LONG).show();
                       /* Intent intent = new Intent(Login.this, Main.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);*/
                    }
                }
            }

            @Override
            public void onFailure(Call<UserMask> call, Throwable t) {
                Toast.makeText(Login.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}