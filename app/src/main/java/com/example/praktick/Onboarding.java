package com.example.praktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Onboarding extends AppCompatActivity {

    public static String avtWithoiutLog;
    public static String nickWithoutLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // << это вставить именно сюда
        setContentView(R.layout.activity_onboarding);// << это есть
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE);
        if(sharedPreferences != null)
        {
            if(!sharedPreferences.getString("NickName", "").equals(""))
            {
                nickWithoutLog = sharedPreferences.getString("NickName", "");
                avtWithoiutLog = sharedPreferences.getString("Avatar", "");
                startActivity(new Intent(this, Main.class));
            }
        }

    }


    public void goReg(View v)
    {
        startActivity(new Intent(this, Register.class));
    }

    public void goSignIn(View v)
    {
        startActivity(new Intent(this, Login.class));
    }
}