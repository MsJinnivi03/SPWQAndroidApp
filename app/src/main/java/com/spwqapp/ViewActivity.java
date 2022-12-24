package com.spwqapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
        private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        listView = findViewById(R.id.listview1);


        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listTemp = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,list);


        listView.setAdapter(adapter);
        //Getting the Data from Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Result");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                listTemp.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    listTemp.add(dataSnapshot.getValue().toString());
                }
                for (int i = listTemp.size()-1; i >=0 ; i--) {
                    list.add(listTemp.get(i));
                }

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}