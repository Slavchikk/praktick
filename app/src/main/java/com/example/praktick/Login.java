package com.example.praktick;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE);
        if(sharedPreferences != null)
        {
            etEmail.setText(sharedPreferences.getString("Email", ""));
            etPassword.requestFocus();
        }
    }

    public void goSignIn(View v) {
        if (etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("")) {
            Toast.makeText(Login.this, "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
        } else {
            Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(etEmail.getText().toString());
            boolean bools = matcher.find();
            if (bools) {
                goIn();
            } else {
                Toast.makeText(Login.this, "Адрес почты должнен иметь символ: '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goIn() {
        String emailLogin = String.valueOf(etEmail.getText());
        String passwordLogin = String.valueOf(etPassword.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        SentUser sentUser = new SentUser(emailLogin, passwordLogin);
        Call<UserMask> call = retrofitAPI.createUser(sentUser);
        call.enqueue(new Callback<UserMask>() {
            @Override
            public void onResponse(Call<UserMask> call, Response<UserMask> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Ошибка при вводе данных", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    if (response.body().getToken() != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences(
                                "Date", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("NickName", "" + response.body().getNickName()).apply();
                        sharedPreferences.edit().putString("Email", "" + emailLogin).apply();
                        sharedPreferences.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                        Onboarding.avtWithoiutLog = response.body().getAvatar();
                        Onboarding.nickWithoutLog = response.body().getNickName();
                        Intent intent = new Intent(Login.this, Main.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserMask> call, Throwable throwable) {
                Toast.makeText(Login.this, "Ошибка при входе в систему " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void goReg(View v)
    {
        startActivity(new Intent(this, Register.class));
    }
}