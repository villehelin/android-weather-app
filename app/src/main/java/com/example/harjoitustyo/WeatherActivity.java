package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        // Update UI elements
        String stationName = getIntent().getStringExtra("STATION_NAME");
        if (stationName == null)
            stationName = "VIRHE!";
        TextView stationNameTextView = (TextView) findViewById(R.id.stationNameTextView);
        stationNameTextView.setText(stationName);


        String country = getIntent().getStringExtra("COUNTRY");
        if (country == null)
            country = "Tarkista sijainti";
        TextView countryTextView = (TextView) findViewById(R.id.countryTextView);
        countryTextView.setText(country);


        if (getIntent().getStringExtra("TEMPERATURE") != null) {
            double temperature = Double.parseDouble(getIntent().getStringExtra("TEMPERATURE"));
            TextView temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
            temperatureTextView.setText("" + temperature + "°C");
        }
        else {
            double temperature = 0;
            TextView temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
            temperatureTextView.setText("" + temperature + "°C");
        }


        if (getIntent().getStringExtra("TEMPERATUREMAX") != null) {
            double tempMax = Double.parseDouble(getIntent().getStringExtra("TEMPERATUREMAX"));
            TextView tempMaxTextView = (TextView) findViewById(R.id.temperatureMaxTextView);
            tempMaxTextView.setText("" + tempMax + "°C");
        }
        else {
            double  tempMax = 0;
            TextView tempMaxTextView = (TextView) findViewById(R.id.temperatureMaxTextView);
            tempMaxTextView.setText("" + tempMax + "°C");
        }


        if (getIntent().getStringExtra("TEMPERATUREMIN") != null) {
            double tempMin = Double.parseDouble(getIntent().getStringExtra("TEMPERATUREMIN"));
            TextView tempMinTextView  = (TextView)findViewById(R.id.temperatureMinTextView);
            tempMinTextView.setText("" + tempMin+ "°C");
        }else {
            double tempMin = 0;
            TextView tempMinTextView  = (TextView)findViewById(R.id.temperatureMinTextView);
            tempMinTextView.setText("" + tempMin+ "°C");
        }


        if (getIntent().getStringExtra("TEMPERATUREFEELLIKE") != null) {
            double tempFeelsLike = Double.parseDouble(getIntent().getStringExtra("TEMPERATUREFEELLIKE"));
            TextView tempFeelsLikeTextView  = (TextView)findViewById(R.id.temperatureFeelsLikeTextView);
            tempFeelsLikeTextView.setText("" + tempFeelsLike + "°C");
        }else {
            double tempFeelsLike = 0;
            TextView tempFeelsLikeTextView  = (TextView)findViewById(R.id.temperatureFeelsLikeTextView);
            tempFeelsLikeTextView.setText("" + tempFeelsLike + "°C");
        }


        if (getIntent().getStringExtra("HUMIDITY") != null) {
            double humidity = Double.parseDouble(getIntent().getStringExtra("HUMIDITY"));
            TextView humidityTextView = (TextView)findViewById(R.id.humidityTextView);
            humidityTextView.setText("" + humidity + "%");
        }else {
            double humidity = 0;
            TextView humidityTextView = (TextView)findViewById(R.id.humidityTextView);
            humidityTextView.setText("" + humidity + "%");
        }


        if (getIntent().getStringExtra("WINDSPEED") != null) {
            double windSpeed = Double.parseDouble(getIntent().getStringExtra("WINDSPEED"));
            TextView windSpeedTextView = (TextView)findViewById(R.id.windspeedTextView);
            windSpeedTextView.setText("" + windSpeed + "m/s");

        }else {
            double windSpeed = 0;
            TextView windSpeedTextView = (TextView)findViewById(R.id.windspeedTextView);
            windSpeedTextView.setText("" + windSpeed + "m/s");
        }


        String weatherDescription = getIntent().getStringExtra("WEATHERDESCRIPTION");
        TextView descriptionTextView = (TextView)findViewById(R.id.weatherDescriptionTextView);
        descriptionTextView.setText(weatherDescription);
    }
}