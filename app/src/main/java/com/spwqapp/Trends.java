package com.spwqapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Trends extends AppCompatActivity {
    private Button TemperatureButton, pHButton, TurbidityButton, SampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        TemperatureButton =  (Button) findViewById(R.id.TemperatureButton);
        TemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTempeatureTrends();
            }
        });

        pHButton =  (Button) findViewById(R.id.pHButton);
        pHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpHTrends();
            }
        });

        TurbidityButton =  (Button) findViewById(R.id.TurbidityButton);
        TurbidityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTurbidityTrends();
            }
        });

    }
    public void openTempeatureTrends(){
        Intent intent = new Intent(this, TemperatureTrends.class);
        startActivity(intent);
    }

    public void openpHTrends(){
        Intent intent = new Intent(this, pHTrends.class);
        startActivity(intent);
    }

    public void openTurbidityTrends(){
        Intent intent = new Intent(this, TurbidityTrends.class);
        startActivity(intent);
    }

}