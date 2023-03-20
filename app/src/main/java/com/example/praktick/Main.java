package com.example.praktick;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main extends AppCompatActivity {

    ImageView imageProfile;
    TextView textBack;
    private List<Quote> listQuote = new ArrayList<>();
    private OptionsQuote pAdapter;
    private List<Feels> listFeeling = new ArrayList<>();
    private OptionsFeels dataRVAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvQoute = findViewById(R.id.listFact);
        pAdapter = new OptionsQuote(Main.this, listQuote);
        lvQoute.setAdapter(pAdapter);
        new ReturnQuotes().execute();

        RecyclerView rbFeels = findViewById(R.id.recVie);
        rbFeels.setHasFixedSize(true);
        rbFeels.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dataRVAdapter = new OptionsFeels(listFeeling, Main.this);
        rbFeels.setAdapter(dataRVAdapter);
        new ReturnFeels().execute();

        imageProfile = findViewById(R.id.imgProfils);
        new OptionsQuote.decodeImage((ImageView) imageProfile)
                .execute(Onboarding.avtWithoiutLog);

        textBack = findViewById(R.id.tbWithBack);
        textBack.setText(textBack.getText().toString() + Onboarding.nickWithoutLog + "!");
    }

    private class ReturnQuotes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listQuote.clear();
                pAdapter.notifyDataSetInvalidated();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Quote tempProduct = new Quote(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );
                    listQuote.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }

            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "Ошибка при выводе цитат", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ReturnFeels extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listFeeling.clear();
                dataRVAdapter.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Feels tempProduct = new Feels(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    listFeeling.add(tempProduct);
                    dataRVAdapter.notifyDataSetChanged();
                }
                listFeeling.sort(Comparator.comparing(Feels::getPosition));
                dataRVAdapter.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "Ошибка при выводе чувств", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void goMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public  void goProfil(View view)
    {
        startActivity(new Intent(this, Profile.class));
    }

    public void goListen(View view)
    {
        startActivity(new Intent(this, Listen.class));
    }
}