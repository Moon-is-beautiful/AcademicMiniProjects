package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout homeRL;
    private TextView cityNameTV,temperatureTV,conditionTV,windTV;
    private RecyclerView weatherRV;
    private ProgressBar loadingPB;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeRL=findViewById(R.id.idRLHome);
        cityNameTV=findViewById(R.id.idTVCityName);
        temperatureTV=findViewById(R.id.idTVTemperature);
        conditionTV=findViewById(R.id.idTVCondition);
        weatherRV=findViewById(R.id.idRVWeather);
        loadingPB=findViewById(R.id.idPBLoading);
        windTV=findViewById(R.id.idTVwind);
        weatherRVModalArrayList=new ArrayList<>();
        weatherRVAdapter=new WeatherRVAdapter(this,weatherRVModalArrayList);
        weatherRV.setAdapter(weatherRVAdapter);

        getWeatherInfo();
    }
    private  void getWeatherInfo(){
        String url="https://api.open-meteo.com/v1/forecast?latitude=30.2672&longitude=-97.7431&current=temperature_2m,pressure_msl,wind_speed_10m&hourly=temperature_2m&temperature_unit=fahrenheit&timezone=America%2FChicago";
        cityNameTV.setText("Austin");
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                homeRL.setVisibility(View.VISIBLE);
                weatherRVModalArrayList.clear();
                try {
                    temperatureTV.setText(response.getJSONObject("current").getString("temperature_2m") + response.getJSONObject("current_units").getString("temperature_2m"));
                    conditionTV.setText(response.getJSONObject("current").getString("pressure_msl") + response.getJSONObject("current_units").getString("pressure_msl"));
                    windTV.setText(response.getJSONObject("current").getString("wind_speed_10m") + response.getJSONObject("current_units").getString("wind_speed_10m"));

                    JSONArray timeArray = response.getJSONObject("hourly").getJSONArray("time");
                    JSONArray tempArray = response.getJSONObject("hourly").getJSONArray("temperature_2m");
                    int index = 0;
                    for (int d = 0; d < 7; d++) {
                        String time = timeArray.getString(index);
                        int ave = 0;
                        for (int i = 0; i < 24; i++) {
                            ave += tempArray.getInt(index);
                            index++;
                        }
                        ave /= 24;
                        weatherRVModalArrayList.add(new WeatherRVModal(time, ave + ""));
                    }
                    weatherRVAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, null);

        requestQueue.add(jsonObjectRequest);

    }
}