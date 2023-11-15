package com.example.harjoitustyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Location lastKnownLocation;
    private int updateInterval = 1800000; // 30min == 1800000ms


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
        }
    }

    public void getWeatherByCity(View view) {
        EditText cityEditText = (EditText) findViewById(R.id.cityEditText);
        String city = cityEditText.getText().toString();
        // Lähetetään web request OpenWeatherMap APIlle
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=" + city + "&lang=fi&appid=d0b9f77ecc980cd7dd65f878d7e8abe1";
        StringRequest getWeatherRequest = new StringRequest(Request.Method.GET, url, response->{
            // Kaikki ok -> response sisältää palvelimen JSON -vastauksen
            parseWeatherJsonAndStartIntent(response);
        }, error->{
            // Virhe

        });

        queue.add(getWeatherRequest);
    }

    public void getWeatherByLocation(View view) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            return;
        }
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateInterval,0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        RequestQueue queue = Volley.newRequestQueue(this);
         String url = "https://api.openweathermap.org/data/2.5/weather?units=metric&lat=" + latitude + "&lon=" + longitude + "&lang=fi&appid=d0b9f77ecc980cd7dd65f878d7e8abe1";
        StringRequest getWeatherRequest = new StringRequest(Request.Method.GET, url, response->{
            // Kaikki ok -> response sisältää palvelimen JSON -vastauksen
           parseWeatherJsonAndStartIntent(response);
        }, error->{
            // Virhe
        });
       queue.add(getWeatherRequest);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {

    }

    private void parseWeatherJsonAndStartIntent(String response) {
        Intent openWeatherIntent = new Intent(this, WeatherActivity.class);

        try {
            JSONObject weatherObject = new JSONObject( response );
            String stationName = weatherObject.getString("name");

            // Luetaan lämpötila, joka löytyy pääobjektin sisältä main -objektista temp -kentästä
            JSONObject main = weatherObject.getJSONObject("main");
            String temperature = main.getString("temp");
            String tempMin = main.getString("temp_min");
            String tempMax = main.getString("temp_max");
            String tempFeelsLike = main.getString("feels_like");
            String humidity = main.getString("humidity");

            JSONObject wind = weatherObject.getJSONObject("wind");
            String windSpeed = wind.getString("speed");

            JSONObject sys = weatherObject.getJSONObject("sys");
            String country = sys.getString("country");

            // weatherdescription löytyy "weather" -nimisen JSONArrayn sisältä ensimmäisestä indeksistä
            String weatherDescription = weatherObject.getJSONArray("weather")
                    .getJSONObject(0).getString("description");

            openWeatherIntent.putExtra("STATION_NAME", stationName);
            openWeatherIntent.putExtra("TEMPERATURE", temperature.toString());
            openWeatherIntent.putExtra("COUNTRY", country);
            openWeatherIntent.putExtra("TEMPERATUREMAX", tempMax.toString());
            openWeatherIntent.putExtra("TEMPERATUREMIN", tempMin);
            openWeatherIntent.putExtra("TEMPERATUREFEELLIKE", tempFeelsLike);
            openWeatherIntent.putExtra("HUMIDITY", humidity);
            openWeatherIntent.putExtra("WINDSPEED", windSpeed);
            openWeatherIntent.putExtra("WEATHERDESCRIPTION", weatherDescription);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity(openWeatherIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
