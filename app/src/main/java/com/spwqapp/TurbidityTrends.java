package com.spwqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TurbidityTrends extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbidity_trends);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mChart = (LineChart) findViewById(R.id.linechart);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Float> valueList = new ArrayList<>();


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,list);

        //Getting the Data from Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Result");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(dataSnapshot.getValue().toString());

                }
                //  for turbidity
                for (int i = 0; i < list.size(); i++) {
                    String str =  list.get(i);
                    String[] arrOfStr = str.split(":", 8);

                    String turbilityStr=arrOfStr[6];
                    int max=8;

                    while (true) {
                        try {
                            int d = Integer.parseInt(turbilityStr.substring(max-1,max));
                            break;
                        } catch (NumberFormatException nfe) {
                            System.out.println("Not a number");
                        }
                        max--;
                    }

                    float turbidity=  Float.parseFloat(turbilityStr.substring(1,max));

                    // append value of turbidity
                    valueList.add(turbidity);
                    System.out.println("HELLO");
                    try {
                        String[] arrOfStr2 = list.get(i).split(" ", 10);
                        String time=arrOfStr2[2];
                        String dateTime=arrOfStr2[1]+" "+time.substring(0,time.length()-1 );

                    // append date here
                        dateList.add(dateTime);

                    } catch (Exception e) {
                        System.out.println("error");
                    }

                }



                // setting the graph
                mChart.setBackgroundColor(Color.WHITE);
                // enable scaling and dragging
                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(true);
                mChart.setDrawGridBackground(false);
                mChart.setHighlightPerDragEnabled(true);
                // enable touch gestures
                mChart.setTouchEnabled(true);
                mChart.getAxisRight().setEnabled(false);

                mChart.setDragDecelerationFrictionCoef(0.9f);

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
                xAxis.setTextSize(10f);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setDrawAxisLine(false);
                xAxis.setDrawGridLines(true);
                xAxis.setTextColor(Color.rgb(0,0,0));
                xAxis.setCenterAxisLabels(true);
                xAxis.setGranularity(1f); // one hour
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
                    //Getting the Date and Time
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        long millis = TimeUnit.HOURS.toMillis((long) value);
                        return mFormat.format(new Date(millis));
                    }
                });


                //call set data
               setData(dateList,valueList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setData(ArrayList dateList, ArrayList valueList) {
        String myDate="";
        System.out.println(dateList.size()+"Val Size");
        long millis=0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {
            // convert string to ms
            myDate= (String) dateList.get(i);

            try {
                Date date = sdf.parse(myDate);
                millis = date.getTime();
            } catch (ParseException e) {
                System.out.println("error");
            }
            /// add to array list
          //  float f = (float)valueList.get(i);
            long date_created = TimeUnit.MILLISECONDS.toHours(millis);
            System.out.println(date_created+"date created");
            values.add(new Entry(date_created,(float)valueList.get(i)));
            System.out.println(valueList.get(i)+"value list");

        }


        // setup graph
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);


    }


}