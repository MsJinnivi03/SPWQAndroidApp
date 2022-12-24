package com.spwqapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    TextView facebooklink,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        facebooklink = findViewById(R.id.facebook);
        address = findViewById(R.id.Address);

        //For facebook link
        facebooklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/SPQW_official-106372438821883/");
            }
        });
        //For address link
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.google.com/maps/dir//Poblacion+Norte,+Rizal,+Nueva+Ecija/@15.6985395,120.8231258,10z/data=!4m8!4m7!1m0!1m5!1m1!1s0x3390ce4b381a9f2b:0x12ac371dbbf556d!2m2!1d121.1073082!2d15.7129831");
            }
        });

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}