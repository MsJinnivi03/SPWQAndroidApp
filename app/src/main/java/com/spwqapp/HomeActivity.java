package com.spwqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    TextView phView,tempView,turidView,statView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tempView = findViewById(R.id.res_temp);
        phView = findViewById(R.id.res_ph);
        turidView = findViewById(R.id.res_turbid);
        statView = findViewById(R.id.res_stat);

        //Realtime data from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database.getReference("Real Status");
        DatabaseReference myyRef2 = database.getReference("Real Turbidity ");
        DatabaseReference myRef1 = database.getReference("Real pH");
        DatabaseReference myRef = database.getReference("Real Temperature");

        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                statView.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myyRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                turidView.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                tempView.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);

               phView.setText(result);
           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
      });
   }


    @Override
    public void onBackPressed() {
        Toast.makeText(HomeActivity.this, "Please choose the Menu bar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Homepage selected", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.item2:
                Toast.makeText(this, "View all Record selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
                return true;
            case R.id.item3:
                Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                return true;
            case R.id.item4:
                Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                return true;
            case R.id.item5:
                Toast.makeText(this, "Contact us selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, ContactActivity.class));
                return true;
            case R.id.item7:
                Toast.makeText(this, "Trends  selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, Trends.class));
                return true;

            case R.id.item6:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Do you want to Exit");
                builder.setCancelable(true);

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return true;

    }
}