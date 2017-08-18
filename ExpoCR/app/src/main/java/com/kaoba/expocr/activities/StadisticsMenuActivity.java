package com.kaoba.expocr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kaoba.expocr.R;
import com.squareup.picasso.Picasso;

public class StadisticsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics_menu);
        //Images
        ImageView imageStadisticsHeader = (ImageView) findViewById(R.id.imageChartMenu);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500603128/chart-donut_f17qtv.png").into(imageStadisticsHeader);
        ImageView imageHeatMap = (ImageView) findViewById(R.id.heatMapImage);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500655203/chart-scatterplot-hexbin_jqzuib.png").into(imageHeatMap);
        ImageView imagePeopleCounter = (ImageView) findViewById(R.id.peopleCounterImage);
        Picasso.with(getApplicationContext()).load("http://res.cloudinary.com/duxllywl7/image/upload/v1500655200/chart-bar-stacked_cpnxxl.png").into(imagePeopleCounter);
        //Images

        Button btnCounter = (Button) findViewById(R.id.btnPeopleCounter);
        btnCounter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CounterActivity.class);
                startActivity(i);
            }
        });
    }
}
