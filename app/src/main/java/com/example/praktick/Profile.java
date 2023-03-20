package com.example.praktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    ImageView imageProfile;
    TextView txtNameUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageProfile = findViewById(R.id.imgUser);
        new OptionsQuote.decodeImage((ImageView) imageProfile)
                .execute(Onboarding.avtWithoiutLog);
        txtNameUser = findViewById(R.id.nameUser);
        txtNameUser.setText (Onboarding.nickWithoutLog);
    }

    public void goExit(View view)
    {
        SharedPreferences prefs = getSharedPreferences(
                "Date", Context.MODE_PRIVATE);
        prefs.edit().putString("Avatar", "").apply();
        prefs.edit().putString("NickName", "").apply();

        startActivity(new Intent(this, Login.class));
    }
    public  void goMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public  void goMain(View view)
    {
        startActivity(new Intent(this, Main.class));
    }

    public void goListen(View view)
    {
        startActivity(new Intent(this, Listen.class));
    }
}